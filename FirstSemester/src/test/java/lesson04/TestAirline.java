package lesson04;

import org.example.lesson04.Airline;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.DayOfWeek;

import static org.testng.Assert.assertEquals;

public class TestAirline {

    Airline airline = new Airline();

    @DataProvider(name = "specialConditionOne")
    public Object[][] specialConditionOneCases() {
        return new Object[][]{
                // 20% expected discount
                {"Age 19, India, Tuesday, stay 5 -> 20%", 19, Airline.DestinationType.INDIA, DayOfWeek.TUESDAY, 5, 20},
                {"Age 19, India, Wednesday, stay 5 -> 20%", 19, Airline.DestinationType.INDIA, DayOfWeek.WEDNESDAY, 5, 20},
                {"Age 19, India, Thursday, stay 5 -> 20%", 19, Airline.DestinationType.INDIA, DayOfWeek.THURSDAY, 5, 20},
                {"Age 19, India, Saturday, stay 5 -> 20%", 19, Airline.DestinationType.INDIA, DayOfWeek.SATURDAY, 5, 20},
                {"Age 19, India, Sunday, stay 5 -> 20%", 19, Airline.DestinationType.INDIA, DayOfWeek.SUNDAY, 5, 20},

                // Blocked days 0% expected discount
                {"Age 19, India, Monday, stay 5 -> 0%", 19, Airline.DestinationType.INDIA, DayOfWeek.MONDAY, 5, 0},
                {"Age 19, India, Friday, stay 5 -> 0%", 19, Airline.DestinationType.INDIA, DayOfWeek.FRIDAY, 5, 0},

                // 18 years old 0% expected discount
                {"Age 18, India, Thursday, stay 5 -> 0%", 18, Airline.DestinationType.INDIA, DayOfWeek.THURSDAY, 5, 0},

        };
    }

    @Test(dataProvider = "specialConditionOne")
    public void testSpecialConditionOne(String testCaseName,
                                                             int passengerAgeInYears,
                                                             Airline.DestinationType destinationType,
                                                             DayOfWeek dayOfWeek,
                                                             int stayLengthInDays,
                                                             int expectedDiscount) {
        float actualDiscount = airline.calculateDiscount(passengerAgeInYears, destinationType, dayOfWeek, stayLengthInDays);
        assertEquals(actualDiscount, expectedDiscount, testCaseName);
    }

    @DataProvider(name = "specialConditionTwo")
    public Object[][] specialConditionTwo() {
        return new Object[][]{
                // 25% expected discount (Outside India, not Mon/Fri)
                {"Age 19, Outside India, Tuesday, stay 5 -> 25%", 19, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.TUESDAY, 5, 25},
                {"Age 19, Outside India, Wednesday, stay 5 -> 25%", 19, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.WEDNESDAY, 5, 25},
                {"Age 19, Outside India, Thursday, stay 5 -> 25%", 19, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.THURSDAY, 5, 25},
                {"Age 19, Outside India, Saturday, stay 5 -> 25%", 19, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.SATURDAY, 5, 25},
                {"Age 19, Outside India, Sunday, stay 5 -> 25%", 19, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.SUNDAY, 5, 25},

                // Blocked days 0% expected discount (Outside India, Mon/Fri)
                {"Age 19, Outside India, Monday, stay 5 -> 0%", 19, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.MONDAY, 5, 0},
                {"Age 19, Outside India, Friday, stay 5 -> 0%", 19, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.FRIDAY, 5, 0},

        };
    }

    @Test(dataProvider = "specialConditionTwo")
    public void testSpecialConditionTwo(String testCaseName,
                                                             int passengerAgeInYears,
                                                             Airline.DestinationType destinationType,
                                                             DayOfWeek dayOfWeek,
                                                             int stayLengthInDays,
                                                             int expectedDiscount) {
        float actualDiscount = airline.calculateDiscount(passengerAgeInYears, destinationType, dayOfWeek, stayLengthInDays);
        assertEquals(actualDiscount, expectedDiscount, testCaseName);
    }

    @DataProvider(name = "specialConditionThreeAndFour")
    public Object[][] specialConditionThreeAndFour() {
        return new Object[][]{
                // Additional 10% discount cases (stay length rule)
                {"Age 19, India, Thursday, stay 5 -> 20%", 19, Airline.DestinationType.INDIA, DayOfWeek.THURSDAY, 5, 20},
                {"Age 19, India, Thursday, stay 6 -> 30%", 19, Airline.DestinationType.INDIA, DayOfWeek.THURSDAY, 6, 30},
                {"Age 19, India, Thursday, stay 7 -> 30%", 19, Airline.DestinationType.INDIA, DayOfWeek.THURSDAY, 7, 30},
                {"Age 19, India, Monday, stay 6 -> 0%", 19, Airline.DestinationType.INDIA, DayOfWeek.MONDAY, 6, 0},

                // Age-based discounts (apply regardless of destination/day/stay)
                {"Age 2, India, Thursday, stay 5 -> 100% (Free)", 2, Airline.DestinationType.INDIA, DayOfWeek.THURSDAY, 5, 100},
                {"Age 2, Outside India, Monday, stay 6 -> 100% (Free)", 2, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.MONDAY, 6, 100},

                {"Age 3, India, Friday, stay 0 -> 40%", 3, Airline.DestinationType.INDIA, DayOfWeek.FRIDAY, 0, 40},
                {"Age 17, Outside India, Monday, stay 6 -> 40% (+10% extra) = 50%", 17, Airline.DestinationType.OUTSIDE_INDIA, DayOfWeek.MONDAY, 6, 50},
                {"Age 17, India, Thursday, stay 5 -> 40%", 17, Airline.DestinationType.INDIA, DayOfWeek.THURSDAY, 5, 40},
                {"Age 17, India, Thursday, stay 6 -> 50%", 17, Airline.DestinationType.INDIA, DayOfWeek.THURSDAY, 6, 50},
        };
    }

    @Test(dataProvider = "specialConditionThreeAndFour")
    public void testSpecialConditionThreeAndFour(String testCaseName,
                                        int passengerAgeInYears,
                                        Airline.DestinationType destinationType,
                                        DayOfWeek dayOfWeek,
                                        int stayLengthInDays,
                                        int expectedDiscount) {
        float actualDiscount = airline.calculateDiscount(passengerAgeInYears, destinationType, dayOfWeek, stayLengthInDays);
        assertEquals(actualDiscount, expectedDiscount, testCaseName);
    }
}
