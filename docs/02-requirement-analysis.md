# Step 2 - Requirement Analysis

## Executive Summary

The Demoblaze web application (`https://www.demoblaze.com/`) is an online e-commerce platform offering electronics such as mobile phones, laptops, and monitors. This document outlines the application objectives, key features, target users, assumptions, risks, and technical observations.

---

## 1. Application Objectives

- Provide users with an intuitive online store to browse electronics catalog by categories.
- Allow users to view product specs, add items to a shopping cart, modify cart items, and execute synthetic checkouts.
- Enable user authentication (sign up and log in) with modal-based feedback.

---

## 2. Key Features Analyzed

1. **Category Navigation & Filtering:** Filtering catalog items by Phones, Laptops, and Monitors.
2. **Product Details View:** Displays product name, price, description, and "Add to cart" button.
3. **Shopping Cart Management:** Cart table (`cart.html`) showing item titles, prices, total cost calculation, and deletion functionality.
4. **Order Placement & Checkout:** Modal dialog capturing buyer name, country, city, credit card, month, year, and generating order confirmation popups (`"Thank you for your purchase!"`).
5. **User Authentication:** Sign up and Log in popups with JavaScript alert validation.

---

## 3. Assumptions and Dependencies

- **Public Availability:** The SUT is accessible via HTTP/HTTPS without restricted IP whitelisting.
- **Client-Side Rendering:** Demoblaze uses client-side JavaScript (`fetch`) to populate category grids and cart rows dynamically.
- **Browser Compatibility:** Chrome stable version running in both headless and headed modes.

---

## 4. Identified Quality Risks & Mitigations

| Risk | Impact | Mitigation Strategy |
|---|---|---|
| Asynchronous AJAX delay during category filtering | High | Implement explicit waits (`stalenessOf` & dynamic locator re-polling) in `HomePage` |
| Asynchronous DOM delay when opening Cart page | High | Implement URL wait (`cart.html`) and AJAX response buffer before cart item queries |
| Alert popups blocking execution | Medium | Use Selenium `ExpectedConditions.alertIsPresent()` helper in `BasePage` |
