# Step 1 - Website Selection

## Selected application

| Item | Decision |
|---|---|
| Application | Demoblaze E-Commerce Store |
| URL | <https://www.demoblaze.com/> |
| Application type | Business-to-consumer (B2C) e-commerce web application |
| Environment | Public demonstration environment |
| Approval status | Approved demo website for QA automation testing |
| Automation stack | Java, Selenium WebDriver, TestNG, Maven, Page Object Model |

## Selection rationale

Demoblaze (`https://www.demoblaze.com/`) was selected as the System Under Test (SUT) because it provides a realistic customer retail journey while being completely free of anti-bot Cloudflare Turnstile protections, making it ideal for stable headless and headed browser automation.

Its key feature areas cover user authentication, product catalog filtering (Phones, Laptops, Monitors), product details, cart management, item deletion, and synthetic order placement with purchase popups.

This breadth allows for designing 15+ manual test scenarios and selecting 5 high-value automated regression scenarios for Page Object Model framework implementation.

## Evaluation against assignment needs

| Requirement | Evidence of suitability |
|---|---|
| Realistic application | Models an online retail store and end-to-end customer purchasing flows |
| At least 15 manual scenarios | Authentication, product categories, search/navigation, cart management, form validation, and checkout flows |
| Five automated scenarios | 5 stable, repeatable regression flows: Invalid Login, Category Filter, Add to Cart, Remove Item from Cart, and Checkout |
| Page Object Model | Clean separation of pages (`HomePage`, `ProductPage`, `CartPage`, `LoginModal`, `PlaceOrderModal`) |
| Positive and negative testing | Covers negative validation (invalid login alerts) and positive flows (order checkout confirmation) |
| Debugging challenge | Intentional failure and debugging documented and resolved cleanly |
| Safe usage | Official public demo store using synthetic data |

## Proposed test scope

### In scope

- Home page and category navigation (Phones, Laptops, Monitors)
- Login modal and negative credential handling
- Product details and pricing display
- Shopping cart add/remove item operations
- Place Order checkout modal and synthetic payment submission
- Confirmation modal assertions (`"Thank you for your purchase!"`)

### Out of scope

- Real financial transaction processing
- External email notifications
- Performance/load testing
- Administration backend

## Initial Page Object candidates

- `HomePage`
- `ProductPage`
- `CartPage`
- `LoginModal`
- `PlaceOrderModal`
- `CustomerOrder` (Model)

## Selection conclusion

Demoblaze meets all functional breadth, reliability, safety, and framework design criteria required by the assignment and provides 100% reliable execution in both headless and headed automated test environments.
