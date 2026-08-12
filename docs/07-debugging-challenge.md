# Intentional Failure and Debugging Evidence

## Objective

Demonstrate a controlled test failure, isolate its cause, correct it, and rerun
the test without confusing the exercise with a nopCommerce defect.

## Failure introduced

Commit `36f3657` added `IntentionalFailureTest`. Its fixed input was the captured
generic login error:

```text
Login was unsuccessful. Please correct the errors and try again.
```

The assertion intentionally searched for `No customer account found`, producing:

```text
Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
AssertionError: Intentional failure: the expected message does not match the captured contract
expected [true] but found [false]
```

## Diagnosis

- **Observed:** a deterministic assertion failure at line 12.
- **Expected by the test:** a specific account-not-found phrase.
- **Actual contract:** a generic unsuccessful-login phrase.
- **Root cause:** the automated expectation did not match the known input. The
  test data and TestNG runner were functioning correctly.

## Fix and verification

The assertion was changed to search for `Login was unsuccessful`, which is the
stable part of the captured contract. The focused command is:

```bash
mvn -Dtest=lk.ucsc.nopcommerce.debug.IntentionalFailureTest test
```

The corrected test ran with one test and zero failures. The consecutive Git
commits retain both the deliberate failure and its correction for assessment.

