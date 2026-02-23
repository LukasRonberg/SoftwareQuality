package lesson04;

import org.example.lesson04.DriverLicense;
import org.example.lesson04.DriversLicenseRecord;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TestDriverLicense {

    DriverLicense driverLicense = new DriverLicense();

    @DataProvider(name = "licenseLogicProvider")
    public Object[][] licenseLogicProvider() {
        return new Object[][]{
                {"Both Pass (Median)", 92, 1, true, false, false, false},
                {"Both Pass (Boundaries)", 85, 2, true, false, false, false},

                {"Fail Theory Only (Boundary 84)", 84, 1, false, true, false, false},
                {"Fail Theory Only (Median 42)", 42, 0, false, true, false, false},

                {"Fail Practical Only (Boundary 3)", 90, 3, false, false, true, false},
                {"Fail Practical Only (Median 6)", 88, 6, false, false, true, false},

                {"Fail Both - Extra Lessons (Medians)", 42, 5, false, true, true, true},
                {"Fail Both - Extra Lessons (Boundaries)", 84, 3, false, true, true, true}
        };
    }

    @Test(dataProvider = "licenseLogicProvider")
    public void testLicenseOutcomes(String desc, int theory, int practical,
                                    boolean g, boolean rt, boolean rp, boolean el) {
        DriversLicenseRecord result = driverLicense.passOrFail(theory, practical);

        assertEquals(result.grantDriversLicense(), g, desc + ": Grant status mismatch");
        assertEquals(result.repeatTheory(), rt, desc + ": Repeat Theory mismatch");
        assertEquals(result.repeatPractical(), rp, desc + ": Repeat Practical mismatch");
        assertEquals(result.takeExtraDrivingLessons(), el, desc + ": Extra Lessons mismatch");
    }

    @DataProvider(name = "invalidInputProvider")
    public Object[][] invalidInputProvider() {
        return new Object[][]{
                {"Theory above max", 150, 0},
                {"Theory below min", -10, 0},
                {"Practical below min", 85, -5}
        };
    }

    @Test(dataProvider = "invalidInputProvider", expectedExceptions = IllegalArgumentException.class)
    public void testInvalidInputs(String desc, int theory, int practical) {
        driverLicense.passOrFail(theory, practical);
    }
}
