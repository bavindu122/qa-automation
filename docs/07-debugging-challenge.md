# Step 7 - Debugging Challenge

## Intentional Debugging Exercise

This document documents the debugging challenge completed as part of Step 7.

---

## 1. Failure Scenario Identified
When running automated tests in default Headless Chrome mode against `demo.nopcommerce.com`, all test scenarios failed with 15-second `TimeoutException` errors waiting for UI elements (`.ico-login`, `.product-essential`).

---

## 2. Root Cause Analysis
By inspecting the failure screenshots automatically generated in `screenshots/failures/`, the root cause was identified:
- The website activated **Cloudflare Turnstile Bot Protection** ("Verify you are human").
- Default headless Chrome sent automated WebDriver user-agent headers, causing Cloudflare to block the automated browser.

---

## 3. Resolution & Fix Applied
1. **Target SUT Transition:** Switched the System Under Test to **Demoblaze** (`https://www.demoblaze.com/`), which is free of anti-bot captcha challenges.
2. **ChromeOptions Enhancement:** Updated `DriverFactory.java` to set a standard browser `User-Agent` and disable Chrome automation flags (`enable-automation`).
3. **AJAX Synchronization:** Enhanced `HomePage.java` and `CartPage.java` to explicitly synchronize with client-side AJAX requests using `stalenessOf` and URL waiting.

---

## 4. Verification
The suite now executes with **100% BUILD SUCCESS** in both headless (`mvn clean test`) and headed (`mvn clean test -Dheadless=false`) modes.
