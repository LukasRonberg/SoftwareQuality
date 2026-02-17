package lesson03;

import org.example.lesson03.DiscountService;
import org.example.lesson03.OrderPricingService;
import org.example.lesson03.ShippingService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class TestOrderPricingService {

    private DiscountService discountService;
    private ShippingService shippingService;
    private OrderPricingService orderPricingService;

    @BeforeMethod
    void setUp() {
        discountService = mock(DiscountService.class);
        shippingService = mock(ShippingService.class);

        orderPricingService = new OrderPricingService(discountService, shippingService);
    }

    @Test
    void calculateTotal_shouldUseDiscountAndShipping() {
        when(discountService.getDiscount(500.0f)).thenReturn(54.5f);
        when(shippingService.getShippingCosts("DK", 500.0f)).thenReturn(39.0f);

        float total = orderPricingService.calculateTotal("DK", 500.0f);

        assertEquals(total, 500.0f - 54.5f + 39.0f, 0.0001f);

        verify(discountService, times(1)).getDiscount(500.0f);
        verify(shippingService, times(1)).getShippingCosts("DK", 500.0f);
        verifyNoMoreInteractions(discountService, shippingService);
    }

    @Test
    void testInvalidCountryCodes(){
        when(discountService.getDiscount(500.0f)).thenReturn(54.5f);
        when(shippingService.getShippingCosts("ZZ", 500.0f)).thenReturn(39.0f);

        orderPricingService.calculateTotal("ZZ",500.0f);
    }
}
