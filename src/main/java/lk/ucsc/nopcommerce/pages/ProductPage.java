package lk.ucsc.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class ProductPage extends BasePage {
    private final By productName = By.cssSelector(".product-essential .product-name h1");
    private final By quantity = By.cssSelector(".overview .qty-input");
    private final By addToCart = By.cssSelector(".overview .add-to-cart-button");
    private final By addToCompare = By.cssSelector(
            ".overview .add-to-compare-list-button");
    private final By successNotification = By.cssSelector(".bar-notification.success");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage waitUntilOpen() {
        isDisplayed(productName);
        return this;
    }

    public String getProductName() {
        return text(productName);
    }

    public ProductPage addToCart(int requestedQuantity) {
        type(quantity, String.valueOf(requestedQuantity));
        click(addToCart);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                successNotification, "shopping cart"));
        return this;
    }

    public ProductPage addToComparison() {
        click(addToCompare);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                successNotification, "product comparison"));
        return this;
    }

    public CartPage openCart() {
        driver.get(lk.ucsc.nopcommerce.config.ConfigReader.get("baseUrl") + "cart");
        return new CartPage(driver).waitUntilOpen();
    }
}
