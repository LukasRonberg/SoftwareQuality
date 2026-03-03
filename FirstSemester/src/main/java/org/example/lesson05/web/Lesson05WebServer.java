package org.example.lesson05.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.lesson05.Currency;
import org.example.lesson05.Length;
import org.example.lesson05.Temperature;
import org.example.lesson05.Weight;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Lesson05WebServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", new StaticFileHandler("lesson05-web/index.html", "text/html; charset=utf-8"));
        server.createContext("/styles.css", new StaticFileHandler("lesson05-web/styles.css", "text/css; charset=utf-8"));
        server.createContext("/app.js", new StaticFileHandler("lesson05-web/app.js", "application/javascript; charset=utf-8"));

        server.createContext("/api/length", exchange -> handleApi(exchange, Lesson05WebServer::handleLength));
        server.createContext("/api/weight", exchange -> handleApi(exchange, Lesson05WebServer::handleWeight));
        server.createContext("/api/temperature", exchange -> handleApi(exchange, Lesson05WebServer::handleTemperature));
        server.createContext("/api/currency", exchange -> handleApi(exchange, Lesson05WebServer::handleCurrency));
        server.createContext("/api/grades", exchange -> handleApi(exchange, Lesson05WebServer::handleGrades));

        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("Lesson05 web interface running at http://localhost:" + PORT);
    }

    private static String handleLength(Map<String, String> params) {
        double value = parseDouble(params, "value");
        String system = required(params, "system");
        Length length = new Length(value, system);
        return "{\"result\":" + length.convert() + "}";
    }

    private static String handleWeight(Map<String, String> params) {
        double value = parseDouble(params, "value");
        String system = required(params, "system");
        double result = Weight.convert(value, system);
        return "{\"result\":" + result + "}";
    }

    private static String handleTemperature(Map<String, String> params) {
        double value = parseDouble(params, "value");
        String source = required(params, "source");
        String destination = required(params, "destination");
        Temperature temperature = new Temperature(value, source);
        return "{\"result\":" + temperature.convert(destination) + "}";
    }

    private static String handleCurrency(Map<String, String> params) {
        String base = required(params, "base");
        double amount = parseDouble(params, "amount");
        String destination = required(params, "destination");
        Currency currency = new Currency(base);
        double result = currency.convert(amount, destination);
        return "{\"result\":" + result + "}";
    }

    private static String handleGrades(Map<String, String> params) {
        return "{\"message\":\"Grades class currently has no conversion method implemented.\"}";
    }

    private static void handleApi(HttpExchange exchange, ApiHandler apiHandler) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            Map<String, String> params = parseQuery(exchange.getRequestURI().getRawQuery());
            String response = apiHandler.handle(params);
            sendJson(exchange, 200, response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            sendJson(exchange, 400, "{\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        } catch (RuntimeException e) {
            sendJson(exchange, 500, "{\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
        }
    }

    private static void sendJson(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(bytes);
        }
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null || query.isBlank()) {
            return result;
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            if (pair.isBlank()) {
                continue;
            }
            String[] keyValue = pair.split("=", 2);
            String key = decode(keyValue[0]);
            String value = keyValue.length > 1 ? decode(keyValue[1]) : "";
            result.put(key, value);
        }
        return result;
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private static String required(Map<String, String> params, String key) {
        String value = params.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing parameter: " + key);
        }
        return value;
    }

    private static double parseDouble(Map<String, String> params, String key) {
        String raw = required(params, key);
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number for parameter: " + key);
        }
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    @FunctionalInterface
    private interface ApiHandler {
        String handle(Map<String, String> params);
    }

    private static class StaticFileHandler implements HttpHandler {
        private final String resourcePath;
        private final String contentType;

        private StaticFileHandler(String resourcePath, String contentType) {
            this.resourcePath = resourcePath;
            this.contentType = contentType;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            byte[] body = readResource(resourcePath);
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        }

        private byte[] readResource(String path) throws IOException {
            try (InputStream stream = Lesson05WebServer.class.getClassLoader().getResourceAsStream(path)) {
                if (stream == null) {
                    throw new IOException("Resource not found: " + path);
                }
                return stream.readAllBytes();
            }
        }
    }
}
