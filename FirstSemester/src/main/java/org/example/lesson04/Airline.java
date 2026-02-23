package org.example.lesson04;
import java.time.DayOfWeek;

public class Airline {

    public enum DestinationType {
        INDIA,
        OUTSIDE_INDIA
    }

    public int calculateDiscount(
            int passengerAgeInYears,
            DestinationType destinationType,
            DayOfWeek departureDayOfWeek,
            int stayLengthInDays) {

        if (passengerAgeInYears < 0) {
            throw new IllegalArgumentException("passengerAgeInYears must be >= 0");
        }
        if (stayLengthInDays < 0) {
            throw new IllegalArgumentException("stayLengthInDays must be >= 0");
        }

        // Children 2 and under -> free
        if (passengerAgeInYears <= 2) {
            return 100;
        }

        int totalDiscountRate = 0;

        // Age 3–17 -> 40%
        if (passengerAgeInYears <= 17) {
            totalDiscountRate = 40;
        }
        // Adults > 18 -> destination rules
        else if (passengerAgeInYears > 18) {
            boolean departureOnMondayOrFriday =
                    departureDayOfWeek == DayOfWeek.MONDAY ||
                            departureDayOfWeek == DayOfWeek.FRIDAY;

            if (!departureOnMondayOrFriday) {
                if (destinationType == DestinationType.INDIA) {
                    totalDiscountRate = 20;
                } else {
                    totalDiscountRate = 25;
                }
            }
        }
        // Age == 18 -> no discount

        // Additional 10% if staying at least 6 days AND already has discount
        if (totalDiscountRate > 0 && stayLengthInDays >= 6) {
            totalDiscountRate += 10;
        }

        return totalDiscountRate;
    }
}
