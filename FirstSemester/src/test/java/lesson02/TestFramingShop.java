package lesson02;

import org.example.exceptions.InvalidFrameSizeException;
import org.example.lesson02.FramingShop;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

public class TestFramingShop {

    private final FramingShop framingShop = new FramingShop();

    // ------------------------
    // WIDTH and HEIGHT boundary: valid values (should NOT throw)
    // ------------------------
    @DataProvider(name = "validBoundaryValues")
    public Object[][] validBoundaryValues() {
        return new Object[][]{
                {"width=30", 30, 30},
                {"width=31", 31, 30},
                {"width=99", 99, 30},
                {"width=100", 100, 30},
                {"height=60", 100, 60},
                //{"height=30", 100, 30}
                {"height=100", 100, 30}
        };
    }

    @Test(dataProvider = "validBoundaryValues")
    public void validValues_shouldNotThrow(String testCaseName, int width, int height) throws InvalidFrameSizeException {
        framingShop.calculatePrice(width, height);
    }

    // ------------------------
    // WIDTH boundary: invalid values (should throw + message)
    // ------------------------
    @DataProvider(name = "invalidWidthBoundaryValues")
    public Object[][] invalidWidthBoundaryValues() {
        return new Object[][]{
                {"width=29", 29, 30, "Width must be between 30 and 100."},
                {"width=101", 101, 30, "Width must be between 30 and 100."},
                {"width=0", 0, 30, "Width must be between 30 and 100."}
        };
    }

    @Test(dataProvider = "invalidWidthBoundaryValues")
    public void invalidWidth_shouldThrow(String testCaseName, int width, int height, String expectedMessage) {
        InvalidFrameSizeException invalidFrameSizeException = expectThrows(
                InvalidFrameSizeException.class,
                () -> framingShop.calculatePrice(width, height)
        );

        assertEquals(invalidFrameSizeException.getMessage(), expectedMessage);
    }

    // ------------------------
    // SURFACE AREA pricing boundary:
    // surfaceArea < 1600 => 3500
    // surfaceArea >= 1600 => 3000
    // ------------------------
    @DataProvider(name = "surfaceAreaBoundaryValues")
    public Object[][] surfaceAreaBoundaryValues() {
        return new Object[][]{
                // Below 1600 (valid width/height) -> expect 3500
                {"area=1599 (39*40)", 39, 40, 3500},

                // Exactly 1600 (valid width/height) -> expect 3000
                {"area=1600 (40*40)", 40, 40, 3000},

                // Above 1600 (valid width/height) -> expect 3000
                {"area=1640 (41*40)", 41, 40, 3000}
        };
    }

    @Test(dataProvider = "surfaceAreaBoundaryValues")
    public void surfaceArea_shouldReturnCorrectPrice(String testCaseName, int width, int height, int expectedPrice)
            throws InvalidFrameSizeException {

        int actualPrice = framingShop.calculatePrice(width, height);
        assertEquals(actualPrice, expectedPrice);
    }
}
