# Step 3 - Manual Test Scenario Design

This document contains 15 manual test scenarios designed for the Demoblaze web application (`https://www.demoblaze.com/`), covering positive and negative functional flows across authentication, catalog navigation, shopping cart, and order checkout.

---

## Scenario Summary Table

| ID | Module | Scenario Title | Priority | Selected for Automation? |
|---|---|---|---|:---:|
| **MTS-01** | Auth | Reject invalid login credentials | High | ✅ **Yes (AUT-01)** |
| **MTS-02** | Catalog | Filter products by Laptops category | Medium | ✅ **Yes (AUT-02)** |
| **MTS-03** | Cart | Add a product to the shopping cart | High | ✅ **Yes (AUT-03)** |
| **MTS-04** | Cart | Remove a product from the shopping cart | High | ✅ **Yes (AUT-04)** |
| **MTS-05** | Checkout | Complete guest order checkout with valid synthetic details | High | ✅ **Yes (AUT-05)** |
| **MTS-06** | Auth | Sign up with an existing username | Medium | ❌ No (Excluded) |
| **MTS-07** | Catalog | Filter products by Phones category | Medium | ❌ No (Excluded) |
| **MTS-08** | Catalog | Filter products by Monitors category | Medium | ❌ No (Excluded) |
| **MTS-09** | Product | View product details page for Sony vaio i5 | Low | ❌ No (Excluded) |
| **MTS-10** | Cart | Calculate correct total price for multiple items | Medium | ❌ No (Excluded) |
| **MTS-11** | Cart | Open empty cart page | Low | ❌ No (Excluded) |
| **MTS-12** | Checkout | Submit checkout form with blank required fields | High | ❌ No (Excluded) |
| **MTS-13** | Navigation | Navigate back to Home page using Brand header link | Low | ❌ No (Excluded) |
| **MTS-14** | Contact | Open Contact modal dialog | Low | ❌ No (Excluded) |
| **MTS-15** | About Us | Play video in About Us modal dialog | Low | ❌ No (Excluded) |

---

## Detailed Manual Scenarios

### MTS-01: Reject invalid login credentials
- **Preconditions:** User is on the Home Page (`https://www.demoblaze.com/`).
- **Steps:** Click "Log in" link -> Enter invalid username (`invalid_user`) & password (`wrong_pass`) -> Click "Log in" button.
- **Expected Result:** Browser alert appears with message `"User does not exist."` or `"Wrong password."`.

### MTS-02: Filter products by Laptops category
- **Preconditions:** User is on Home Page.
- **Steps:** Click "Laptops" in the left categories sidebar.
- **Expected Result:** Product grid refreshes to display laptops, including `"Sony vaio i5"`.

### MTS-03: Add a product to the shopping cart
- **Preconditions:** User is on Home Page.
- **Steps:** Click `"Samsung galaxy s6"` -> Click `"Add to cart"` -> Accept alert `"Product added."` -> Click `"Cart"`.
- **Expected Result:** Cart table lists `"Samsung galaxy s6"`.

### MTS-04: Remove a product from the shopping cart
- **Preconditions:** Product `"Samsung galaxy s6"` added to cart.
- **Steps:** Open Cart page -> Click `"Delete"` link next to product.
- **Expected Result:** Product row is removed and cart becomes empty.

### MTS-05: Complete guest order checkout with valid synthetic details
- **Preconditions:** Product added to cart.
- **Steps:** Open Cart page -> Click `"Place Order"` -> Enter synthetic details (Name, Country, City, Card, Month, Year) -> Click `"Purchase"`.
- **Expected Result:** Confirmation modal appears displaying `"Thank you for your purchase!"` along with order ID and total amount.
