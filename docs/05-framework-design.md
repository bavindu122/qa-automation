# Step 5 - Framework Design

## Technology selection

| Technology | Purpose |
|---|---|
| Java 21 | Strongly typed test implementation and current LTS runtime |
| Selenium WebDriver | Browser control through the W3C WebDriver model |
| Selenium Manager | Automatic browser-driver discovery and management |
| TestNG | Test lifecycle, assertions, grouping, suite execution, and reporting |
| Maven | Reproducible dependency, compilation, and test execution lifecycle |
| Page Object Model | Separation of page interaction from scenario assertions |

## High-level flow

```text
TestNG scenario
    -> Page Object methods
        -> BasePage waits/interactions
            -> WebDriver
                -> Chrome
                    -> nopCommerce demo

Configuration -> DriverFactory and test data
Test listener <- TestNG failure event -> screenshot evidence
```

## Package responsibilities

### `lk.ucsc.nopcommerce.config`

Loads non-secret defaults from `config.properties`. A command-line system
property overrides a file value, allowing commands such as
`mvn test -Dheadless=false` without source changes.

### `lk.ucsc.nopcommerce.driver`

Creates, returns, and closes WebDriver instances. Browser construction is kept
out of tests and Page Objects. A `ThreadLocal` prevents accidental driver
sharing if parallel execution is introduced later, although this assignment
initially runs serially because the demo is shared.

### `lk.ucsc.nopcommerce.pages`

Contains one class per meaningful page or workflow surface. Page Objects own
locators and user actions. They return another Page Object when navigation
occurs and expose state required by test assertions. `BasePage` supplies
explicitly waited common interactions.

### `lk.ucsc.nopcommerce.models`

Holds immutable test-data records such as `BillingAddress`. Models prevent long
argument lists and keep data separate from interaction code.

### `lk.ucsc.nopcommerce.base`

Defines the TestNG browser lifecycle. Every test receives a clean browser and
the driver is closed even when a test fails.

### `lk.ucsc.nopcommerce.tests`

Contains the five selected scenario classes. Tests describe intent and perform
assertions; they do not contain raw Selenium locators.

### `lk.ucsc.nopcommerce.listeners`

Responds to TestNG events. The failure listener writes a timestamped screenshot
while preserving the original exception and stack trace.

## Resource responsibilities

| Resource | Responsibility |
|---|---|
| `config.properties` | Environment, timeout, and changeable catalog test data |
| `testng.xml` | Suite composition, listener registration, and serial execution policy |

## Design rules

1. Tests must not call `findElement` directly.
2. Locators must remain private inside Page Objects.
3. Fixed sleeps are prohibited in production test code.
4. Page Objects perform actions and return state; TestNG tests own assertions.
5. Each test creates its own browser and prerequisites.
6. Mutable demo values are configuration, not hard-coded test logic.
7. Teardown runs for passed, failed, and skipped tests.
8. A failure screenshot supplements—but never replaces—the exception.

## Locator strategy

Preference order:

1. Stable HTML `id`
2. Stable form `name`
3. Purpose-specific CSS class scoped to a page component
4. Semantic relationship to a known product or section
5. Text only when the text is the expected business label

Absolute XPath, index-only locators, generated identifiers, and visual
coordinates are avoided.

## Synchronisation strategy

The framework sets no implicit wait. `WebDriverWait` is used for the exact
state needed by the next action: visibility, clickability, URL change, or
presence of a business message. This avoids combining implicit and explicit
timeouts and makes failures easier to diagnose.

## Error handling and evidence

- Selenium and assertion exceptions remain visible to TestNG.
- The listener captures a PNG for every failed test.
- TestNG produces HTML/XML results under `test-output`/Surefire output.
- Environment problems are reported separately during result analysis.
- Retry logic is intentionally absent; automatic retries could conceal flaky
  tests or application defects.

## Maintainability examples

- If a cart selector changes, only `CartPage` should require modification.
- If the demo product changes, update `config.properties` without editing test
  flow code.
- A new browser can be added inside `DriverFactory` without changing tests.
- Additional scenarios can reuse the existing pages and data models.

