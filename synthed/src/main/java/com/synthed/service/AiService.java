package com.synthed.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiService {
    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${openai.api.key}")
    private String apiKey;

    public AiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String generateFlashcardsFromJson(String documentText) {
        String systemPrompt = "You are an expert teacher. Extract ALL key educational concepts from the provided text. " +
                "Create a comprehensive set of flashcards covering the important topics. " +
                "Generate an appropriate number of flashcards based on the text's length and density. " +
                "You MUST output a JSON object containing a single key called \"flashcards\". " +
                "The value of \"flashcards\" MUST be an array of objects. " +
                "Each object must have exactly two keys: \"question\" and \"answer\". " +
                "CRITICAL: Write the questions and answers in the EXACT SAME LANGUAGE as the provided text.";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");

        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_object");
        requestBody.put("response_format", responseFormat);

        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", "Text to analyze:\n" + documentText)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            System.out.println("Sending text to OpenAI API...");
            ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, requestEntity, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());
            String aiResponseText = root.path("choices").get(0).path("message").path("content").asText();

            System.out.println("OpenAI successfully generated the flashcards!");
            return aiResponseText;

        } catch (Exception e) {
            System.err.println("Error while communicating with OpenAI: " + e.getMessage());
            throw new RuntimeException("Failed to generate flashcards using OpenAI.");
        }
    }
}