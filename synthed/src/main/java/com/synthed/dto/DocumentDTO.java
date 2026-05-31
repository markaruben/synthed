package com.synthed.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DocumentDTO {
    private Long id;
    private String fileName;
    private String content;
    private LocalDateTime uploadDate;
    private List<FlashcardDTO> flashcards;
}
