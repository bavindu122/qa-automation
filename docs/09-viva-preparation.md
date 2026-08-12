# Viva Preparation

Use these prompts to check understanding. Answer naturally and refer to the
code; do not memorise sentences.

## Project decisions

**Why nopCommerce?** It provides realistic, public, non-production e-commerce
flows across authentication, catalog, cart, comparison, and checkout. Its demo
data also avoids using real accounts or payments.

**Why exactly these five tests?** Together they cover a negative security-facing
check, a high-frequency discovery path, cart state, cross-page comparison state,
and the revenue-critical guest checkout journey. They offer more risk coverage
than five similar happy paths.

**Why not automate every manual scenario?** Automation has implementation and
maintenance cost. Visual/usability checks, email-dependent flows, and unstable
or low-value cases can be more effective as manual tests until their repetition
and risk justify automation.

## Framework

**What is Page Object Model?** Each page class owns its locators and user-facing
actions. Tests call those actions to express behaviour. A UI locator change is
therefore corrected in one Page Object instead of every test.

**Why explicit waits instead of `Thread.sleep`?** Explicit waits stop as soon as
the required state is ready and fail with a useful timeout if it never becomes
ready. Fixed sleeps are slow when unnecessary and still unreliable when too
short.

**How are tests isolated?** `BaseTest` creates a new WebDriver before each test,
opens the base URL, and quits it afterward. That avoids cookies, carts, and
comparison state leaking between test methods.

**How is configuration handled?** Defaults live in `config.properties`; Java
system properties override them. The same code can therefore switch headless
mode or catalog values without source edits.

**What does the listener do?** On a TestNG failure it takes a timestamped PNG
from the current driver and writes it under `screenshots/failures/` for triage.

## Test reasoning

**What makes an assertion useful?** It checks a stable, observable outcome tied
to the requirement and includes a diagnostic message. For example, cart tests
check both product identity and quantity, not only navigation.

**How do you distinguish product, test, and environment defects?** Reproduce the
failure, inspect the page and stack trace, identify the first divergence, and
compare it with the expected contract. A Cloudflare challenge before any app
element is an environment blocker; a wrong expected phrase is a test defect.

**What was the intentional failure?** A deterministic string contained a generic
login error, while the assertion searched for a different account-specific
message. The focused TestNG output isolated the mismatch; correcting the stable
phrase made the test pass.

## Likely improvement questions

- Add CI only when the target explicitly permits automated access.
- Introduce tags/groups for smoke and regression execution.
- Produce richer HTML reporting and retain screenshots as CI artifacts.
- Add test-data builders and cleanup for a dedicated test environment.
- Expand browser coverage after stabilising Chrome execution.

