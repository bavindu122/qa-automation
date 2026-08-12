package lk.ucsc.nopcommerce.pages;

import java.time.Duration;
import java.util.List;
import lk.ucsc.nopcommerce.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Shared synchronisation and interaction behaviour for all Page Objects. */
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(ConfigReader.getInt("explicitWaitSeconds")));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }

    protected String text(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText().trim();
    }

    protected List<WebElement> visibleElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected boolean isDisplayed(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    protected void waitForUrlContaining(String value) {
        wait.until(ExpectedConditions.urlContains(value));
    }

    protected void selectByText(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        new Select(element).selectByVisibleText(text);
    }

    protected void selectFirstRadio(By locator) {
        List<WebElement> radios = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        WebElement firstEnabled = radios.stream()
                .filter(WebElement::isEnabled)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No enabled option found: " + locator));
        if (!firstEnabled.isSelected()) {
            firstEnabled.click();
        }
    }

    protected void waitForClass(By locator, String className) {
        wait.until(ExpectedConditions.attributeContains(locator, "class", className));
    }

    protected String acceptAlertAndGetText() {
        org.openqa.selenium.Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }
}

