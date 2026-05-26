package com.aidevops;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
import java.util.List;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final WebClient webClient;

    public GroqService() {
        this.webClient = WebClient.builder().build();
    }

    public String askGroq(String userMessage) {
        String systemPrompt = "You are an expert AI DevOps Assistant. " +
            "You help developers with Docker, Jenkins, Maven, Kubernetes, CI/CD pipelines, " +
            "cloud deployments, and all DevOps related topics. " +
            "Give clear, practical, and concise answers.";

        Map<String, Object> requestBody = Map.of(
            "model", model,
            "messages", List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", userMessage)
            ),
            "max_tokens", 1024,
            "temperature", 0.7
        );

        try {
            Map response = webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

            List<Map> choices = (List<Map>) response.get("choices");
            Map message = (Map) choices.get(0).get("message");
            return (String) message.get("content");

        } catch (Exception e) {
            return "Error calling Groq API: " + e.getMessage();
        }
    }
}