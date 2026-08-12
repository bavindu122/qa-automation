# Step 3 - Manual Test Scenario Design

## Test environment

| Item | Value |
|---|---|
| Application | nopCommerce Demo Store |
| Base URL | <https://demo.nopcommerce.com/> |
| Primary browser | Current stable Google Chrome, desktop viewport |
| Test data | Synthetic data only |
| Execution rule | Recheck product availability before executing catalog-dependent cases |

## Priority definitions

- **P0 - Critical:** failure blocks a core customer journey or checkout.
- **P1 - High:** failure significantly affects discovery, account use, or cart
  integrity but has a workaround.
- **P2 - Medium:** failure affects a supporting feature or convenience.

## Scenario summary and requirement traceability

| ID | Scenario | Priority | Requirement |
|---|---|---:|---|
| MTS-01 | Register a customer with valid unique data | P1 | FR-02 |
| MTS-02 | Validate required registration fields | P1 | FR-02 |
| MTS-03 | Reject registration using an existing email | P1 | FR-02 |
| MTS-04 | Log in with valid credentials | P0 | FR-03 |
| MTS-05 | Reject invalid login credentials | P0 | FR-03 |
| MTS-06 | Log out an authenticated customer | P1 | FR-03 |
| MTS-07 | Search for an existing product | P0 | FR-04 |
| MTS-08 | Search for a nonexistent product | P1 | FR-04 |
| MTS-09 | Browse a catalog category | P1 | FR-01, FR-04 |
| MTS-10 | Sort a product listing by price | P1 | FR-04 |
| MTS-11 | Add a simple product to the cart | P0 | FR-05, FR-06 |
| MTS-12 | Add a configured product to the cart | P0 | FR-05, FR-06 |
| MTS-13 | Update product quantity in the cart | P0 | FR-06 |
| MTS-14 | Remove a product from the cart | P1 | FR-06 |
| MTS-15 | Compare two products | P2 | FR-07 |
| MTS-16 | Clear the product comparison list | P2 | FR-07 |
| MTS-17 | Validate checkout terms requirement | P0 | FR-08 |
| MTS-18 | Complete a valid guest checkout | P0 | FR-08 |

---

## Detailed scenarios

### MTS-01 - Register a customer with valid unique data

**Priority:** P1  
**Preconditions:** User is logged out; email has not previously been registered.  
**Test data:** Gender: Male; first name: `QA`; last name: `Student`;
email: `qa.student+<timestamp>@example.com`; password: `Test@12345`.

**Steps:**

1. Open the home page.
2. Select **Register**.
3. Enter the valid test data in all mandatory fields.
4. Re-enter the password in the confirmation field.
5. Select **Register**.

**Expected result:** Registration succeeds, the completion message is displayed,
and the header represents an authenticated customer.  
**Evidence:** Screenshot of the successful registration message.

### MTS-02 - Validate required registration fields

**Priority:** P1  
**Preconditions:** User is logged out and is on the registration page.  
**Test data:** Leave all fields empty.

**Steps:**

1. Select **Register** without entering any information.
2. Observe validation messages next to required inputs.

**Expected result:** Registration is not submitted successfully; required-field
messages are shown for first name, last name, email, password, and password
confirmation.  
**Evidence:** Screenshot containing the validation messages.

### MTS-03 - Reject registration using an existing email

**Priority:** P1  
**Preconditions:** A customer account was created during the current demo-reset
window; user is logged out.  
**Test data:** Reuse the registered email; provide otherwise valid information.

**Steps:**

1. Open **Register**.
2. Complete the form using the existing email address.
3. Submit the form.

**Expected result:** No duplicate account is created and an email-already-exists
error is displayed.  
**Evidence:** Screenshot of the duplicate-email error.

### MTS-04 - Log in with valid credentials

**Priority:** P0  
**Preconditions:** A registered account exists in the current reset window; user
is logged out.  
**Test data:** Valid account email and password.

