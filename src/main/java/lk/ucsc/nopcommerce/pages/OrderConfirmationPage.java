package lk.ucsc.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class OrderConfirmationPage extends BasePage {
    private final By successTitle = By.cssSelector(".order-completed .title strong");
    private final By orderNumber = By.cssSelector(".order-completed .order-number strong");

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public OrderConfirmationPage waitUntilOpen() {
        waitForUrlContaining("/checkout/completed");
        isDisplayed(successTitle);
        return this;
    }

    public String getSuccessMessage() {
        return text(successTitle);
    }

    public String getOrderNumber() {
        return text(orderNumber);
    }
}
