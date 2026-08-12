package lk.ucsc.nopcommerce.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class SearchResultsPage extends BasePage {
    private final By pageTitle = By.cssSelector(".page-title h1");
    private final By productNames = By.cssSelector(".product-grid .product-title a");
    private final By searchInput = By.id("q");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public SearchResultsPage waitUntilOpen() {
        waitForUrlContaining("/search");
        isDisplayed(pageTitle);
        return this;
    }

    public List<String> getProductNames() {
        return visibleElements(productNames).stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    public String getQuery() {
        return wait.until(driver -> driver.findElement(searchInput).getAttribute("value"));
    }
}
