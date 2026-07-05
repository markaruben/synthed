package com.synthed.model;

import lombok.Data;

@Data
public class FlashcardUpdateRequest {
    private String question;
    private String answer;
}
