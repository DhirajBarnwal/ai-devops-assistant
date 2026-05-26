package com.aidevops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class DevOpsController {

    @Autowired
    private GroqService groqService;

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "AI DevOps Assistant is running");
        response.put("version", "2.0.0");
        response.put("ai", "Powered by Groq Llama3");
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("health", "UP");
        response.put("service", "AI DevOps Assistant");
        return response;
    }

    @PostMapping("/analyze")
    public Map<String, String> analyzePipeline(@RequestBody Map<String, String> request) {
        String question = request.getOrDefault("question", "How can I improve my DevOps pipeline?");
        String aiResponse = groqService.askGroq(question);
        Map<String, String> response = new HashMap<>();
        response.put("question", question);
        response.put("answer", aiResponse);
        response.put("model", "llama3-8b-8192");
        return response;
    }

    @PostMapping("/ask")
    public Map<String, String> askDevOps(@RequestBody Map<String, String> request) {
        String question = request.getOrDefault("question", "What is DevOps?");
        String aiResponse = groqService.askGroq(question);
        Map<String, String> response = new HashMap<>();
        response.put("question", question);
        response.put("answer", aiResponse);
        return response;
    }
}