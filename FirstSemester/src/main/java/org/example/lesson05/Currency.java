package org.example.lesson05;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Currency {
    private static final String API_KEY = "cur_live_jPgWIwtE8qLPdEwbtrsrCAoS3YCd4tqIdYzRycEt";
    private static final String API_URL_TEMPLATE =
            "https://freecurrencyapi.net/api/v2/latest?apikey=%s&base_currency=%s";

    private final String baseCurrency;
    private final HttpClient httpClient;

    public Currency(String baseCurrency) {
        this.baseCurrency = baseCurrency.toUpperCase();
        this.httpClient = HttpClient.newHttpClient();
    }

    public double convert(double amount, String destinationCurrency) {
        String destination = destinationCurrency.toUpperCase();
        double rate = fetchRate(destination);
        return roundToTwoDecimals(amount * rate);
    }

    public double convert(double amount) {
        return convert(amount, "USD");
    }

    private double fetchRate(String destinationCurrency) {
        String url = String.format(
                API_URL_TEMPLATE,
                URLEncoder.encode(API_KEY, StandardCharsets.UTF_8),
                URLEncoder.encode(baseCurrency, StandardCharsets.UTF_8)
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IllegalStateException("Currency API request failed with status " + response.statusCode());
            }
            return extractRate(response.body(), destinationCurrency);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to call currency API", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to call currency API", e);
        }
    }

    private double extractRate(String jsonBody, String destinationCurrency) {
        Pattern ratePattern = Pattern.compile("\"" + Pattern.quote(destinationCurrency) + "\"\\s*:\\s*([0-9]+(?:\\.[0-9]+)?)");
        Matcher matcher = ratePattern.matcher(jsonBody);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Rate not found for currency: " + destinationCurrency);
        }
        return Double.parseDouble(matcher.group(1));
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
