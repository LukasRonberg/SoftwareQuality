package lesson1;

import lombok.extern.slf4j.Slf4j;
import org.example.lesson1.RomanNumerals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Slf4j
public class RomanNumeralsTests {
    RomanNumerals romanNumerals;

    @BeforeMethod
    public void setup(){
        romanNumerals = new RomanNumerals();
    }

    @Test
    public void testMaxRomanNumerals(){
        int decimalAmount = romanNumerals.calculateRomanNumerals("MMMCMXCIX");
        log.info("Decimal Amount: {}", decimalAmount);
    }
}
