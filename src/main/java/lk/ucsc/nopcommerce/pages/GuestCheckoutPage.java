package lk.ucsc.nopcommerce.pages;

import lk.ucsc.nopcommerce.models.BillingAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class GuestCheckoutPage extends BasePage {
    private final By guestCheckout = By.cssSelector(".checkout-as-guest-button");
    private final By billingSection = By.id("opc-billing");
    private final By firstName = By.id("BillingNewAddress_FirstName");
    private final By lastName = By.id("BillingNewAddress_LastName");
    private final By email = By.id("BillingNewAddress_Email");
    private final By country = By.id("BillingNewAddress_CountryId");
    private final By state = By.id("BillingNewAddress_StateProvinceId");
    private final By city = By.id("BillingNewAddress_City");
    private final By addressLine = By.id("BillingNewAddress_Address1");
    private final By postalCode = By.id("BillingNewAddress_ZipPostalCode");
    private final By phone = By.id("BillingNewAddress_PhoneNumber");
    private final By billingContinue = By.cssSelector(
            "#billing-buttons-container .new-address-next-step-button");
    private final By shippingMethodSection = By.id("opc-shipping_method");
    private final By shippingOptions = By.cssSelector("input[name='shippingoption']");
    private final By shippingContinue = By.cssSelector(
            "#shipping-method-buttons-container .shipping-method-next-step-button");
    private final By paymentMethodSection = By.id("opc-payment_method");
    private final By paymentOptions = By.cssSelector("input[name='paymentmethod']");
    private final By paymentContinue = By.cssSelector(
            "#payment-method-buttons-container .payment-method-next-step-button");
    private final By paymentInfoSection = By.id("opc-payment_info");
    private final By paymentInfoContinue = By.cssSelector(
            "#payment-info-buttons-container .payment-info-next-step-button");
    private final By confirmSection = By.id("opc-confirm_order");
    private final By confirmOrder = By.cssSelector(
            "#confirm-order-buttons-container .confirm-order-next-step-button");

    public GuestCheckoutPage(WebDriver driver) {
        super(driver);
    }

    public GuestCheckoutPage waitUntilChoicePage() {
        waitForUrlContaining("checkoutasguest");
        return this;
    }

    public GuestCheckoutPage chooseGuestCheckout() {
        click(guestCheckout);
        waitForClass(billingSection, "active");
        return this;
    }

    public GuestCheckoutPage enterBillingAddress(BillingAddress address) {
        type(firstName, address.firstName());
        type(lastName, address.lastName());
        type(email, address.email());
        selectByText(country, address.country());
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector("#BillingNewAddress_StateProvinceId option"), 1));
        selectByText(state, address.state());
        type(city, address.city());
        type(addressLine, address.addressLine());
        type(postalCode, address.postalCode());
        type(phone, address.phoneNumber());
        click(billingContinue);
        waitForClass(shippingMethodSection, "active");
        return this;
    }

    public GuestCheckoutPage chooseFirstShippingMethod() {
        selectFirstRadio(shippingOptions);
        click(shippingContinue);
        waitForClass(paymentMethodSection, "active");
        return this;
    }

    public GuestCheckoutPage chooseFirstPaymentMethod() {
        selectFirstRadio(paymentOptions);
        click(paymentContinue);
        waitForClass(paymentInfoSection, "active");
        return this;
    }

    public GuestCheckoutPage continuePaymentInformation() {
        click(paymentInfoContinue);
        waitForClass(confirmSection, "active");
        return this;
    }

    public OrderConfirmationPage confirmOrder() {
        click(confirmOrder);
        return new OrderConfirmationPage(driver).waitUntilOpen();
    }
}
