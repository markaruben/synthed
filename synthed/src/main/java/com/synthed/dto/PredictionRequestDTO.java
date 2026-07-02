package com.synthed.dto;

import lombok.Data;

@Data
public class PredictionRequestDTO {

    private Long documentId;
    private Integer flashcardSuccessRate;
    private Double previousScore;
    private Integer studyHours;


    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Integer getFlashcardSuccessRate() {
        return flashcardSuccessRate;
    }

    public void setFlashcardSuccessRate(Integer flashcardSuccessRate) {
        this.flashcardSuccessRate = flashcardSuccessRate;
    }

    public Double getPreviousScore() {
        return previousScore;
    }

    public void setPreviousScore(Double previousScore) {
        this.previousScore = previousScore;
    }

    public Integer getStudyHours() {
        return studyHours;
    }

    public void setStudyHours(Integer studyHours) {
        this.studyHours = studyHours;
    }
}