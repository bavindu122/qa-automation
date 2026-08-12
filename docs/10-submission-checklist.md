# Final Submission Checklist

## Repository and source

- [x] Local Git repository uses the `main` branch.
- [x] Analysis, scenarios, decisions, framework, tests, debugging, and report are
  separated into meaningful commits.
- [x] Java main and test sources compile successfully.
- [x] Five selected Selenium/TestNG scenarios are implemented using Page Objects.
- [x] The intentional failure and its fix remain visible in consecutive commits.
- [x] `AI_USAGE.md` discloses assistance and student responsibility.
- [ ] Create the GitHub repository and push `main` after confirming repository
  name, owner, and visibility.
- [ ] Add the final GitHub URL to the LMS submission and report if required.

## Evidence and execution

- [x] Recorded Selenium attempt is classified honestly as blocked by Cloudflare.
- [x] An authentic failure-listener screenshot is stored under
  `evidence/environment/` and embedded in the report.
- [x] Corrected controlled-debugging test passes in a focused run.
- [ ] Rerun all five tests when the public demo permits automated access.
- [ ] Update `docs/06-test-execution.md` and the report if the final result changes.
- [ ] Execute the 18 manual scenarios and fill the execution record with actual
  pass/fail/blocked outcomes and evidence paths.

## Required artifacts

- [x] PDF report generated and visually verified.
- [x] Editable DOCX report source included.
- [x] Video reflection guide and viva preparation included.
- [x] Clean project ZIP generated without `.git`, Maven output, or local test output.
- [ ] Record the student's own approximately five-minute reflection video.
- [ ] Upload the video and add its permitted sharing link.
- [ ] Confirm the lecturer's naming convention and add student/index identifiers
  if required.
- [ ] Rebuild the final ZIP after adding any required links or evidence.

## Final integrity check

Run immediately before submission:

```bash
git status --short
git log --oneline --reverse
mvn test-compile
```

The working tree should be clean, compilation should succeed, and the ZIP/PDF
timestamps should correspond to the final committed content.

