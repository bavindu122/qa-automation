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
- [x] Intentional failure and debugging evidence
- [x] Five-minute reflection video guide
- [x] PDF report
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

## Assignment deliverables

- `output/report/nopcommerce-qa-automation-report.pdf` - rendered assignment report
- `output/report/nopcommerce-qa-automation-report.docx` - editable report source
- `docs/03-manual-test-scenarios.md` - 18 detailed manual scenarios
- `docs/04-automation-decision.md` - selection and exclusion rationale
- `docs/08-video-reflection-guide.md` - five-minute recording guide
- `docs/09-viva-preparation.md` - viva understanding prompts
- `AI_USAGE.md` - AI assistance disclosure and activity log

## Responsible demo usage

The test suite targets only the official public demo environment. It will use
synthetic test data, avoid real payment information, and keep tests independent
where practical. The demo may contain changes from other users and is restored
to its initial state every hour.
