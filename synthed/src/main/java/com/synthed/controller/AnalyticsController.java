package com.synthed.controller;

import com.synthed.model.PredictionRequest;
import com.synthed.model.PredictionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final WebClient webClient;

    public AnalyticsController(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> getPrediction(@RequestBody PredictionRequest request) {
        try {
            PredictionResponse response = webClient.post()
                    .uri("/predict")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PredictionResponse.class)
                    .block();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}