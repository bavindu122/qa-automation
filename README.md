# nopCommerce QA Automation Assignment

QA analysis and browser automation project for the public
[nopCommerce demo store](https://demo.nopcommerce.com/).

The project follows the UCSC Practical Take-Home Assignment requirements and
uses Java, Selenium WebDriver, TestNG, Maven, and the Page Object Model.

## Assignment progress

- [x] Website selection
- [x] Requirement analysis
- [x] At least 15 manual test scenarios
- [x] Five automation decisions
- [x] Framework design
- [x] Five automated scenarios
- [ ] Intentional failure and debugging evidence
- [ ] Five-minute reflection video guide
- [ ] PDF report
- [ ] Final repository audit

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

```bash
mvn clean test
```

Run with a visible browser:

```bash
mvn clean test -Dheadless=false
```

Environment and catalog data can be changed in
`src/test/resources/config.properties` or overridden using `-Dkey=value`.

## Responsible demo usage

The test suite targets only the official public demo environment. It will use
synthetic test data, avoid real payment information, and keep tests independent
where practical. The demo may contain changes from other users and is restored
to its initial state every hour.
