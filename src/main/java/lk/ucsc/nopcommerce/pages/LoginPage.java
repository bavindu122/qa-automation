package lk.ucsc.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class LoginPage extends BasePage {
    private final By email = By.id("Email");
    private final By password = By.id("Password");
    private final By loginButton = By.cssSelector(".login-button");
    private final By errorSummary = By.cssSelector(".message-error.validation-summary-errors");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage waitUntilOpen() {
        waitForUrlContaining("/login");
        return this;
    }

    public LoginPage loginExpectingFailure(String emailAddress, String passwordValue) {
        type(email, emailAddress);
        type(password, passwordValue);
        click(loginButton);
        isDisplayed(errorSummary);
        return this;
    }

    public String getErrorMessage() {
        return text(errorSummary);
    }
}
