# Step 4 - Automation Decision

## Automation Decision Matrix

| Manual Scenario ID | Title | Automate? | Selection Rationale |
|---|---|:---:|---|
| **MTS-01** | Reject invalid login credentials | ✅ **Selected (AUT-01)** | Critical security negative test; verifies alert handling and modal interaction. |
| **MTS-02** | Filter products by Laptops category | ✅ **Selected (AUT-02)** | Core navigation flow; verifies AJAX catalog re-rendering. |
| **MTS-03** | Add product to shopping cart | ✅ **Selected (AUT-03)** | Core e-commerce function; verifies product detail interaction and cart persistence. |
| **MTS-04** | Remove product from shopping cart | ✅ **Selected (AUT-04)** | High business value; verifies DOM element deletion and cart state updates. |
| **MTS-05** | Complete guest order checkout | ✅ **Selected (AUT-05)** | End-to-end purchasing journey; verifies multi-step modal form entry and order confirmation assertions. |
| MTS-06 to MTS-15 | Category variations, Contact modal, video playback | ❌ Excluded | Lower ROI, redundant category coverage, or static content better suited for manual/visual testing. |

---

## Justification for Selected Automated Scenarios

1. **AUT-01 (MTS-01):** Validates negative authentication and native alert dialog handling.
2. **AUT-02 (MTS-02):** Validates dynamic AJAX catalog DOM updates without full page reloads.
3. **AUT-03 (MTS-03):** Validates the primary core e-commerce add-to-cart funnel.
4. **AUT-04 (MTS-04):** Validates cart item removal and empty state handling.
5. **AUT-05 (MTS-05):** Validates end-to-end revenue transaction flow from cart to order confirmation.
