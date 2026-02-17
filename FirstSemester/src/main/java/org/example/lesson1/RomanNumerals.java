package org.example.lesson1;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class RomanNumerals {

    private final Map<String, Integer> numerals = new HashMap<>();

    private static final Set<String> NON_REPEATABLE_NUMERALS = Set.of("V", "L", "D");
    private static final Set<String> REPEATABLE_NUMERALS = Set.of("I", "X", "C", "M");
    private static final Set<String> SUBTRACTIVE_NUMERALS = Set.of("I", "X", "C");

    public RomanNumerals() {
        numerals.put("I", 1);
        numerals.put("V", 5);
        numerals.put("X", 10);
        numerals.put("L", 50);
        numerals.put("C", 100);
        numerals.put("D", 500);
        numerals.put("M", 1000);
    }

    public int calculateRomanNumerals(String romanNumerals) {
        if (romanNumerals == null || romanNumerals.trim().isEmpty()) {
            throw new IllegalArgumentException("Roman numeral string must not be null/empty.");
        }

        romanNumerals = romanNumerals.toUpperCase();

        int numeralDecimalAmount = 0;

        String previousNumeral = null;
        int repeatCount = 0;

        for (int index = 0; index < romanNumerals.length(); index++) {
            String currentNumeral = String.valueOf(romanNumerals.charAt(index));

            checkNumeralIsValid(currentNumeral);

            // repetition tracking
            if (currentNumeral.equals(previousNumeral)) {
                repeatCount++;
            } else {
                repeatCount = 1;
                previousNumeral = currentNumeral;
            }

            if (NON_REPEATABLE_NUMERALS.contains(currentNumeral) && repeatCount > 1) {
                log.info("V, L or D must not be repeated. Found: {}", currentNumeral);
                throw new IllegalArgumentException("V, L or D must not be repeated. Found: " + currentNumeral);
            }

            if (REPEATABLE_NUMERALS.contains(currentNumeral) && repeatCount > 3) {
                log.info("I, X, C, M can be repeated max 3 times. Found: {} repeated {} times", currentNumeral, repeatCount);
                throw new IllegalArgumentException("Too many repeats of: " + currentNumeral);
            }

            int currentValue = getValue(currentNumeral);

            // lookahead for subtractive case
            if (index + 1 < romanNumerals.length()) {
                String nextNumeral = String.valueOf(romanNumerals.charAt(index + 1));

                checkNumeralIsValid(nextNumeral);

                int nextValue = getValue(nextNumeral);

                if (currentValue < nextValue) {
                    // Only I, X, C can subtract
                    if (!SUBTRACTIVE_NUMERALS.contains(currentNumeral)) {
                        log.info("Only I, X, C can be subtractive. Found subtractive: {}", currentNumeral);
                        throw new IllegalArgumentException("Invalid subtractive numeral: " + currentNumeral);
                    }

                    // Must be a valid subtractive pair (I before V/X, X before L/C, C before D/M)
                    if (!isValidSubtractivePair(currentNumeral, nextNumeral)) {
                        log.info("Invalid subtractive pair: {}{}", currentNumeral, nextNumeral);
                        throw new IllegalArgumentException("Invalid subtractive pair: " + currentNumeral + nextNumeral);
                    }

                    // Cannot subtract with repeats like IIX, XXC, etc.
                    if (repeatCount > 1) {
                        log.info("Cannot use repeated numeral in subtractive form. Found: {}", romanNumerals.substring(index - (repeatCount - 1), index + 2));
                        throw new IllegalArgumentException("Repeated subtractive numeral is invalid.");
                    }

                    numeralDecimalAmount += (nextValue - currentValue);
                    index++; // skip next numeral because we consumed it as a pair
                    continue;
                }
            }

            numeralDecimalAmount += currentValue;
        }

        if (numeralDecimalAmount > 3999) {
            log.info("Largest representable value is 3999. Found: {}", numeralDecimalAmount);
            throw new IllegalArgumentException("Roman numeral value exceeds 3999: " + numeralDecimalAmount);
        }

        return numeralDecimalAmount;
    }

    private void checkNumeralIsValid(String numeral) {
        if (!numerals.containsKey(numeral)) {
            log.info("Invalid numeral: {}", numeral);
            throw new IllegalArgumentException("Invalid numeral: " + numeral);
        }
    }

    private Integer getValue(String key) {
        return numerals.get(key);
    }

    private boolean isValidSubtractivePair(String currentNumeral, String nextNumeral) {
        return (currentNumeral.equals("I") && (nextNumeral.equals("V") || nextNumeral.equals("X")))
                || (currentNumeral.equals("X") && (nextNumeral.equals("L") || nextNumeral.equals("C")))
                || (currentNumeral.equals("C") && (nextNumeral.equals("D") || nextNumeral.equals("M")));
    }
}
