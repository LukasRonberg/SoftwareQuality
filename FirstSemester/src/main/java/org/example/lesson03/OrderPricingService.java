package org.example.lesson03;

import java.util.Locale;
import java.util.Set;

public class OrderPricingService {

    private static final Set<String> ISO_ALPHA2_CODES =
            Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2);

    private final DiscountService discountService;
    private final ShippingService shippingService;

    public OrderPricingService(DiscountService discountService, ShippingService shippingService) {
        this.discountService = discountService;
        this.shippingService = shippingService;
    }

    public float calculateTotal(String countryCode, float subtotal) {
        if (countryCode == null) {
            throw new IllegalArgumentException("Country code is required");
        }

        if (!ISO_ALPHA2_CODES.contains(countryCode)) {
            throw new IllegalArgumentException("Wrong country code");
        }

        float discount = discountService.getDiscount(subtotal);
        float shippingCosts = shippingService.getShippingCosts(countryCode, subtotal);

        if(subtotal < discount){
            throw new IllegalArgumentException("Discount cannot be larger than Subtotal");
        }

        return subtotal - discount + shippingCosts;
    }
}
