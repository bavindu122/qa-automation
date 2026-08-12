package lk.ucsc.nopcommerce.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** Page Object representing Demoblaze Home Page (index.html). */
public final class HomePage extends BasePage {
    private static final By LOGIN_LINK = By.id("login2");
    private static final By CART_LINK = By.id("cartur");
    private static final By PRODUCT_CARD_TITLES = By.cssSelector("#tbodyid .card-title a");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginModal openLoginModal() {
        click(LOGIN_LINK);
        return new LoginModal(driver);
    }

    public CartPage openCart() {
        click(CART_LINK);
        return new CartPage(driver);
    }

    public HomePage filterByCategory(String categoryName) {
        By categoryLocator = By.xpath("//a[text()='" + categoryName + "']");
        click(categoryLocator);
        return this;
    }

    public List<String> getDisplayedProductNames() {
        return visibleElements(PRODUCT_CARD_TITLES).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public ProductPage openProduct(String productName) {
        By productLocator = By.xpath("//a[text()='" + productName + "']");
        click(productLocator);
        return new ProductPage(driver);
    }
}
