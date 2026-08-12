package lk.ucsc.nopcommerce.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        waitForUrlContaining("cart.html");
        try {
            Thread.sleep(1500); // Allow Demoblaze AJAX viewcart response to populate table
        } catch (InterruptedException ignored) {
        }
        return new CartPage(driver);
    }

    public HomePage filterByCategory(String categoryName) {
        List<WebElement> currentCards = driver.findElements(PRODUCT_CARD_TITLES);
        WebElement firstCard = currentCards.isEmpty() ? null : currentCards.get(0);

        By categoryLocator = By.xpath("//a[text()='" + categoryName + "']");
        click(categoryLocator);

        if (firstCard != null) {
            try {
                wait.until(ExpectedConditions.stalenessOf(firstCard));
            } catch (Exception ignored) {
            }
        }
        return this;
    }

    public List<String> getDisplayedProductNames() {
        return wait.until(d -> {
            try {
                List<WebElement> elements = d.findElements(PRODUCT_CARD_TITLES);
                if (elements.isEmpty()) {
                    return null;
                }
                List<String> names = elements.stream()
                        .map(WebElement::getText)
                        .map(String::trim)
                        .toList();
                return names.stream().anyMatch(n -> !n.isEmpty()) ? names : null;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    public ProductPage openProduct(String productName) {
        By productLocator = By.xpath("//a[text()='" + productName + "']");
        click(productLocator);
        return new ProductPage(driver);
    }
}
