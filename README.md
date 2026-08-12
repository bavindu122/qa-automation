# Demoblaze QA Automation Assignment

QA analysis and browser automation project for the public [Demoblaze Store](https://www.demoblaze.com/).

The project follows the UCSC Practical Take-Home Assignment requirements and uses Java, Selenium WebDriver, TestNG, Maven, and the Page Object Model (POM).

## Assignment progress

- [x] Website selection (Demoblaze - free of Cloudflare anti-bot protection)
- [x] Requirement analysis
- [x] Manual test scenarios
- [x] Five automation decisions
- [x] Framework design (Page Object Model)
- [x] Five automated scenarios (Login rejection, Category filtering, Add to cart, Remove from cart, Order checkout)
- [x] Intentional failure and debugging evidence
- [x] Final repository audit

## Repository structure

```text
docs/                 Assignment analysis and evidence
src/main/java/        Page objects and reusable framework code
src/test/java/        TestNG test classes
src/test/resources/   Test configuration and suite files
```

## Prerequisites

- JDK 21
- Maven 3.9 or newer
- Current stable Google Chrome

Selenium Manager resolves the matching ChromeDriver automatically.

## Run the suite

Run headless:

```bash
mvn clean test
```

Run with a visible browser:

```bash
mvn clean test -Dheadless=false
```

Environment and catalog data can be changed in `src/test/resources/config.properties` or overridden using `-Dkey=value`.

## Automated Scenarios Overview

1. **AUT-01 / MTS-01**: Reject invalid login credentials (modal input & alert assertion).
2. **AUT-02 / MTS-02**: Filter products by category (Laptops filter validation).
3. **AUT-03 / MTS-03**: Add a simple product to cart (Samsung galaxy s6 add & cart verification).
4. **AUT-04 / MTS-04**: Remove a product from cart (Cart item deletion & list verification).
5. **AUT-05 / MTS-05**: Complete product order checkout (Place order modal, synthetic buyer details & confirmation popup).
