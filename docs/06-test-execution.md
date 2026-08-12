# Test Execution Record

## Verification environment

| Item | Value |
|---|---|
| Date | 2026-08-12 |
| Operating system | macOS 26.5.1 (Apple Silicon) |
| Java | OpenJDK 21.0.12 |
| Maven | 3.9.11 |
| Selenium | 4.44.0 |
| TestNG | 7.12.0 |
| Chrome / ChromeDriver | 151.0.7922.76 / 151.0.7922.138 |

## Results

1. `mvn test-compile` completed successfully: all framework, Page Object,
   listener, and TestNG source files compiled.
2. The five selected flows were inspected against the live nopCommerce demo to
   confirm the routes, controls, product data, and expected messages used by the
   Page Objects.
3. A full Selenium run on 2026-08-12 launched the matching ChromeDriver, but
   all five tests were stopped by nopCommerce's Cloudflare **Performing security
   verification** page. Each failure occurred at the first application element;
   the screenshot listener captured the challenge page.

This run is recorded as **blocked by the test environment**, not as a product
defect and not as a passed test run. The suite must be rerun when the public demo
permits automated browser access. No attempt was made to bypass the website's
security control.

## Rerun commands

```bash
mvn clean test
mvn clean test -Dheadless=false
```

Expected successful result: five tests run, zero failures, zero errors, and zero
skips. Inspect `target/surefire-reports/` and `screenshots/failures/` after each
run before reporting the outcome.

