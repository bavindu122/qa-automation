package lk.ucsc.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page Object representing Demoblaze Product Details Page. */
public final class ProductPage extends BasePage {
    private static final By PRODUCT_TITLE = By.cssSelector(".name");
    private static final By PRODUCT_PRICE = By.cssSelector(".price-container");
    private static final By ADD_TO_CART_BUTTON = By.xpath("//a[text()='Add to cart']");
    private static final By CART_LINK = By.id("cartur");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductTitle() {
        return text(PRODUCT_TITLE);
    }

    public String getProductPrice() {
        return text(PRODUCT_PRICE);
    }

    public ProductPage addToCart() {
        click(ADD_TO_CART_BUTTON);
        acceptAlertAndGetText();
        return this;
    }

    public CartPage openCart() {
        click(CART_LINK);
        return new CartPage(driver);
    }
}
