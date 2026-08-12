"""Generate the Demoblaze SQA assignment report as a polished PDF."""

from datetime import datetime
from pathlib import Path
from xml.sax.saxutils import escape

from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import LETTER
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import inch
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.pdfbase import pdfmetrics
from reportlab.platypus import (
    BaseDocTemplate,
    Frame,
    KeepTogether,
    PageBreak,
    PageTemplate,
    Paragraph,
    Spacer,
    Table,
    TableStyle,
)


ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / "output" / "report" / "demoblaze-qa-automation-report.pdf"
OUT.parent.mkdir(parents=True, exist_ok=True)

NAVY = colors.HexColor("#17324D")
BLUE = colors.HexColor("#2474A6")
PALE_BLUE = colors.HexColor("#EAF2F8")
PALE_GREEN = colors.HexColor("#EAF7EF")
PALE_GOLD = colors.HexColor("#FFF5D6")
INK = colors.HexColor("#243447")
MUTED = colors.HexColor("#687786")
GRID = colors.HexColor("#BCC8D2")


def register_fonts():
    candidates = [
        ("ReportSans", "/System/Library/Fonts/Supplemental/Arial.ttf"),
        ("ReportSans-Bold", "/System/Library/Fonts/Supplemental/Arial Bold.ttf"),
    ]
    for name, path in candidates:
        if Path(path).exists():
            pdfmetrics.registerFont(TTFont(name, path))
    return "ReportSans" if "ReportSans" in pdfmetrics.getRegisteredFontNames() else "Helvetica"


FONT = register_fonts()
BOLD = "ReportSans-Bold" if "ReportSans-Bold" in pdfmetrics.getRegisteredFontNames() else "Helvetica-Bold"


def p(text, style):
    return Paragraph(text, style)


def cell(text, style):
    return Paragraph(escape(str(text)).replace("\n", "<br/>"), style)


styles = getSampleStyleSheet()
styles.add(ParagraphStyle(
    name="ReportBody", fontName=FONT, fontSize=9.5, leading=13.2,
    textColor=INK, spaceAfter=6,
))
styles.add(ParagraphStyle(
    name="ReportSmall", fontName=FONT, fontSize=8, leading=10.2,
    textColor=INK,
))
styles.add(ParagraphStyle(
    name="ReportTiny", fontName=FONT, fontSize=7.3, leading=9,
    textColor=INK,
))
styles.add(ParagraphStyle(
    name="ReportH1", fontName=BOLD, fontSize=16, leading=19,
    textColor=BLUE, spaceBefore=10, spaceAfter=7, keepWithNext=True,
))
styles.add(ParagraphStyle(
    name="ReportH2", fontName=BOLD, fontSize=12, leading=15,
    textColor=NAVY, spaceBefore=8, spaceAfter=5, keepWithNext=True,
))
styles.add(ParagraphStyle(
    name="ReportH3", fontName=BOLD, fontSize=10.2, leading=13,
    textColor=NAVY, spaceBefore=6, spaceAfter=3, keepWithNext=True,
))
styles.add(ParagraphStyle(
    name="ReportCaption", fontName=FONT, fontSize=8, leading=10,
    textColor=MUTED, alignment=TA_CENTER, spaceBefore=3, spaceAfter=7,
))
styles.add(ParagraphStyle(
    name="ReportCallout", fontName=BOLD, fontSize=10.5, leading=14,
    textColor=NAVY, alignment=TA_CENTER, spaceAfter=0,
))
styles.add(ParagraphStyle(
    name="ReportBullet", parent=styles["ReportBody"], leftIndent=16,
    firstLineIndent=-8, bulletIndent=4, spaceAfter=3,
))


def data_table(headers, rows, widths, font_size=8, header_fill=PALE_BLUE):
    body_style = styles["ReportTiny"] if font_size < 8 else styles["ReportSmall"]
    data = [[cell(value, body_style) for value in headers]]
    data.extend([[cell(value, body_style) for value in row] for row in rows])
    table = Table(data, colWidths=widths, repeatRows=1, hAlign="LEFT")
    table.setStyle(TableStyle([
        ("FONTNAME", (0, 0), (-1, 0), BOLD),
        ("BACKGROUND", (0, 0), (-1, 0), header_fill),
        ("TEXTCOLOR", (0, 0), (-1, 0), NAVY),
        ("GRID", (0, 0), (-1, -1), 0.45, GRID),
        ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),
        ("LEFTPADDING", (0, 0), (-1, -1), 6),
        ("RIGHTPADDING", (0, 0), (-1, -1), 6),
        ("TOPPADDING", (0, 0), (-1, -1), 5),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
    ]))
    return table


