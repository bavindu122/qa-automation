# AI Usage Disclosure

## Tools Used

Antigravity AI (Google DeepMind) was used as a pair-programming coding assistant for this assignment.

## How AI Was Used

AI assistance was used to:
- Evaluate candidate demo websites for headless automation suitability;
- Identify and debug Cloudflare anti-bot restrictions on initial target site;
- Design Page Object Model architecture and Java 21 / Selenium WebDriver 4.44 classes;
- Refactor test scenarios for Demoblaze (`https://www.demoblaze.com/`);
- Resolve AJAX synchronization (`StaleElementReferenceException`) and alert popups;
- Draft comprehensive assignment documentation and execution guides.

## Student Responsibility

All submitted code and documentation have been executed, verified, and understood by the student. The student remains responsible for running the test suite, explaining technical decisions during the viva, and presenting the 5-minute video reflection.

## Activity Log

| Date | Activity | Result |
|---|---|---|
| 2026-08-12 | Evaluated target websites & debugged Cloudflare Turnstile blocks | Transitioned SUT to Demoblaze (`https://www.demoblaze.com/`) |
| 2026-08-12 | Developed Page Object Model framework (`HomePage`, `ProductPage`, `CartPage`, `LoginModal`, `PlaceOrderModal`) | POM architecture built with Java 21 & Selenium 4.44 |
| 2026-08-12 | Implemented 5 automated regression scenarios | All 5 test cases passing in headless and headed modes |
| 2026-08-12 | Resolved AJAX synchronization timing & alert dialog handling | Robust, flake-free test execution achieved |
| 2026-08-12 | Updated assignment documentation & report guides | Repository ready for final submission |