**Steps:**

1. Select **Log in**.
2. Enter the valid email and password.
3. Submit the login form.

**Expected result:** The customer is authenticated, the account email or account
link is visible in the header, and **Log out** is available.  
**Evidence:** Screenshot of the authenticated header.

### MTS-05 - Reject invalid login credentials

**Priority:** P0  
**Preconditions:** User is logged out.  
**Test data:** Email: `invalid.user@example.com`; password: `WrongPassword123!`.

**Steps:**

1. Open **Log in**.
2. Enter the invalid credentials.
3. Submit the form.

**Expected result:** The user remains unauthenticated and a clear unsuccessful
login message is displayed without revealing which credential was correct.  
**Evidence:** Screenshot of the login error.

### MTS-06 - Log out an authenticated customer

**Priority:** P1  
**Preconditions:** Customer is authenticated.  
**Test data:** None.

**Steps:**

1. Select **Log out** from the header.
2. Observe the header and current page.

**Expected result:** The authenticated session ends, **Log in** and **Register**
are displayed, and customer-only access is no longer available.  
**Evidence:** Screenshot of the logged-out header.

### MTS-07 - Search for an existing product

**Priority:** P0  
**Preconditions:** An identifiable product such as `Apple MacBook Pro` is visible
in the current catalog.  
**Test data:** Search term: `MacBook`.

**Steps:**

1. Enter the term in the header search box.
2. Submit the search.
3. Review the returned products.

**Expected result:** The search-results page is displayed and contains at least
one product relevant to `MacBook`.  
**Evidence:** Screenshot of the search term and relevant result.

### MTS-08 - Search for a nonexistent product

**Priority:** P1  
**Preconditions:** User can access the search box.  
**Test data:** Search term: `zz-no-such-product-48291`.

**Steps:**

1. Search using the nonexistent term.
2. Review the results area.

**Expected result:** The search-results page loads successfully, no unrelated
product is presented as a match, and an empty-result message is shown.  
**Evidence:** Screenshot of the term and empty-result message.

### MTS-09 - Browse a catalog category

**Priority:** P1  
**Preconditions:** Home page is available.  
**Test data:** Category: **Computers**; subcategory: **Notebooks**.

**Steps:**

1. Select **Computers** from the main navigation.
2. Open **Notebooks**.
3. Review the breadcrumb, title, and product listing.

**Expected result:** The correct category page is displayed; its breadcrumb and
heading identify Notebooks, and any displayed products belong to that category.  
**Evidence:** Screenshot containing breadcrumb, title, and listing.

### MTS-10 - Sort a product listing by price

**Priority:** P1  
**Preconditions:** A category or search page contains at least two differently
priced products.  
**Test data:** Sort option: **Price: Low to High**.

**Steps:**

1. Open a qualifying product-list page.
2. Select **Price: Low to High** from the sorting control.
3. Wait for the product list to refresh.
4. Compare the displayed prices from first to last.

**Expected result:** Products are arranged in non-decreasing price order, subject
to any clearly identified special-price rules.  
**Evidence:** Screenshot showing the selected sort option and ordered prices.

### MTS-11 - Add a simple product to the cart

**Priority:** P0  
**Preconditions:** Cart is empty; a simple in-stock product is available.  
**Test data:** Product: `HTC smartphone` or another currently available simple
product; quantity: 1. The MacBook is excluded because live inspection showed a
minimum purchase quantity of 2.

**Steps:**

1. Open the product detail page.
2. Select **Add to cart**.
3. Wait for the success notification.
4. Open the shopping cart.

**Expected result:** A success notification appears, cart quantity increases,
and the cart contains the selected product with quantity 1.  
**Evidence:** Screenshot of the cart row.

### MTS-12 - Add a configured product to the cart