def bullet(text):
    return Paragraph("• " + escape(text), styles["ReportBullet"])


def scenario_block(scenario_id, title, priority, preconditions, steps, expected):
    content = [
        p(f"{scenario_id} - {escape(title)}", styles["ReportH3"]),
        p(f"<b>Priority:</b> {priority} &nbsp;&nbsp; <b>Preconditions:</b> {escape(preconditions)}", styles["ReportSmall"]),
        p(f"<b>Steps:</b> {escape(steps)}", styles["ReportSmall"]),
        p(f"<b>Expected result:</b> {escape(expected)}", styles["ReportSmall"]),
    ]
    box = Table([[content]], colWidths=[6.5 * inch], hAlign="LEFT")
    box.setStyle(TableStyle([
        ("BACKGROUND", (0, 0), (-1, -1), colors.white),
        ("BOX", (0, 0), (-1, -1), 0.6, GRID),
        ("LEFTPADDING", (0, 0), (-1, -1), 9),
        ("RIGHTPADDING", (0, 0), (-1, -1), 9),
        ("TOPPADDING", (0, 0), (-1, -1), 4),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
    ]))
    return [box, Spacer(1, 6)]


def draw_header_footer(canvas, doc):
    canvas.saveState()
    page = canvas.getPageNumber()
    if page > 1:
        canvas.setFont(FONT, 8)
        canvas.setFillColor(MUTED)
        canvas.drawString(0.75 * inch, 10.55 * inch, "UCSC SQA | Demoblaze QA Automation")
        canvas.drawRightString(7.75 * inch, 10.55 * inch, "Practical Take-Home Assignment")
        canvas.setStrokeColor(GRID)
        canvas.setLineWidth(0.4)
        canvas.line(0.75 * inch, 10.42 * inch, 7.75 * inch, 10.42 * inch)
    canvas.setFont(FONT, 8)
    canvas.setFillColor(MUTED)
    canvas.drawRightString(7.75 * inch, 0.48 * inch, f"Page {page}")
    canvas.restoreState()


def add_cover(story):
    story.extend([
        Spacer(1, 1.38 * inch),
        p("PRACTICAL TAKE-HOME ASSIGNMENT", ParagraphStyle(
            "CoverKicker", fontName=BOLD, fontSize=10, leading=12,
            textColor=BLUE, alignment=TA_CENTER, spaceAfter=15,
        )),
        p("Demoblaze QA Automation", ParagraphStyle(
            "CoverTitle", fontName=BOLD, fontSize=29, leading=34,
            textColor=NAVY, alignment=TA_CENTER, spaceAfter=10,
        )),
        p("Software Quality Assurance Analysis, Manual Test Design<br/>and Selenium WebDriver Implementation", ParagraphStyle(
            "CoverSubtitle", fontName=FONT, fontSize=13, leading=18,
            textColor=MUTED, alignment=TA_CENTER, spaceAfter=48,
        )),
        data_table(
            ["Report detail", "Value"],
            [
                ("Student", "Bavindu Shamen"),
                ("System Under Test", "Demoblaze E-Commerce Store"),
                ("URL", "https://www.demoblaze.com/"),
                ("Technology", "Java 21 | Selenium 4.44 | TestNG 7.12 | Maven | POM"),
                ("Verified execution", "13 August 2026 | 5 passed | 0 failed | 0 skipped"),
            ],
            [1.65 * inch, 4.85 * inch],
            font_size=8,
        ),
        Spacer(1, 26),
        Table([[p("CURRENT RESULT: BUILD SUCCESS", styles["ReportCallout"])]],
              colWidths=[6.5 * inch], style=TableStyle([
                  ("BACKGROUND", (0, 0), (-1, -1), PALE_GREEN),
                  ("BOX", (0, 0), (-1, -1), 0.8, colors.HexColor("#78B58B")),
                  ("TOPPADDING", (0, 0), (-1, -1), 10),
                  ("BOTTOMPADDING", (0, 0), (-1, -1), 10),
              ])),
        Spacer(1, 12),
        p("Prepared from the current Markdown answers, implementation, Git history, and a fresh local Selenium execution.", styles["ReportCaption"]),
        PageBreak(),
    ])


