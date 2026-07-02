package com.synthed.model;

import lombok.Data;

@Data
public class PredictionRequest {
    private Double study_hours;
    private Double previous_grade;
}