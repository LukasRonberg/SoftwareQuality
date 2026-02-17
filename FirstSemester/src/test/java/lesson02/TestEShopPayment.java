package lesson02;

import lombok.extern.slf4j.Slf4j;
import org.example.lesson02.EShopPayment;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;

@Slf4j
public class TestEShopPayment {
    EShopPayment testEShopPayment;

    @DataProvider(name="testNoDiscount")
    public Object[][] testData(){
        return new Object[][]{{-0.01f,false},{0f,false},{0.01f,true},{0.02f,true},{299.99f,true},{300f,true},{300.01f,false}};
    }

    @BeforeMethod
    void setup(){
        testEShopPayment = new EShopPayment();
    }

    @Test
    void testInvalidDataType(){

    }

    @Test(dataProvider = "testNoDiscount")
    void testNoDiscount(float value, boolean shouldBeAccepted){
        if (shouldBeAccepted) {
            int noDiscount = testEShopPayment.calculateDiscount(value);
            assertEquals(noDiscount, 0);
            log.info("Test case passed for value: {}", value);
        } else {
            log.info("Test case failed for value: {}", value);
            testEShopPayment.calculateDiscount(value);
        }
    }

    @Test
    void testFivePercentDiscount(){

    }

    @Test
    void testTenPercentDiscount(){

    }
}
