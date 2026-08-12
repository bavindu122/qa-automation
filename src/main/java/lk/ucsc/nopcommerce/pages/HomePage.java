package lk.ucsc.nopcommerce.pages;

import lk.ucsc.nopcommerce.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public final class HomePage extends BasePage {
    private final By loginLink = By.className("ico-login");
    private final By searchBox = By.id("small-searchterms");
    private final By searchButton = By.cssSelector(".search-box-button");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage openLogin() {
        click(loginLink);
        return new LoginPage(driver).waitUntilOpen();
    }

    public SearchResultsPage searchFor(String query) {
        type(searchBox, query);
        click(searchButton);
        return new SearchResultsPage(driver).waitUntilOpen();
    }

    public ProductPage openProduct(String slug) {
        driver.get(ConfigReader.get("baseUrl") + slug);
        return new ProductPage(driver).waitUntilOpen();
    }
}
