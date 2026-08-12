# Step 1 - Website Selection

## Selected application

| Item | Decision |
|---|---|
| Application | nopCommerce public demo store |
| URL | <https://demo.nopcommerce.com/> |
| Application type | Business-to-consumer e-commerce web application |
| Environment | Public demonstration environment |
| Approval status | Demo websites permitted by the lecturer; final URL to be registered if the lecturer maintains a uniqueness list |
| Automation stack | Java, Selenium WebDriver, TestNG, Maven, Page Object Model |

## Selection rationale

nopCommerce was selected because it provides a realistic customer journey while
remaining a demonstration environment. Its features cover several independent
business areas: customer registration, authentication, catalog navigation,
search, configurable products, wishlist and comparison lists, shopping cart,
and guest checkout.

This breadth is sufficient to design more than 15 meaningful manual scenarios
and select five high-value regression scenarios for browser automation. The
pages also form natural Page Objects, making the application suitable for
demonstrating a maintainable framework rather than a collection of unrelated
scripts.

## Evaluation against assignment needs

| Requirement | Evidence of suitability |
|---|---|
| Realistic application | Models an online retail store and end-to-end customer journeys |
| At least 15 manual scenarios | Authentication, search, catalog, product, cart, comparison, wishlist, and checkout behaviours provide sufficient coverage |
| Five automated scenarios | Stable, repeatable flows can be selected from login, search, cart, comparison, and checkout |
| Page Object Model | Major screens have distinct responsibilities and reusable controls |
| Positive and negative testing | Forms and searches expose required-field, invalid-input, and empty-result behaviours |
| Debugging challenge | An intentional assertion or locator failure can be isolated and repaired without harming the application |
| Safe usage | It is an official demo and does not require a real financial transaction |

## Proposed test scope

### In scope

- Home page and header navigation
- Registration and login validation
- Product search and search results
- Product details and product configuration
- Shopping cart behaviour
- Product comparison
- Guest checkout up to successful order confirmation using demo data
- User-visible validation messages and state changes

### Out of scope

- Administration portal
- Database and API validation
- Real payment gateway processing
- Email delivery and external email content
- Load, penetration, and destructive security testing
- Mobile native applications
- Third-party links and services
- Visual pixel-perfect comparison

## Constraints and responsible-use rules

- The shared demo may contain temporary changes made by other users.
- Demo data can be reset, so tests must create or discover their own prerequisites.
- Exact catalog counts, review counts, and other volatile values will not be hard-coded.
- Registration tests will generate unique synthetic email addresses.
- Tests will not use personal or real payment information.
- Tests should run at a responsible frequency and avoid unnecessary repeated orders.

## Initial Page Object candidates

- `HomePage`
- `RegisterPage`
- `LoginPage`
- `SearchResultsPage`
- `ProductPage`
- `CartPage`
- `CompareProductsPage`
- `CheckoutPage`
- `OrderConfirmationPage`

## Selection conclusion

nopCommerce meets the functional breadth, safety, testability, and framework
design needs of the assignment. It is therefore selected as the system under
test, subject only to any lecturer process for registering a unique choice.

