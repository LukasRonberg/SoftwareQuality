package org.example.lesson04;

public class DriverLicense {

    public DriversLicenseRecord passOrFail(int theoreticalPoints, int practicalErrors) {
        if (theoreticalPoints < 0 || theoreticalPoints > 100) {
            throw new IllegalArgumentException("Theoretical points must be between 0 and 100");
        }
        if (practicalErrors < 0) {
            throw new IllegalArgumentException("Practical errors cannot be negative");
        }

        boolean passTheory = theoreticalPoints >= 85;
        boolean passPractical = practicalErrors <= 2;

        boolean grantDriversLicense = passTheory && passPractical;
        boolean repeatTheory = !passTheory;
        boolean repeatPractical = !passPractical;

        boolean takeExtraDrivingLessons = repeatTheory && repeatPractical;

        return new DriversLicenseRecord(
                grantDriversLicense,
                repeatTheory,
                repeatPractical,
                takeExtraDrivingLessons
        );
    }
}
