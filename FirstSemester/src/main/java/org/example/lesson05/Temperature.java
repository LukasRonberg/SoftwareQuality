package org.example.lesson05;

public class Temperature {
    private final double value;
    private final String sourceScale;

    public Temperature(double value, String sourceScale) {
        this.value = value;
        this.sourceScale = sourceScale;
    }

    public double convert(String destinationScale) {
        String source = sourceScale.toUpperCase();
        String destination = destinationScale.toUpperCase();
        String conversion = source + "_TO_" + destination;

        return switch (conversion) {
            case "C_TO_F" -> roundToTwoDecimals(celsiusToFahrenheit(value));
            case "C_TO_K" -> roundToTwoDecimals(celsiusToKelvin(value));
            case "F_TO_C" -> roundToTwoDecimals(fahrenheitToCelsius(value));
            case "F_TO_K" -> roundToTwoDecimals(fahrenheitToKelvin(value));
            case "K_TO_C" -> roundToTwoDecimals(kelvinToCelsius(value));
            case "K_TO_F" -> roundToTwoDecimals(kelvinToFahrenheit(value));
            default -> throw new IllegalArgumentException("Unsupported conversion: " + conversion);
        };
    }

    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    private double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    private double fahrenheitToKelvin(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0 + 273.15;
    }

    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private double kelvinToFahrenheit(double kelvin) {
        return (kelvin - 273.15) * 9.0 / 5.0 + 32.0;
    }

    private double roundToTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
