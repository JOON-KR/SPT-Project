package com.sptp.dawnary.global.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GcpUtil {

    @Value("${GCP-API-KEY}")
    private String APIKEY;
    private final String URL = "https://language.googleapis.com/v2/documents:analyzeSentiment?key=";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public double getSentiment(String content) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, String> document = new HashMap<>();
        document.put("type", "PLAIN_TEXT");
        document.put("languageCode", "ko");
        document.put("content", content);

        requestBody.put("document", document);
        requestBody.put("encodingType", "UTF8");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                URL + APIKEY,
                HttpMethod.POST,
                entity,
                String.class
        );

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            return root.path("documentSentiment").path("score").asDouble();
        } catch (Exception e) {
            throw new RuntimeException("sentiment error: " + e.getMessage(), e);
        }
    }
}
