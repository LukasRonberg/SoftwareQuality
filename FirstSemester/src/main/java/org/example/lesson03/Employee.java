package org.example.lesson03;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Employee {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String cpr;                 // 10 numeric digits
    private String firstName;           // 1-30 chars: letters, spaces, dash
    private String lastName;            // 1-30 chars: letters, spaces, dash
    private String department;          // HR, Finance, IT, Sales, General Services
    private int baseSalary;             // 20000..100000
    private int educationalLevel;       // 0..3
    private LocalDate dateOfBirth;      // at least 18 years ago
    private LocalDate dateOfEmployment; // <= today
    private String country;             // country name


    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    // Getter returns the NAME of the level
    public String getEducationalLevel() {
        return switch (educationalLevel) {
            case 0 -> "none";
            case 1 -> "primary";
            case 2 -> "secondary";
            case 3 -> "tertiary";
            default -> "unknown";
        };
    }

    public void setEducationalLevel(int educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

    public String getDateOfBirth() {
        return dateOfBirth == null ? null : dateOfBirth.format(DATE_FORMATTER);
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = LocalDate.parse(dateOfBirth, DATE_FORMATTER);
    }

    public String getDateOfEmployment() {
        return dateOfEmployment == null ? null : dateOfEmployment.format(DATE_FORMATTER);
    }

    public void setDateOfEmployment(String dateOfEmployment) {
        this.dateOfEmployment = LocalDate.parse(dateOfEmployment, DATE_FORMATTER);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Actual salary = base salary + (educational level * 1220)
    public int getSalary() {
        return baseSalary + (educationalLevel * 1220);
    }

    // Discount = years of employment * 0.5
    public double getDiscount() {
        if (dateOfEmployment == null) {
            return 0.0;
        }
        int yearsOfEmployment = Period.between(dateOfEmployment, LocalDate.now()).getYears();
        if (yearsOfEmployment < 0) {
            yearsOfEmployment = 0;
        }
        return yearsOfEmployment * 0.5;
    }

    // Shipping cost percentage:
    // Denmark/Norway/Sweden: 0%, Iceland/Finland: 50%, others: 100%
    public int getShippingCosts() {
        if (country == null) {
            return 100;
        }

        String normalizedCountry = country.trim().toLowerCase(Locale.ROOT);

        if (normalizedCountry.equals("denmark")
                || normalizedCountry.equals("norway")
                || normalizedCountry.equals("sweden")) {
            return 0;
        }

        if (normalizedCountry.equals("iceland")
                || normalizedCountry.equals("finland")) {
            return 50;
        }

        return 100;
    }
}
