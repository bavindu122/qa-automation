package lk.ucsc.nopcommerce.pages;

import java.util.List;
import lk.ucsc.nopcommerce.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class CompareProductsPage extends BasePage {
    private final By title = By.cssSelector(".compare-products-page .page-title h1");
    private final By productNames = By.cssSelector(
            ".compare-products-table tr.product-name td:not(:first-child)");

    public CompareProductsPage(WebDriver driver) {
        super(driver);
    }

    public CompareProductsPage open() {
        driver.get(ConfigReader.get("baseUrl") + "compareproducts");
        isDisplayed(title);
        return this;
    }

    public List<String> getComparedProductNames() {
        return visibleElements(productNames).stream()
                .map(element -> element.getText().trim())
                .toList();
    }
}
