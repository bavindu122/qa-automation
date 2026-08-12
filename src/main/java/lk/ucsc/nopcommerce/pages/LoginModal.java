package lk.ucsc.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page Object for Demoblaze Login Modal (#logInModal). */
public final class LoginModal extends BasePage {
    private static final By USERNAME_INPUT = By.id("loginusername");
    private static final By PASSWORD_INPUT = By.id("loginpassword");
    private static final By LOGIN_BUTTON = By.xpath("//button[text()='Log in']");

    public LoginModal(WebDriver driver) {
        super(driver);
    }

    public String loginExpectingFailure(String username, String password) {
        type(USERNAME_INPUT, username);
        type(PASSWORD_INPUT, password);
        click(LOGIN_BUTTON);
        return acceptAlertAndGetText();
    }
}
