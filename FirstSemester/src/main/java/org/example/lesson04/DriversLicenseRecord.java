package org.example.lesson04;

public record DriversLicenseRecord(
        boolean grantDriversLicense,
        boolean repeatTheory,
        boolean repeatPractical,
        boolean takeExtraDrivingLessons
) {}
