package lk.ucsc.nopcommerce.tests;

import java.util.List;
import lk.ucsc.nopcommerce.base.BaseTest;
import lk.ucsc.nopcommerce.config.ConfigReader;
import lk.ucsc.nopcommerce.models.CustomerOrder;
import lk.ucsc.nopcommerce.pages.CartPage;
import lk.ucsc.nopcommerce.pages.HomePage;
import lk.ucsc.nopcommerce.pages.PlaceOrderModal;
import org.testng.Assert;
import org.testng.annotations.Test;

public final class SelectedScenariosTest extends BaseTest {
    @Test(description = "AUT-01 / MTS-01 - Reject invalid login credentials")
    public void invalidLoginIsRejected() {
        String alertText = new HomePage(getDriver())
                .openLoginModal()
                .loginExpectingFailure(ConfigReader.get("invalidUsername"), ConfigReader.get("invalidPassword"));

        Assert.assertTrue(alertText.toLowerCase().contains("user does not exist") || alertText.toLowerCase().contains("wrong password"),
                "Expected invalid login alert message, but received: " + alertText);
    }

    @Test(description = "AUT-02 / MTS-02 - Filter products by category")
    public void productsCanBeFilteredByCategory() {
        String category = ConfigReader.get("laptopCategoryName");
        String expectedProduct = ConfigReader.get("laptopProductName");

        List<String> products = new HomePage(getDriver())
                .filterByCategory(category)
                .getDisplayedProductNames();

        Assert.assertTrue(products.contains(expectedProduct),
                "Filtered category " + category + " should contain product " + expectedProduct + ", but found: " + products);
    }

    @Test(description = "AUT-03 / MTS-03 - Add a product to the cart")
    public void simpleProductCanBeAddedToCart() {
        String productName = ConfigReader.get("phoneProductName");

        List<String> cartItems = new HomePage(getDriver())
                .openProduct(productName)
                .addToCart()
                .openCart()
                .getProductNames();

        Assert.assertTrue(cartItems.contains(productName),
                "Shopping cart should contain " + productName + ", but found: " + cartItems);
    }

    @Test(description = "AUT-04 / MTS-04 - Remove a product from the cart")
    public void productCanBeRemovedFromCart() {
        String productName = ConfigReader.get("phoneProductName");

        CartPage cart = new HomePage(getDriver())
                .openProduct(productName)
                .addToCart()
                .openCart()
                .removeProduct(productName);

        Assert.assertFalse(cart.getProductNames().contains(productName),
                "Shopping cart should no longer contain " + productName);
    }

    @Test(description = "AUT-05 / MTS-05 - Complete product checkout")
    public void guestCanCompleteCheckout() {
        String productName = ConfigReader.get("phoneProductName");

        PlaceOrderModal orderModal = new HomePage(getDriver())
                .openProduct(productName)
                .addToCart()
                .openCart()
                .openPlaceOrderModal()
                .fillOrderDetails(CustomerOrder.validSyntheticOrder())
                .clickPurchase();

        String heading = orderModal.getConfirmationHeading();
        String details = orderModal.getConfirmationDetails();
        orderModal.confirmOrder();

        Assert.assertTrue(heading.contains("Thank you for your purchase!"),
                "Order confirmation heading missing, received: " + heading);
        Assert.assertTrue(details.contains("Amount:") || details.contains("Id:"),
                "Order confirmation details missing order metadata: " + details);
    }
}
