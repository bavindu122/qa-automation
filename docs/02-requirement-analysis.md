# Step 2 - Requirement Analysis

## 1. Application overview

nopCommerce Demo is a public storefront that simulates the customer-facing
functions of an online retailer. A visitor can discover products and build a
cart without signing in. A registered customer can authenticate and use
customer-specific functions. Both guests and registered customers can progress
through checkout using demonstration data.

## 2. Business objectives

The application is expected to:

1. Present an organised and searchable catalog of products.
2. Help customers make purchasing decisions using product information,
   categories, sorting, comparison, and wishlist functionality.
3. Allow customers to configure products that have required options.
4. Preserve accurate cart items, quantities, and calculated totals.
5. Support account creation and secure customer authentication.
6. Collect the information required to complete a guest or registered checkout.
7. Confirm successful orders and prevent progression when required data is
   missing or invalid.

## 3. Users and stakeholders

| User or stakeholder | Goals and interests |
|---|---|
| Guest shopper | Browse, search, compare products, manage a cart, and check out without creating an account |
| Registered customer | Sign in and use account-related shopping functions in addition to the guest capabilities |
| Store administrator | Maintain products, customers, configuration, and orders; administration is outside this project's test scope |
| Business owner | Receive valid orders and provide a dependable buying experience |
| QA engineer | Detect regressions in important customer journeys and provide reproducible evidence |
| Development team | Receive maintainable tests and clear failure information |

## 4. Key functional requirements

The following requirements are inferred from the observable behaviour of the
demo storefront. They will be validated during manual exploration before being
treated as final expected behaviour.

### FR-01 - Navigation

- The application shall display the storefront home page.
- The header and category navigation shall lead to their corresponding pages.
- The application shall provide visible access to search, registration, login,
  wishlist, cart, and product comparison functions where appropriate.

### FR-02 - Customer registration

- The application shall provide a registration form.
- Mandatory registration fields shall be identified and validated.
- A customer shall be able to register using valid, unique information.
- The application shall reject an email address already associated with an
  account.

### FR-03 - Authentication

- A registered customer shall be able to sign in with valid credentials.
- Invalid credentials shall not create an authenticated session.
- A signed-in customer shall be able to log out.

### FR-04 - Search and catalog discovery

- A visitor shall be able to search using a product-related term.
- Matching products shall be shown on the search-results page.
- A search with no matching product shall display a clear empty-result message.
- Customers shall be able to browse products by category.
- Available sorting and display controls shall update the product listing.

### FR-05 - Product details and configuration

- A product page shall display its name, price, description, and purchasing
  controls.
- A configurable product shall require valid selections before being added to
  the cart.
- The selected configuration and quantity shall be reflected in the cart.

### FR-06 - Shopping cart

- A customer shall be able to add an available product to the cart.
- The cart shall show the selected product, quantity, price, and totals.
- Updating quantity shall recalculate the appropriate totals.
- Removing an item shall update the cart state.

### FR-07 - Product comparison

- A visitor shall be able to add eligible products to the comparison list.
- The comparison page shall display the selected products and their comparable
  information.
- A visitor shall be able to remove a product or clear the comparison list.

### FR-08 - Checkout

- A visitor with at least one valid cart item shall be able to begin checkout.
- A guest shall be offered guest checkout when the store permits it.
- Required billing, shipping, and payment information shall be validated.
- The order review shall represent the selected item and checkout information.
- Confirming a valid demo order shall display an order-success message and order
  identifier.

## 5. Non-functional quality expectations

| ID | Quality attribute | Expected behaviour |
|---|---|---|
| NFR-01 | Usability | Labels, actions, validation messages, and navigation should be understandable to a typical shopper |
| NFR-02 | Reliability | Repeating an unchanged test flow should produce functionally equivalent results |
| NFR-03 | Compatibility | Critical customer journeys should function in a current desktop Chrome browser |
| NFR-04 | Performance | Pages and user-visible state changes should complete within a reasonable explicit-wait timeout |
| NFR-05 | Security | Password values should be masked and invalid authentication should not expose account access |
| NFR-06 | Maintainability | Automated tests should separate test intent, page interaction, configuration, and test data |

This project performs functional browser testing. It observes, but does not
formally certify, performance, accessibility, or security.

## 6. Assumptions

- The official demo URL remains publicly reachable during execution.
- The lecturer accepts a demo application and Java/Selenium/TestNG/POM as stated
  in the assignment.
- Chrome and a compatible driver can be installed or resolved automatically.
- The storefront continues to allow guest checkout.
- Product names and catalog availability may change, so reusable tests will use
  configurable test data and verify behaviour rather than exact catalog size.
- A successful demo checkout has no real financial or fulfilment consequence.
- Only the English desktop storefront is tested in this assignment.

## 7. Risks and mitigations

| Risk | Likelihood | Impact | Mitigation |
|---|---|---|---|
| Shared users alter demo data | High | Medium | Use independent tests, unique registration data, and behaviour-based assertions |
| Hourly reset removes accounts or orders | High | Medium | Create prerequisites inside the test or use guest flows; never depend on old orders |
| Product or UI content changes | Medium | High | Keep selectors inside Page Objects and avoid volatile text/count assertions |
| Network or demo outage | Medium | High | Use clear environment checks and report environmental failures separately |
| Asynchronous UI updates cause flaky tests | Medium | High | Use explicit waits for visible/clickable states; prohibit fixed sleeps |
| Browser/driver incompatibility | Low | High | Use Selenium Manager and record supported Java/browser versions |
| Parallel tests collide through shared state | Medium | Medium | Run state-changing tests serially initially and isolate data before enabling parallelism |
| Real personal data is entered | Low | High | Use clearly synthetic names, addresses, telephone numbers, and email domains |
| Overly long end-to-end tests obscure failures | Medium | Medium | Keep five selected tests focused and use reusable prerequisite helpers |

## 8. Testability observations

- Major customer journeys are separated into distinct pages, which suits Page
  Object Model design.
- The shared demo makes persistent account and order assertions unreliable.
- Configurable product and checkout flows exercise several Selenium control
  types: text input, checkbox, radio button, dropdown, links, and dynamic state.
- Search and validation scenarios can be made data-driven through TestNG.
- User-visible messages provide strong assertion points.
- The framework should capture a screenshot and current URL when a test fails.

## 9. Acceptance boundaries

A scenario passes when the expected user-visible behaviour is observed in the
supported browser within the configured timeout. A scenario does not fail the
product solely because exact inventory, price, review count, order number, or
catalog size differs from a previous execution, unless that value is the direct
subject of the scenario.

Environmental failures—such as DNS failure, demo outage, or browser start
failure—must be reported separately from verified application defects.

