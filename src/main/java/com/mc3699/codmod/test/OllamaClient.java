package com.mc3699.codmod.test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import net.minecraft.server.MinecraftServer;

public class OllamaClient {
    private static final String OLLAMA_URL = "http://127.0.0.1:11434/v1/chat/completions";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final int MAX_MESSAGES = 25;
    private static final List<Message> history = Collections.synchronizedList(new ArrayList<>());
    private static final String SYSTEM_PROMPT = """
            You may only generate responses that fit on minecraft signs using the following rules:
            - Responses must be under 64 characters
            - Responses must be sarcastic
            - DO NOT INCLUDE \\n in your messages!!
            - Do not surround messages in quotes (" ")
            """;

    private static class Message {
        String role;
        String content;

        Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    // Escape special characters for JSON
    private static String escapeJson(String input) {
        if (input == null) return "";
        StringBuilder escaped = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (c) {
                case '\"': escaped.append("\\\""); break;
                case '\\': escaped.append("\\\\"); break;
                case '\b': escaped.append("\\b"); break;
                case '\f': escaped.append("\\f"); break;
                case '\r': escaped.append("\\r"); break;
                case '\t': escaped.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) c));
                    } else {
                        escaped.append(c);
                    }
            }
        }
        return escaped.toString();
    }

    public static CompletableFuture<String> queryOllama(String model, String prompt, MinecraftServer server) {
        // Add user prompt to history
        synchronized (history) {
            history.add(new Message("user", prompt));
            if (history.size() > MAX_MESSAGES) {
                history.remove(0);
            }
        }

        // Build messages array with system prompt and history
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", SYSTEM_PROMPT));
        messages.addAll(history);

        String messagesJson = messages.stream()
                .map(msg -> String.format("{\"role\": \"%s\", \"content\": \"%s\"}", msg.role, escapeJson(msg.content)))
                .collect(Collectors.joining(",", "[", "]"));

        // Construct JSON payload
        String json = String.format("{\"model\": \"%s\", \"messages\": %s}", model, messagesJson);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        return "Error: HTTP " + response.statusCode() + " - " + response.body();
                    }
                    String body = response.body();
                    int start = body.indexOf("\"content\":\"") + 11;
                    int end = body.indexOf("\"", start);
                    String content = start > 10 && end > start ? body.substring(start, end) : "Error: Invalid response";

                    // Process command if present
                    String cleanContent = content;

                    // Add AI response to history (cleaned, without command)
                    synchronized (history) {
                        history.add(new Message("assistant", cleanContent));
                        if (history.size() > MAX_MESSAGES) {
                            history.remove(0);
                        }
                    }
                    return cleanContent;
                })
                .exceptionally(e -> {
                    if (e.getCause() instanceof java.net.ConnectException) {
                        return "Error: Ollama server not reachable. Is it running?";
                    }
                    return "Error: " + e.getMessage();
                });
    }
}