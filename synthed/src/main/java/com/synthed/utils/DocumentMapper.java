package com.synthed.utils;

import com.synthed.dto.DocumentDTO;
import com.synthed.dto.DocumentSummaryDTO;
import com.synthed.dto.FlashcardDTO;
import com.synthed.model.Document;

import java.util.List;
import java.util.stream.Collectors;

public class DocumentMapper {

    public static DocumentSummaryDTO mapToSummary(Document doc) {
        DocumentSummaryDTO dto = new DocumentSummaryDTO();
        dto.setId(doc.getId());
        dto.setFileName(doc.getFileName());
        dto.setUploadDate(doc.getUploadDate());
        return dto;
    }

    public static DocumentDTO mapToDetail(Document doc) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(doc.getId());
        dto.setFileName(doc.getFileName());
        dto.setUploadDate(doc.getUploadDate());
        dto.setContent(doc.getContent());

        if (doc.getFlashcards() != null) {
            dto.setFlashcards(getFlashcardDTOS(doc));
        }

        return dto;
    }

    private static List<FlashcardDTO> getFlashcardDTOS(Document doc) {
        return doc.getFlashcards().stream()
                .map(card -> new FlashcardDTO(card.getId(), card.getQuestion(), card.getAnswer()))
                .collect(Collectors.toList());
    }
}
