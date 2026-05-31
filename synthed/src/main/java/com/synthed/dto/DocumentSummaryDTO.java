package com.synthed.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentSummaryDTO {
    private Long id;
    private String fileName;
    private LocalDateTime uploadDate;
}
