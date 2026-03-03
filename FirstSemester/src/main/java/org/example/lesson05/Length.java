package org.example.lesson05;

public class Length {
    private static final double CM_PER_INCH = 2.54;

    private final double value;
    private final String system;

    public Length(double value, String system) {
        this.value = value;
        this.system = system;
    }

    public double convert() {
        double convertedValue;
        if ("Metric".equalsIgnoreCase(system)) {
            convertedValue = value / CM_PER_INCH;
        } else {
            convertedValue = value * CM_PER_INCH;
        }
        return roundToTwoDecimals(convertedValue);
    }

    private double roundToTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
