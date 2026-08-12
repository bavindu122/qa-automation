package lk.ucsc.nopcommerce.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class CartPage extends BasePage {
    private final By title = By.cssSelector(".shopping-cart-page .page-title h1");
    private final By productNames = By.cssSelector(".cart .product-name");
    private final By rowQuantity = By.cssSelector(".qty-input");
    private final By terms = By.id("termsofservice");
    private final By checkout = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage waitUntilOpen() {
        waitForUrlContaining("/cart");
        isDisplayed(title);
        return this;
    }

    public List<String> getProductNames() {
        return visibleElements(productNames).stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    public int getQuantityFor(String expectedProduct) {
        List<org.openqa.selenium.WebElement> rows = visibleElements(By.cssSelector(".cart tbody tr"));
        return rows.stream()
                .filter(row -> row.getText().contains(expectedProduct))
                .findFirst()
                .map(row -> Integer.parseInt(row.findElement(rowQuantity).getAttribute("value")))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product is not present in cart: " + expectedProduct));
    }

    public GuestCheckoutPage beginGuestCheckout() {
        click(terms);
        click(checkout);
        return new GuestCheckoutPage(driver).waitUntilChoicePage();
    }
}
