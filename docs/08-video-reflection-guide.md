# Five-Minute Video Reflection Guide

The student should record this reflection in their own words while sharing the
repository and a short test run. Do not read the script mechanically.

## 0:00-0:35 - Context and scope

- Introduce the nopCommerce demo and the assignment objective.
- State why a demo store was suitable: realistic public e-commerce workflows,
  repeatable data, no real customer or payment data, and broad test coverage.
- Show the repository and its milestone commits.

## 0:35-1:20 - Analysis and manual design

- Open `docs/02-requirement-analysis.md` and explain one functional requirement
  and one quality risk.
- Open `docs/03-manual-test-scenarios.md`; mention that 18 scenarios cover
  authentication, search, cart, comparison, checkout, and validation.
- Explain how risk and business impact influenced priority.

## 1:20-2:05 - Automation decisions

- Show `docs/04-automation-decision.md`.
- Name the five selected flows: invalid login, search, add to cart, compare, and
  guest checkout.
- Explain that the set balances fast checks, negative coverage, stateful flows,
  and one end-to-end business journey.
- Give one example intentionally left manual and explain why.

## 2:05-3:15 - Framework walkthrough

- Show `pom.xml`, `BaseTest`, `DriverFactory`, and the Page Object package.
- Explain the separation: tests express intent, Page Objects own locators and
  interactions, configuration owns environment data, and the listener captures
  screenshots on failure.
- Open one test and trace its calls through the relevant Page Objects.

## 3:15-4:05 - Execution and honest reporting

- Run `mvn clean test -Dheadless=false` if the demo is accessible.
- Show TestNG/Surefire results and any failure screenshot.
- If Cloudflare is still present, explain that this is an environment blocker,
  not a product defect or a pass, and show `docs/06-test-execution.md`.

## 4:05-4:40 - Debugging exercise

- Show commits `36f3657` and `dc535b2`.
- Explain the mismatched login-error expectation, the assertion output, how the
  actual contract identified the root cause, and the focused passing rerun.

## 4:40-5:00 - Reflection

- State one lesson: stable assertions must match observable contracts, and test
  failures must be classified before changing code.
- State one future improvement: CI execution with an approved test environment,
  richer reporting, or resilient test data provisioning.
- Mention the AI disclosure and personal responsibility for every decision.

## Before recording

- Rerun the suite and update the execution record with the actual result.
- Close unrelated tabs and hide notifications or personal information.
- Keep the repository, commit history, report, and terminal ready.
- Record at readable zoom and verify audio before the final take.