**Priority:** P0  
**Preconditions:** Cart is empty and `Build your own computer` is available.  
**Test data:** Select one valid processor, RAM, HDD, OS, and any explicitly
recorded software choices.

**Steps:**

1. Open **Build your own computer**.
2. Select a valid value for every required configuration option.
3. Record the displayed price.
4. Add the product to the cart.
5. Open the cart.

**Expected result:** The product is added once; the cart displays the selected
configuration and a price consistent with the choices shown before adding.  
**Evidence:** Screenshots of the selected configuration and resulting cart row.

### MTS-13 - Update product quantity in the cart

**Priority:** P0  
**Preconditions:** Cart contains one product with quantity 1.  
**Test data:** New quantity: 2.

**Steps:**

1. Open the shopping cart.
2. Change the product quantity from 1 to 2.
3. Select **Update shopping cart** if the interface requires it.
4. Observe the line subtotal and cart total.

**Expected result:** Quantity becomes 2 and the line subtotal/cart total are
recalculated consistently without duplicating the row.  
**Evidence:** Before-and-after screenshots of quantity and subtotal.

### MTS-14 - Remove a product from the cart

**Priority:** P1  
**Preconditions:** Cart contains at least one product.  
**Test data:** Existing cart item.

**Steps:**

1. Open the shopping cart.
2. Use the item's remove control.
3. Observe the cart and header cart count.

**Expected result:** The selected product is removed, totals are updated, and an
empty-cart message is shown if no items remain.  
**Evidence:** Screenshot of the updated or empty cart.

### MTS-15 - Compare two products

**Priority:** P2  
**Preconditions:** Comparison list is empty; at least two comparable products
are available.  
**Test data:** Two products from the same category.

**Steps:**

1. Add the first product to the comparison list.
2. Add the second product to the comparison list.
3. Open the comparison page.

**Expected result:** Both selected products appear in the comparison table with
their names, prices, and available comparable attributes.  
**Evidence:** Screenshot of both comparison columns.

### MTS-16 - Clear the product comparison list

**Priority:** P2  
**Preconditions:** Comparison list contains at least two products.  
**Test data:** None.

**Steps:**

1. Open the comparison page.
2. Select **Clear list**.

**Expected result:** All products are removed and the comparison-empty message is
displayed.  
**Evidence:** Screenshot of the empty comparison list.

### MTS-17 - Validate checkout terms requirement

**Priority:** P0  
**Preconditions:** Cart contains a valid product; terms of service are not
selected.  
**Test data:** None.

**Steps:**

1. Open the shopping cart.
2. Leave the terms-of-service checkbox unselected.
3. Select **Checkout**.

**Expected result:** Checkout does not progress and the application instructs
the user to accept the terms of service.  
**Evidence:** Screenshot of the terms validation message or dialog.

### MTS-18 - Complete a valid guest checkout

**Priority:** P0  
**Preconditions:** User is logged out; cart contains an available product; guest
checkout is enabled.  
**Test data:** Synthetic billing address; a demo-compatible shipping and payment
method; email under `example.com`.

**Steps:**

1. Open the cart and accept the terms of service.
2. Select **Checkout** and continue as guest.
3. Complete all required billing fields with valid synthetic information.
4. Select an available shipping method.
5. Select an available non-real payment method.
6. Review the order information.
7. Confirm the order once.

**Expected result:** Each checkout stage accepts valid data, the review represents
the selected product and address, and order confirmation displays a successful
completion message with an order number. No real charge occurs.  
**Evidence:** Screenshots of order review and successful confirmation.

## Execution record template

Use this table during manual execution. Do not overwrite the scenario
definitions with results.

| Execution date | Build/environment | Scenario ID | Actual result | Status | Defect ID | Evidence path | Tester |
|---|---|---|---|---|---|---|---|
| YYYY-MM-DD | Public demo / Chrome version | MTS-XX | Concise observation | Pass/Fail/Blocked | N/A | `evidence/manual/...png` | Name |
