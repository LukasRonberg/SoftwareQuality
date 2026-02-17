package lesson1;

import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.example.lesson1.Calculator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertEquals;

@Slf4j
public class CalculatorTests {
    private Calculator calculator;
    private SoftAssert softAssert;

    @DataProvider(name="Test")
    public Object[][] testData(){
        int test = 1;
        return new Object[][]{{test}};
    }


    @BeforeMethod
    public void setUp() {
        calculator = new Calculator();
        softAssert = new SoftAssert();
    }

    @Test(dataProvider = "Test")
    @Description("Tests all Calculator methods")
    public void testCalculator() {
        int number1 = 10;
        int number2 = 5;

        /*int sumResultActual = calculator.sum(number1,number2);
        int sumResultExpected = 15;
        softAssert.assertEquals(sumResultActual,sumResultExpected);*/

        int subtractResultActual = calculator.subtract(number1,number2);
        int subtractResultExpected = 5;
        softAssert.assertEquals(subtractResultActual, subtractResultExpected);

        int multiplyResultActual = calculator.multiply(number1,number2);
        int multiplyResultExpected = 50;
        softAssert.assertEquals(multiplyResultActual, multiplyResultExpected);

        int divideResultActual = calculator.divide(number1,number2);
        int divideResultExpected = 2;
        softAssert.assertEquals(divideResultActual,divideResultExpected);

        softAssert.assertAll();
    }


    @Test
    public void testSum() {
        float number1 = 3.46f;
        float number2 = 4.47f;

        float result = calculator.sum(number1, number2);

        log.info("sum({}, {}) = {}", number1, number2, result);
        assertEquals(result, 7.93f,0.01f);

    }

    @Test
    public void testSubtract() {
        int number1 = 10;
        int number2 = 3;

        int result = calculator.subtract(number1, number2);

        softAssert.assertEquals(result, 7,
                "Expected: 7 | Actual: " + result);

        int negativeResult = calculator.subtract(-7,1);
        softAssert.assertEquals(negativeResult,-8,
                "Expected: -8 | Actual: " + negativeResult);

        softAssert.assertAll();
    }

    @Test
    public void testMultiply() {
        int number1 = 6;
        int number2 = 2;

        int result = calculator.multiply(number1, number2);

        log.info("multiply({}, {}) = {}", number1, number2, result);
        assertEquals(result, 12);
    }

    @Test
    public void testDivide() {
        int number1 = 20;
        int number2 = 4;

        int result = calculator.divide(number1, number2);

        log.info("divide({}, {}) = {}", number1, number2, result);
        assertEquals(result, 5);
    }
}
