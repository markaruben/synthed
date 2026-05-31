package com.synthed.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardDTO {
    private Long id;
    private String question;
    private String answer;
}
