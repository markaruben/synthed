package com.synthed.controller;

import com.synthed.dto.PredictionResultDTO;
import com.synthed.model.PredictionRequest;
import com.synthed.model.PredictionResponse;
import com.synthed.model.PredictionResult;
import com.synthed.model.User;
import com.synthed.repository.PredictionResultRepository;
import com.synthed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final WebClient webClient;
    private final PredictionResultRepository predictionResultRepository;
    private final UserRepository userRepository;

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> getPrediction(@RequestBody PredictionRequest request, Principal principal) {
        try {
            PredictionResponse response = webClient.post()
                    .uri("/predict")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PredictionResponse.class)
                    .block();

            if (response != null && principal != null) {
                User user = userRepository.findByEmail(principal.getName())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                PredictionResult result = PredictionResult.builder()
                        .documentId(request.getDocumentId())
                        .predictedScore(response.getPredicted_score())
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .build();

                predictionResultRepository.save(result);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    @GetMapping("/scores")
    public ResponseEntity<List<PredictionResultDTO>> getUserScores(Principal principal) {

        List<PredictionResult> entities = predictionResultRepository.findByUserEmailOrderByCreatedAtDesc(principal.getName());

        List<PredictionResultDTO> dtos = entities.stream()
                .map(entity -> PredictionResultDTO.builder()
                        .documentId(entity.getDocumentId())
                        .predictedScore(entity.getPredictedScore())
                        .createdAt(entity.getCreatedAt())
                        .build())
                .toList();

        return ResponseEntity.ok(dtos);
    }
}