package com.synthed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PredictionResultDTO {
    private String documentId;
    private Double predictedScore;
    private LocalDateTime createdAt;
}