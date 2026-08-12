package lk.ucsc.nopcommerce.models;

/** Synthetic buyer details for Demoblaze checkout process. */
public record CustomerOrder(
        String name,
        String country,
        String city,
        String card,
        String month,
        String year
) {
    public static CustomerOrder validSyntheticOrder() {
        return new CustomerOrder(
                "John Doe",
                "Sri Lanka",
                "Colombo",
                "4000123456789010",
                "12",
                "2028"
        );
    }
}
