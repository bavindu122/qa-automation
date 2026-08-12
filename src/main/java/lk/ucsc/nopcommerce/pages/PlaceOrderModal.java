package lk.ucsc.nopcommerce.pages;

import lk.ucsc.nopcommerce.models.CustomerOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page Object for Demoblaze Place Order Modal and Confirmation Alert. */
public final class PlaceOrderModal extends BasePage {
    private static final By NAME_INPUT = By.id("name");
    private static final By COUNTRY_INPUT = By.id("country");
    private static final By CITY_INPUT = By.id("city");
    private static final By CARD_INPUT = By.id("card");
    private static final By MONTH_INPUT = By.id("month");
    private static final By YEAR_INPUT = By.id("year");
    private static final By PURCHASE_BUTTON = By.xpath("//button[text()='Purchase']");

    private static final By CONFIRMATION_HEADING = By.cssSelector(".sweet-alert h2");
    private static final By CONFIRMATION_DETAILS = By.cssSelector(".sweet-alert p");
    private static final By CONFIRMATION_OK_BUTTON = By.xpath("//button[text()='OK']");

    public PlaceOrderModal(WebDriver driver) {
        super(driver);
    }

    public PlaceOrderModal fillOrderDetails(CustomerOrder order) {
        type(NAME_INPUT, order.name());
        type(COUNTRY_INPUT, order.country());
        type(CITY_INPUT, order.city());
        type(CARD_INPUT, order.card());
        type(MONTH_INPUT, order.month());
        type(YEAR_INPUT, order.year());
        return this;
    }

    public PlaceOrderModal clickPurchase() {
        click(PURCHASE_BUTTON);
        return this;
    }

    public String getConfirmationHeading() {
        return text(CONFIRMATION_HEADING);
    }

    public String getConfirmationDetails() {
        return text(CONFIRMATION_DETAILS);
    }

    public void confirmOrder() {
        click(CONFIRMATION_OK_BUTTON);
    }
}
