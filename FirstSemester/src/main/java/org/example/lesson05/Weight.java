package org.example.lesson05;

public class Weight {
    private static final double KG_PER_POUND = 0.45359237;

    private Weight() {
    }

    public static double convert(double value, String system) {
        double convertedValue;
        if ("Metric".equalsIgnoreCase(system)) {
            convertedValue = value / KG_PER_POUND;
        } else {
            convertedValue = value * KG_PER_POUND;
        }
        return roundToTwoDecimals(convertedValue);
    }

    private static double roundToTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
