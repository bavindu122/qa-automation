# Step 5 - Framework Design

## Architecture Overview

The automation framework is built using **Java 21**, **Selenium WebDriver 4.44.0**, **TestNG 7.12.0**, and **Maven 3.9+**. It enforces the **Page Object Model (POM)** pattern to decouple test logic from UI element locators.

---

## Package & Structure Breakdown

```text
src/main/java/lk/ucsc/nopcommerce/
├── config/
│   └── ConfigReader.java         # Loads runtime key-value properties from config.properties
├── driver/
│   └── DriverFactory.java        # ThreadLocal WebDriver lifecycle & ChromeOptions anti-bot settings
├── models/
│   └── CustomerOrder.java        # Record model encapsulating synthetic checkout buyer data
└── pages/
    ├── BasePage.java             # Common Selenium interaction methods & alert handling
    ├── HomePage.java             # Main landing page, navigation, and category filtering
    ├── ProductPage.java          # Product details page and Add-to-Cart action
    ├── CartPage.java             # Shopping cart table, item deletion, and checkout trigger
    ├── LoginModal.java           # Login modal dialog interaction
    └── PlaceOrderModal.java      # Order placement modal form & confirmation alert handling

src/test/java/lk/ucsc/nopcommerce/
├── base/
│   └── BaseTest.java             # TestNG @BeforeMethod & @AfterMethod lifecycle setup
├── debug/
│   └── IntentionalFailureTest.java # Controlled failure exercise
├── listeners/
│   └── TestFailureListener.java  # TestNG ITestListener taking PNG screenshots on failure
└── tests/
    └── SelectedScenariosTest.java # 5 automated regression test scenarios
```

---

## Key Framework Features

1. **Page Object Model (POM):** Locators and page interactions are isolated inside Page classes.
2. **ThreadLocal Driver Management:** Prevents race conditions during parallel or isolated test thread runs.
3. **Synchronization & Alert Management:** Explicit waits (`WebDriverWait`) handle client-side AJAX re-renders and JavaScript browser alerts (`acceptAlertAndGetText()`).
4. **Automated Screenshot Capture:** Failed tests trigger `TestFailureListener` to capture PNG screenshots in `screenshots/failures/`.
