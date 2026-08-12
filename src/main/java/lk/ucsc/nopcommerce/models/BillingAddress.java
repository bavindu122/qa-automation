package lk.ucsc.nopcommerce.models;

/** Synthetic customer data used by the guest checkout scenario. */
public record BillingAddress(
        String firstName,
        String lastName,
        String email,
        String country,
        String state,
        String city,
        String addressLine,
        String postalCode,
        String phoneNumber) {

    public static BillingAddress validSyntheticAddress() {
        return new BillingAddress(
                "QA",
                "Student",
                "qa.checkout@example.com",
                "United States of America",
                "New York",
                "Albany",
                "100 Test Avenue",
                "12207",
                "+1 202 555 0147");
    }
}