def build():
    doc = BaseDocTemplate(
        str(OUT), pagesize=LETTER,
        leftMargin=0.75 * inch, rightMargin=0.75 * inch,
        topMargin=0.72 * inch, bottomMargin=0.68 * inch,
        title="Demoblaze QA Automation Assignment Report",
        author="Bavindu Shamen",
        subject="UCSC Software Quality Assurance practical take-home assignment",
        keywords="Demoblaze, SQA, Selenium, TestNG, Page Object Model",
    )
    frame = Frame(doc.leftMargin, doc.bottomMargin, doc.width, doc.height, id="normal")
    doc.addPageTemplates([PageTemplate(id="report", frames=frame, onPage=draw_header_footer)])
    story = []
    add_cover(story)

    story.extend([
        p("Executive summary", styles["ReportH1"]),
        p("This report documents the analysis and automation of the public Demoblaze e-commerce application. The project covers website selection, requirements and risks, fifteen manual test scenarios, five risk-based automation decisions, Page Object Model architecture, Selenium/TestNG implementation, execution evidence, debugging, and AI usage disclosure.", styles["ReportBody"]),
        p("The current suite was rerun on 13 August 2026. All five selected tests passed in headless Chrome: five tests, zero failures, zero errors, and zero skipped. The result validates invalid-login handling, AJAX category filtering, adding and removing a cart item, and completing a synthetic order.", styles["ReportBody"]),
        p("The application replaced the initial nopCommerce choice after Cloudflare security verification prevented reliable browser automation. Demoblaze did not present that blocker during the current execution and supplied the dynamic catalog, modal, alert, cart, and checkout behaviours needed for the assignment.", styles["ReportBody"]),

        p("1. Website selection", styles["ReportH1"]),
        data_table(
            ["Item", "Decision"],
            [
                ("Application", "Demoblaze E-Commerce Store"),
                ("URL", "https://www.demoblaze.com/"),
                ("Type", "Business-to-consumer e-commerce demo"),
                ("Environment", "Public demonstration environment"),
                ("Stack", "Java, Selenium WebDriver, TestNG, Maven, Page Object Model"),
            ], [1.55 * inch, 4.95 * inch],
        ),
        Spacer(1, 7),
        p("Selection rationale", styles["ReportH2"]),
        p("Demoblaze provides a realistic customer journey without requiring production access or real payment information. Its feature set includes user authentication, category filtering for phones, laptops and monitors, product details, cart management, deletion, checkout form entry, JavaScript alerts, and order-confirmation dialogs.", styles["ReportBody"]),
        p("The site naturally separates into reusable page and modal objects and exposes both positive and negative test paths. During the verified run it supported stable headless execution, making it a better automation target than the original site.", styles["ReportBody"]),
        p("Scope", styles["ReportH2"]),
        data_table(
            ["In scope", "Out of scope"],
            [
                ("Home and category navigation", "Real financial transactions"),
                ("Negative login validation", "External email delivery"),
                ("Product details and pricing", "Performance and load testing"),
                ("Cart add/remove behaviour", "Administration backend"),
                ("Synthetic checkout and confirmation", "Production data or accounts"),
            ], [3.25 * inch, 3.25 * inch],
        ),
    ])

    story.extend([
        p("2. Requirement analysis", styles["ReportH1"]),
        p("Application objectives", styles["ReportH2"]),
        bullet("Provide an intuitive electronics catalog organised by category."),
        bullet("Allow visitors to view product information and add or remove shopping-cart items."),
        bullet("Support a synthetic checkout and display confirmation metadata."),
        bullet("Provide sign-up and login interactions with clear modal or alert feedback."),
        p("Key functional areas", styles["ReportH2"]),
        data_table(
            ["ID", "Area", "Expected behaviour"],
            [
                ("FR-01", "Catalog", "Display products and filter the grid by Phones, Laptops, or Monitors."),
                ("FR-02", "Product", "Display name, price, description, and an Add to cart action."),
                ("FR-03", "Cart", "Persist item titles and prices, calculate totals, and allow deletion."),
                ("FR-04", "Checkout", "Collect buyer data and display purchase confirmation with order metadata."),
                ("FR-05", "Authentication", "Accept modal input and provide JavaScript alert feedback."),
            ], [0.7 * inch, 1.15 * inch, 4.65 * inch], font_size=7.8,
        ),
        Spacer(1, 7),
        p("Quality risks and mitigations", styles["ReportH2"]),
        data_table(
            ["Risk", "Impact", "Mitigation"],
            [
                ("AJAX delay during category filtering", "High", "Wait for stale product elements and repoll the refreshed grid."),
                ("Delayed cart DOM population", "High", "Wait for cart.html and query rows only after the dynamic response."),
                ("JavaScript alerts block WebDriver", "Medium", "Wait for alertIsPresent, capture its text, then accept it."),
                ("Shared public demo state", "Medium", "Create prerequisites within each test and use synthetic input."),
                ("Browser/CDP version drift", "Low", "Use Selenium Manager; record warnings separately from functional outcomes."),
            ], [2.4 * inch, 0.8 * inch, 3.3 * inch], font_size=7.8,
        ),
        p("Assumptions", styles["ReportH2"]),
        bullet("The public site remains reachable over HTTPS."),
        bullet("Chrome executes JavaScript and supports both headless and headed modes."),
        bullet("Catalog and cart content are rendered asynchronously using client-side requests."),
        bullet("Synthetic checkout does not cause a real charge or fulfilment request."),
    ])

    scenarios = [
        ("MTS-01", "Auth", "Reject invalid login credentials", "High", "AUT-01"),
        ("MTS-02", "Catalog", "Filter products by Laptops category", "Medium", "AUT-02"),
        ("MTS-03", "Cart", "Add a product to the shopping cart", "High", "AUT-03"),
        ("MTS-04", "Cart", "Remove a product from the shopping cart", "High", "AUT-04"),
        ("MTS-05", "Checkout", "Complete guest order with valid synthetic details", "High", "AUT-05"),
        ("MTS-06", "Auth", "Sign up with an existing username", "Medium", "Manual"),
        ("MTS-07", "Catalog", "Filter products by Phones category", "Medium", "Manual"),
        ("MTS-08", "Catalog", "Filter products by Monitors category", "Medium", "Manual"),
        ("MTS-09", "Product", "View details for Sony vaio i5", "Low", "Manual"),
        ("MTS-10", "Cart", "Calculate total price for multiple items", "Medium", "Manual"),
        ("MTS-11", "Cart", "Open an empty cart", "Low", "Manual"),
        ("MTS-12", "Checkout", "Submit checkout with blank required fields", "High", "Manual"),
        ("MTS-13", "Navigation", "Return Home using the brand link", "Low", "Manual"),
        ("MTS-14", "Contact", "Open the Contact modal", "Low", "Manual"),
        ("MTS-15", "About Us", "Play video in the About Us modal", "Low", "Manual"),
    ]
    story.extend([
        PageBreak(),
        p("3. Manual test scenario design", styles["ReportH1"]),
        p("The following fifteen scenarios cover authentication, catalog discovery, product details, cart state, checkout validation, navigation, contact, and media interaction. High priority denotes a core security, cart, or checkout journey; Medium covers important supporting behaviour; Low covers informational or convenience behaviour.", styles["ReportBody"]),
        data_table(["ID", "Module", "Scenario", "Priority", "Execution"], scenarios,
                   [0.72 * inch, 0.82 * inch, 3.6 * inch, 0.7 * inch, 0.66 * inch], font_size=7.2),
        Spacer(1, 9),
        p("Detailed scenarios", styles["ReportH2"]),
    ])

    details = [
        ("MTS-01", "Reject invalid login credentials", "High", "Visitor is on the Home page.", "Open Log in; enter invalid_user and wrong_pass; submit.", "An alert states that the user does not exist or the password is wrong, and no authenticated state is created."),
        ("MTS-02", "Filter products by Laptops category", "Medium", "Home catalog is loaded.", "Select Laptops from the category sidebar; wait for the grid to refresh.", "Only laptop products are displayed and Sony vaio i5 is present."),
        ("MTS-03", "Add a product to the shopping cart", "High", "Home page is available and the cart prerequisite is controlled.", "Open Samsung galaxy s6; select Add to cart; accept Product added alert; open Cart.", "The cart contains Samsung galaxy s6 with its displayed price."),
        ("MTS-04", "Remove a product from the shopping cart", "High", "Samsung galaxy s6 has been added during this test flow.", "Open Cart; select Delete for Samsung galaxy s6; wait for the row to disappear.", "The product is absent and the cart state/total is updated."),
        ("MTS-05", "Complete guest order with valid synthetic details", "High", "A product exists in the cart.", "Open Cart and Place Order; enter synthetic name, country, city, card, month, and year; select Purchase.", "A Thank you for your purchase confirmation displays an order ID and amount."),
        ("MTS-06", "Sign up with an existing username", "Medium", "A known demo username has already been registered.", "Open Sign up; enter the existing username and a password; submit.", "An alert explains that the user already exists and no duplicate account is created."),
        ("MTS-07", "Filter products by Phones category", "Medium", "Home catalog is loaded.", "Select Phones and wait for the product grid to refresh.", "The refreshed grid contains phone products and excludes laptop/monitor-only results."),
        ("MTS-08", "Filter products by Monitors category", "Medium", "Home catalog is loaded.", "Select Monitors and wait for the grid to refresh.", "The refreshed grid contains monitor products such as Apple monitor 24."),
        ("MTS-09", "View details for Sony vaio i5", "Low", "Laptops are visible.", "Open Sony vaio i5 from the grid.", "The details page shows the correct name, price, description, and Add to cart action."),
        ("MTS-10", "Calculate total price for multiple items", "Medium", "Two known products have been added to the cart.", "Open Cart; record each line price; compare their sum with the displayed total.", "The total equals the sum of the displayed item prices."),
        ("MTS-11", "Open an empty cart", "Low", "No items have been added in the current clean session.", "Select Cart from the navigation bar.", "The cart page opens without item rows and shows a zero/blank total state."),
        ("MTS-12", "Submit checkout with blank required fields", "High", "A product exists in the cart and Place Order is open.", "Leave required buyer fields blank and select Purchase.", "The order is not completed and a validation alert requests the required information."),
        ("MTS-13", "Return Home using the brand link", "Low", "Visitor is on a product or cart page.", "Select the PRODUCT STORE brand link.", "The browser returns to the Home catalog and products become visible."),
        ("MTS-14", "Open the Contact modal", "Low", "Home page is available.", "Select Contact from the navigation bar.", "A Contact modal appears with email, name, message, Send message, and Close controls."),
        ("MTS-15", "Play video in the About Us modal", "Low", "Home page is available and media playback is permitted.", "Open About us; select the video play control; then close the modal.", "The modal opens, the video begins playback, and closing returns control to the page."),
    ]
    for item in details:
        story.extend(scenario_block(*item))

    decisions = [
        ("AUT-01", "MTS-01", "Invalid login", "Negative security boundary; stable alert assertion."),
        ("AUT-02", "MTS-02", "Laptops filter", "Core AJAX navigation and refreshed-grid validation."),
        ("AUT-03", "MTS-03", "Add to cart", "Primary e-commerce state transition and persistence."),
        ("AUT-04", "MTS-04", "Remove from cart", "High-value DOM deletion and cart-state update."),
        ("AUT-05", "MTS-05", "Guest checkout", "End-to-end modal form and confirmation journey."),
    ]
    story.extend([
        PageBreak(),
        p("4. Automation decision", styles["ReportH1"]),
        p("The selected set balances a negative authentication check, asynchronous catalog behaviour, two cart state transitions, and the end-to-end purchasing journey. The remaining scenarios have lower regression return, duplicate category mechanics, or require human visual/media judgement.", styles["ReportBody"]),
        data_table(["Automation", "Manual", "Scenario", "Selection rationale"], decisions,
                   [0.9 * inch, 0.8 * inch, 1.55 * inch, 3.25 * inch], font_size=7.8),
        p("Exclusion rationale", styles["ReportH2"]),
        bullet("MTS-06 depends on a persistent username and shared demo state."),
        bullet("MTS-07 and MTS-08 repeat the category mechanism already covered by AUT-02."),
        bullet("MTS-09, MTS-13, and MTS-14 are inexpensive to verify manually."),
        bullet("MTS-10 and MTS-12 are strong future automation candidates after the core suite."),
        bullet("MTS-15 requires visual/media judgement beyond the initial functional regression scope."),

        p("5. Framework design", styles["ReportH1"]),
        p("The framework uses Java 21, Selenium WebDriver 4.44.0, TestNG 7.12.0, Maven 3.9+, and Page Object Model. The Java package retains the legacy lk.ucsc.nopcommerce namespace after the SUT migration; behaviour and configuration now target Demoblaze.", styles["ReportBody"]),
        data_table(
            ["Component", "Responsibility"],
            [
                ("ConfigReader", "Loads config.properties and system-property overrides."),
                ("DriverFactory", "Owns ThreadLocal WebDriver lifecycle and Chrome options."),
                ("BasePage", "Provides explicit waits, interactions, and alert handling."),
                ("HomePage", "Navigation, category filtering, product discovery, and modal access."),
                ("ProductPage", "Product detail verification and Add to cart action."),
                ("CartPage", "Cart rows, item deletion, total state, and checkout trigger."),
                ("LoginModal", "Invalid credential submission and alert capture."),
                ("PlaceOrderModal", "Synthetic buyer form and purchase confirmation."),
                ("CustomerOrder", "Immutable synthetic order data."),
                ("BaseTest / listener", "Per-test browser isolation, teardown, and failure screenshots."),
            ], [1.6 * inch, 4.9 * inch], font_size=7.8,
        ),
        p("Key design controls", styles["ReportH2"]),
        bullet("Locators and interactions are isolated in Page Objects; TestNG tests own assertions."),
        bullet("WebDriverWait handles AJAX re-rendering, URL changes, visibility, clickability, and alerts."),
        bullet("Each test starts with a fresh browser to prevent cart or authentication state leakage."),
        bullet("A failure listener writes timestamped screenshots for root-cause analysis."),
    ])

    story.extend([
        PageBreak(),
        p("6. Automated implementation", styles["ReportH1"]),
        data_table(
            ["ID", "Page flow", "Principal assertion"],
            [
                ("AUT-01", "HomePage -> LoginModal", "Alert contains user-not-found or wrong-password feedback."),
                ("AUT-02", "HomePage category filter", "Refreshed product names contain Sony vaio i5."),
                ("AUT-03", "HomePage -> ProductPage -> CartPage", "Cart contains Samsung galaxy s6."),
                ("AUT-04", "ProductPage -> CartPage deletion", "Cart no longer contains Samsung galaxy s6."),
                ("AUT-05", "CartPage -> PlaceOrderModal", "Confirmation thanks the buyer and includes order metadata."),
            ], [0.85 * inch, 2.65 * inch, 3.0 * inch], font_size=7.8,
        ),
        Spacer(1, 8),
        p("Test data and independence", styles["ReportH2"]),
        p("Changeable catalog and credential data live in config.properties. Invalid login uses synthetic credentials. Checkout uses CustomerOrder.validSyntheticOrder(), avoiding personal or real payment information. Add, remove, and checkout scenarios create their own cart prerequisites within a new browser session.", styles["ReportBody"]),

        p("7. Test execution", styles["ReportH1"]),
        data_table(
            ["Execution detail", "Observed value"],
            [
                ("Date/time", "13 August 2026, 00:57 Asia/Colombo"),
                ("Mode", "Headless Chrome through Selenium WebDriver"),
                ("Tests", "5"),
                ("Failures / Errors / Skips", "0 / 0 / 0"),
                ("Suite time", "44.60 seconds"),
                ("Maven result", "BUILD SUCCESS"),
            ], [2.25 * inch, 4.25 * inch], font_size=8.2,
        ),
        Spacer(1, 9),
        Table([[p("VERIFIED: 5 PASSED | 0 FAILED | 0 ERRORS | 0 SKIPPED", styles["ReportCallout"])]],
              colWidths=[6.5 * inch], style=TableStyle([
                  ("BACKGROUND", (0, 0), (-1, -1), PALE_GREEN),
                  ("BOX", (0, 0), (-1, -1), 0.8, colors.HexColor("#78B58B")),
                  ("TOPPADDING", (0, 0), (-1, -1), 10),
                  ("BOTTOMPADDING", (0, 0), (-1, -1), 10),
              ])),
        Spacer(1, 8),
        p("Execution note", styles["ReportH2"]),
        p("Selenium emitted a warning that Chrome DevTools Protocol version 151 did not have an exact Selenium match and used the nearest available version 148. The suite does not depend on DevTools-specific APIs, so this warning did not affect the five WebDriver tests or their passing result. It should be rechecked after a Selenium upgrade.", styles["ReportBody"]),

        p("8. Debugging challenge", styles["ReportH1"]),
        p("Initial symptom", styles["ReportH2"]),
        p("The original nopCommerce suite timed out while waiting for application elements because a Cloudflare security-verification page replaced the expected storefront UI in automated Chrome sessions.", styles["ReportBody"]),
        p("Diagnosis and corrective work", styles["ReportH2"]),
        bullet("Failure screenshots revealed the security-verification page before the first application interaction."),
        bullet("The SUT was changed to Demoblaze, a demo environment that allowed the required browser flows during execution."),
        bullet("Page Objects were redesigned for Demoblaze modals, alerts, dynamic catalog rendering, cart rows, and checkout."),
        bullet("Explicit waits for staleness, URLs, elements, and alerts resolved AJAX timing and stale-element failures."),
        p("Verification", styles["ReportH2"]),
        p("After the migration and synchronization fixes, the fresh headless run completed with five tests and no failures, errors, or skips. The result shows that the environmental blocker and subsequent test-synchronization issues were correctly separated and addressed.", styles["ReportBody"]),
    ])

    commits = [
        ("63982d0", "Migrate the automated suite to Demoblaze and implement the new Page Objects."),
        ("ad8fa7a", "Refactor cart product-name queries for dynamic DOM handling."),
        ("9e06d2b", "Add explicit waits and stale-element handling for navigation and listings."),
    ]
    story.extend([
        p("9. Maintainability and limitations", styles["ReportH1"]),
        p("Page Objects localise UI changes, configuration externalises mutable data, and clean WebDriver sessions protect independence. The most important ongoing risk is reliance on a shared public demo whose content and availability are outside the project team's control. Test results should always be interpreted alongside the first observed divergence and the captured evidence.", styles["ReportBody"]),
        p("The package/artifact names still reference nopCommerce because the project migrated after repository initialization. This does not affect execution, but renaming the Java namespace and Maven artifact to demoblaze would improve clarity in a future cleanup.", styles["ReportBody"]),

        p("10. Conclusion", styles["ReportH1"]),
        p("The Demoblaze project satisfies the assignment's core deliverables: a justified website choice, requirement and risk analysis, fifteen manual scenarios, five automation decisions, a Java/Selenium/TestNG Page Object framework, five implemented browser tests, a debugging narrative, meaningful Git history, and an AI disclosure. The latest execution produced BUILD SUCCESS with all five selected scenarios passing.", styles["ReportBody"]),

        p("Appendix A - Key migration commits", styles["ReportH1"]),
        data_table(["Commit", "Milestone"], commits, [1.15 * inch, 5.35 * inch], font_size=8.2),
        Spacer(1, 8),
        p("Appendix B - AI usage disclosure", styles["ReportH1"]),
        p("The Markdown disclosure states that Antigravity AI (Google DeepMind) assisted with target evaluation, Cloudflare diagnosis, Page Object Model architecture, the Demoblaze refactor, AJAX/alert synchronization, and documentation. The student retains responsibility for reviewing and understanding the implementation, executing it, explaining the decisions during the viva, and recording the five-minute reflection.", styles["ReportBody"]),
        p("Source basis", styles["ReportH2"]),
        p("This report was prepared from README.md, AI_USAGE.md, docs/01 through docs/05, docs/07, the current Java/configuration files, Git history, and the fresh Maven/Surefire execution on 13 August 2026. The older docs/06 nopCommerce execution record was not used as the current Demoblaze result.", styles["ReportBody"]),
    ])

    doc.build(story)
    print(OUT)


if __name__ == "__main__":
    build()
