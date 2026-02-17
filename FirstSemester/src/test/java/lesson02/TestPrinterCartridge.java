package lesson02;

import lombok.extern.slf4j.Slf4j;
import org.example.lesson02.PrinterCartridge;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@Slf4j
public class TestPrinterCartridge {
    PrinterCartridge printerCartridge;

    @DataProvider(name="testCaseValues")
    public Object[][] testData(){
        return new Object[][]{{1,false},{2,false},{3,false},{4,false},{5,true},{6,true},{45,true}};
    }

    @DataProvider(name="testCaseValuesForDiscount")
    public Object[][] testDataForDiscount(){
        return new Object[][]{
                {98,false},
                {99,false},
                {100,true},
                {101,true}};
    }

    @BeforeMethod
    void setup() {
        printerCartridge = new PrinterCartridge();
    }

    @Test(dataProvider = "testCaseValues")
    void testMinimumCartridgeBoundaries(int value, boolean shouldBeAccepted) {
        if (shouldBeAccepted) {
            printerCartridge.calculateCartridges(value);
            log.info("Test case passed for value: {}", value);
        } else {
            log.info("Test case failed for value: {}", value);
            Assert.assertThrows(IllegalArgumentException.class, () ->
                    printerCartridge.calculateCartridges(value)
            );
        }
    }

    @Test(dataProvider = "testCaseValuesForDiscount")
    void testDiscount(int value, boolean shouldBeAccepted) {
        if (shouldBeAccepted) {
            float accepted = printerCartridge.calculateCartridges(value);
            assertEquals(accepted,0.2f);
            log.info("Test case passed for value: {}", value);
        } else {
            float shouldBeDeclined = printerCartridge.calculateCartridges(value);
            assertNotEquals(shouldBeDeclined,0.2f);
        }
    }

    @Test
    void testNegativeNumber(){
        printerCartridge.calculateCartridges(-1);
    }
}
