package lk.ucsc.nopcommerce.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** Page Object representing Demoblaze Shopping Cart page (cart.html). */
public final class CartPage extends BasePage {
    private static final By CART_ROWS = By.cssSelector("#tbodyid tr");
    private static final By PRODUCT_NAMES = By.cssSelector("#tbodyid tr td:nth-child(2)");
    private static final By DELETE_BUTTONS = By.xpath("//a[contains(text(),'Delete')]");
    private static final By TOTAL_PRICE = By.id("totalp");
    private static final By PLACE_ORDER_BUTTON = By.xpath("//button[text()='Place Order']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getProductNames() {
        return visibleElements(PRODUCT_NAMES).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public String getTotalPrice() {
        return text(TOTAL_PRICE);
    }

    public CartPage removeProduct(String productName) {
        List<WebElement> rows = visibleElements(CART_ROWS);
        for (WebElement row : rows) {
            if (row.getText().contains(productName)) {
                WebElement deleteLink = row.findElement(By.xpath(".//a[contains(text(),'Delete')]"));
                deleteLink.click();
                wait.until(ExpectedConditions.stalenessOf(row));
                break;
            }
        }
        return this;
    }

    public PlaceOrderModal openPlaceOrderModal() {
        click(PLACE_ORDER_BUTTON);
        return new PlaceOrderModal(driver);
    }
}
