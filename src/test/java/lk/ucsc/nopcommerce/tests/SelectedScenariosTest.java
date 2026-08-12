package lk.ucsc.nopcommerce.tests;

import java.util.List;
import lk.ucsc.nopcommerce.base.BaseTest;
import lk.ucsc.nopcommerce.config.ConfigReader;
import lk.ucsc.nopcommerce.models.BillingAddress;
import lk.ucsc.nopcommerce.pages.CompareProductsPage;
import lk.ucsc.nopcommerce.pages.HomePage;
import lk.ucsc.nopcommerce.pages.OrderConfirmationPage;
import lk.ucsc.nopcommerce.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class SelectedScenariosTest extends BaseTest {
    @Test(description = "AUT-01 / MTS-05 - Reject invalid login credentials")
    public void invalidLoginIsRejected() {
        String error = new HomePage(getDriver())
                .openLogin()
                .loginExpectingFailure("invalid.user@example.com", "WrongPassword123!")
                .getErrorMessage();

        Assert.assertTrue(error.contains("Login was unsuccessful"),
                "Expected a generic unsuccessful-login message, but received: " + error);
    }

    @Test(description = "AUT-02 / MTS-07 - Search for an existing product")
    public void existingProductCanBeFoundBySearch() {
        String query = ConfigReader.get("searchTerm");
        var results = new HomePage(getDriver()).searchFor(query);
        List<String> names = results.getProductNames();

        Assert.assertEquals(results.getQuery(), query, "Search query should be retained");
        Assert.assertTrue(names.stream().anyMatch(
                        name -> name.toLowerCase().contains(query.toLowerCase())),
                "Expected a relevant search result for " + query + ", but found: " + names);
    }

    @Test(description = "AUT-03 / MTS-11 - Add a simple product to the cart")
    public void simpleProductCanBeAddedToCart() {
        String productName = ConfigReader.get("simpleProductName");
        var cart = new HomePage(getDriver())
                .openProduct(ConfigReader.get("simpleProductSlug"))
                .addToCart(1)
                .openCart();

        Assert.assertTrue(cart.getProductNames().contains(productName),
                "Cart should contain " + productName);
        Assert.assertEquals(cart.getQuantityFor(productName), 1,
                "Cart should contain exactly one unit");
    }

    @Test(description = "AUT-04 / MTS-15 - Compare two products")
    public void twoProductsCanBeCompared() {
        HomePage home = new HomePage(getDriver());
        ProductPage first = home.openProduct(ConfigReader.get("comparisonProductOneSlug"));
        first.addToComparison();
        ProductPage second = home.openProduct(ConfigReader.get("comparisonProductTwoSlug"));
        second.addToComparison();

        List<String> comparedNames = new CompareProductsPage(getDriver())
                .open()
                .getComparedProductNames();

        Assert.assertTrue(comparedNames.contains(ConfigReader.get("comparisonProductOneName")),
                "Comparison should contain the first selected product: " + comparedNames);
        Assert.assertTrue(comparedNames.contains(ConfigReader.get("comparisonProductTwoName")),
                "Comparison should contain the second selected product: " + comparedNames);
    }

    @Test(description = "AUT-05 / MTS-18 - Complete a valid guest checkout")
    public void guestCanCompleteCheckout() {
        var checkout = new HomePage(getDriver())
                .openProduct(ConfigReader.get("simpleProductSlug"))
                .addToCart(1)
                .openCart()
                .beginGuestCheckout()
                .chooseGuestCheckout()
                .enterBillingAddress(BillingAddress.validSyntheticAddress())
                .chooseFirstShippingMethod()
                .chooseFirstPaymentMethod()
                .continuePaymentInformation();

        OrderConfirmationPage confirmation = checkout.confirmOrder();
        Assert.assertTrue(confirmation.getSuccessMessage().contains("successfully processed"),
                "Order success message was not displayed");
        Assert.assertFalse(confirmation.getOrderNumber().isBlank(),
                "A successful order should display its order number");
    }
}
