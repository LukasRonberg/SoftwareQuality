package lesson03;

import org.example.lesson03.Employee;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.testng.Assert.*;

public class TestEmployee {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Employee createEmployeeWithBaseSalaryAndEducation(int baseSalary, int educationalLevel) {
        Employee employee = new Employee();
        employee.setBaseSalary(baseSalary);
        employee.setEducationalLevel(educationalLevel);
        return employee;
    }

    private String formatDate(LocalDate localDate) {
        return localDate.format(dateFormatter);
    }

    // --------------------
    // getEducationalLevel()
    // --------------------

    @DataProvider(name = "educationalLevelToNameCases")
    public Object[][] educationalLevelToNameCases() {
        return new Object[][]{
                {"level=0", 0, "none"},
                {"level=1", 1, "primary"},
                {"level=2", 2, "secondary"},
                {"level=3", 3, "tertiary"},
                {"level=-1 (invalid)", -1, "unknown"},
                {"level=99 (invalid)", 99, "unknown"}
        };
    }

    @Test(dataProvider = "educationalLevelToNameCases")
    public void getEducationalLevel_shouldReturnExpectedName(String testCaseName, int educationalLevel, String expectedName) {
        Employee employee = new Employee();
        employee.setEducationalLevel(educationalLevel);

        assertEquals(employee.getEducationalLevel(), expectedName, testCaseName);
    }

    // -----------
    // getSalary()
    // -----------

    @DataProvider(name = "salaryCases")
    public Object[][] salaryCases() {
        return new Object[][]{
                {"base=20000, level=0", 20000, 0, 20000},
                {"base=20000, level=1", 20000, 1, 21220},
                {"base=20000, level=3", 20000, 3, 23660},
                {"base=100000, level=0", 100000, 0, 100000},
                {"base=100000, level=3", 100000, 3, 103660}
        };
    }

    @Test(dataProvider = "salaryCases")
    public void getSalary_shouldFollowFormula(String testCaseName, int baseSalary, int educationalLevel, int expectedSalary) {
        Employee employee = createEmployeeWithBaseSalaryAndEducation(baseSalary, educationalLevel);
        assertEquals(employee.getSalary(), expectedSalary, testCaseName);
    }

    // -------------
    // getDiscount()
    // -------------

    @Test
    public void getDiscount_whenDateOfEmploymentIsNotSet_shouldReturnZero() {
        Employee employee = new Employee();
        assertEquals(employee.getDiscount(), 0.0);
    }

    @DataProvider(name = "discountYearsCases")
    public Object[][] discountYearsCases() {
        return new Object[][]{
                {"0 years employed", 0, 0.0},
                {"1 year employed", 1, 0.5},
                {"5 years employed", 5, 2.5},
                {"10 years employed", 10, 5.0}
        };
    }

    @Test(dataProvider = "discountYearsCases")
    public void getDiscount_shouldBeYearsTimesPointFive(String testCaseName, int yearsEmployed, double expectedDiscount) {
        Employee employee = new Employee();

        LocalDate employmentDate = LocalDate.now().minusYears(yearsEmployed);
        employee.setDateOfEmployment(formatDate(employmentDate));

        assertEquals(employee.getDiscount(), expectedDiscount, 1e-9, testCaseName);
    }

    @Test
    public void getDiscount_whenEmploymentDateIsInFuture_shouldClampToZero() {
        Employee employee = new Employee();

        LocalDate futureEmploymentDate = LocalDate.now().plusYears(1);
        employee.setDateOfEmployment(formatDate(futureEmploymentDate));

        assertEquals(employee.getDiscount(), 0.0);
    }

    // ------------------
    // getShippingCosts()
    // ------------------

    @DataProvider(name = "shippingCostsCases")
    public Object[][] shippingCostsCases() {
        return new Object[][]{
                {"country=null -> 100%", null, 100},

                {"Denmark -> 0%", "Denmark", 0},
                {"Norway -> 0%", "Norway", 0},
                {"Sweden -> 0%", "Sweden", 0},

                {"Denmark lower-case -> 0%", "denmark", 0},
                {"Norway with spaces -> 0%", "  Norway  ", 0},

                {"Iceland -> 50%", "Iceland", 50},
                {"Finland -> 50%", "Finland", 50},
                {"Finland lower-case -> 50%", "finland", 50},

                {"Germany -> 100%", "Germany", 100},
                {"USA -> 100%", "USA", 100},
                {"Empty string -> 100%", "", 100},
                {"Spaces only -> 100%", "   ", 100}
        };
    }

    @Test(dataProvider = "shippingCostsCases")
    public void getShippingCosts_shouldReturnExpectedPercentage(String testCaseName, String country, int expectedPercentage) {
        Employee employee = new Employee();
        employee.setCountry(country);

        assertEquals(employee.getShippingCosts(), expectedPercentage, testCaseName);
    }

    // -------------------------
    // Date parsing black-box
    // -------------------------

    @DataProvider(name = "validDateStrings")
    public Object[][] validDateStrings() {
        return new Object[][]{
                {"01/01/2000"},
                {"29/02/2024"}, // leap day, valid
                {"31/12/2025"}
        };
    }

    @Test(dataProvider = "validDateStrings")
    public void setDateOfBirth_withValidFormat_shouldStoreAndReturnSameFormat(String dateString) {
        Employee employee = new Employee();
        employee.setDateOfBirth(dateString);

        assertEquals(employee.getDateOfBirth(), dateString);
    }

    @Test(dataProvider = "validDateStrings")
    public void setDateOfEmployment_withValidFormat_shouldStoreAndReturnSameFormat(String dateString) {
        Employee employee = new Employee();
        employee.setDateOfEmployment(dateString);

        assertEquals(employee.getDateOfEmployment(), dateString);
    }

    @DataProvider(name = "invalidDateStrings")
    public Object[][] invalidDateStrings() {
        return new Object[][]{
                {"2025-12-31"}, // wrong format
                {"31/13/2025"}, // invalid month
                {"32/12/2025"}, // invalid day
                {"29/02/2023"}, // not a leap year
                {""}
        };
    }

    @Test(dataProvider = "invalidDateStrings", expectedExceptions = DateTimeParseException.class)
    public void setDateOfBirth_withInvalidDate_shouldThrow(String invalidDateString) {
        Employee employee = new Employee();
        employee.setDateOfBirth(invalidDateString);
    }

    @Test(dataProvider = "invalidDateStrings", expectedExceptions = DateTimeParseException.class)
    public void setDateOfEmployment_withInvalidDate_shouldThrow(String invalidDateString) {
        Employee employee = new Employee();
        employee.setDateOfEmployment(invalidDateString);
    }
}
