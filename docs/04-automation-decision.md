# Step 4 - Automation Decision

## Decision criteria

Each manual scenario was evaluated using the following factors:

- **Business risk:** impact if the behaviour regresses.
- **Regression value:** likelihood that the scenario will be repeated frequently.
- **Determinism:** ability to establish stable preconditions and assertions.
- **Technical coverage:** value in demonstrating Selenium, TestNG, and POM.
- **Maintenance cost:** sensitivity to changing data, layout, or shared state.
- **Execution safety:** ability to run without affecting real users or services.

Automation is not automatically appropriate for every manual scenario. The
selected set balances critical customer journeys with independence and
maintainability.

## Selected five scenarios

| Automation ID | Manual ID | Automated scenario | Main reason |
|---|---|---|---|
| AUT-01 | MTS-05 | Reject invalid login credentials | Critical security boundary with stable negative data and assertion |
| AUT-02 | MTS-07 | Search for an existing product | Frequent core journey with fast feedback and reusable search behaviour |
| AUT-03 | MTS-11 | Add a simple product to the cart | Critical purchase-path state transition with strong visible assertions |
| AUT-04 | MTS-15 | Compare two products | Exercises repeated product actions and verifies multi-product state |
| AUT-05 | MTS-18 | Complete a valid guest checkout | Highest-value end-to-end customer journey and integration coverage |

### AUT-01 - Reject invalid login credentials

**Why automate:** Authentication is a high-risk boundary. Invalid credentials
are safe and repeatable because no account needs to persist across the hourly
demo reset. The result has a clear message and authenticated state can also be
checked. This test provides fast feedback and is suitable for every regression
run.

**Primary assertions:** Login error is visible; logout control is absent; login
page remains active.

### AUT-02 - Search for an existing product

**Why automate:** Search is a frequent entry point to the sales journey. It is
quick to execute, requires no account, and supports reusable Page Object
behaviour. The expected result can be made resilient by configuring a known
search term and checking relevance rather than an exact result count.

**Primary assertions:** Results page is displayed; query is retained; at least
one returned product name contains the expected term.

### AUT-03 - Add a simple product to the cart

**Why automate:** Adding a product is a critical transition between catalog and
purchase flow. The behaviour is repeated across releases and produces strong
observable state: success notification, cart count, product name, and quantity.
Using a simple product keeps this test focused; configurable products remain
valuable for manual exploratory testing.

**Primary assertions:** Success notification is visible; cart contains the
selected product once; quantity is 1.

### AUT-04 - Compare two products

**Why automate:** Comparison involves repeated actions on different products and
state carried into a separate page. It demonstrates reusable component methods
and collection assertions without depending on a customer account or checkout.

**Primary assertions:** Both selected product names are displayed on the compare
page; the comparison table contains two product columns.

### AUT-05 - Complete a valid guest checkout

**Why automate:** Guest checkout covers the most valuable customer path and
connects catalog, cart, terms, customer information, shipping, payment, review,
and confirmation. Although it has higher maintenance cost, one end-to-end test
is justified by its business impact and its value in the assignment and viva.

**Primary assertions:** Checkout reaches each required stage; order review shows
the product; successful order message and non-empty order number are displayed.

## Why the remaining scenarios are not selected

| Manual ID | Decision and justification |
|---|---|
| MTS-01 | Keep manual for this five-test scope. Registration needs a unique address on every run and can be affected by the shared reset; its form also adds maintenance without increasing coverage as much as guest checkout. |
| MTS-02 | Keep manual. Required-field presentation is useful exploratory validation but duplicates form-validation mechanics already exercised in login and checkout. |
| MTS-03 | Keep manual. It depends on successfully creating and retaining an account during the same reset window, increasing setup time and failure ambiguity. |
| MTS-04 | Keep manual initially. Valid login requires a persistent or freshly created customer account; invalid login provides a more independent authentication check. |
| MTS-06 | Keep manual because it depends on the unselected valid-login setup and offers lower risk coverage than the chosen journeys. |
| MTS-08 | Keep manual for the initial suite. It is a good future data-driven extension to AUT-02 but selecting it separately would over-concentrate the five-test suite on search. |
| MTS-09 | Keep manual. Category navigation is covered indirectly when locating products and has lower business risk than cart or checkout. |
| MTS-10 | Keep manual. Price parsing, special pricing, and shared catalog changes increase assertion complexity and maintenance cost. |
| MTS-12 | Keep manual. Configuration combinations make it excellent for exploratory and boundary testing, while AUT-03 gives a more focused cart regression test. |
| MTS-13 | Keep manual for the initial suite. Quantity calculations are valuable but extend cart state and pricing assertions; they are a recommended next automation candidate. |
| MTS-14 | Keep manual because removal is lower risk than adding and checkout, and is simple to verify manually. |
| MTS-16 | Keep manual as a separate scenario. AUT-04 may clear its own comparison state during cleanup, but clearing is not the main test objective. |
| MTS-17 | Keep manual as a separate scenario. The selected guest checkout accepts the terms; modal styling and behaviour can be explored manually without blocking the primary end-to-end path. |

## Planned test independence

- Each automated test starts with a new browser session.
- Tests must not depend on execution order.
- Cart and comparison state are created inside the relevant test.
- Invalid login uses fixed synthetic credentials that should never be valid.
- Checkout uses guest mode and synthetic address data.
- Product names and search terms are stored in configuration so a catalog change
  can be handled without editing test logic.

## Automation traceability

| Automation ID | Page Objects expected | Test type |
|---|---|---|
| AUT-01 | `HomePage`, `LoginPage` | Negative functional test |
| AUT-02 | `HomePage`, `SearchResultsPage` | Positive functional test |
| AUT-03 | `HomePage`, `SearchResultsPage`, `ProductPage`, `CartPage` | Positive integration test |
| AUT-04 | `HomePage`, `SearchResultsPage`, `CompareProductsPage` | Positive functional test |
| AUT-05 | `ProductPage`, `CartPage`, `CheckoutPage`, `OrderConfirmationPage` | End-to-end test |

