package com.synthed.controller;

import com.synthed.dto.DocumentDTO;
import com.synthed.dto.DocumentSummaryDTO;
import com.synthed.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    private String getAuthenticatedEmail(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping
    public ResponseEntity<List<DocumentSummaryDTO>> getAllDocuments(Authentication authentication) {
        return ResponseEntity.ok(documentService.getAllDocuments(getAuthenticatedEmail(authentication)));
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentSummaryDTO> uploadDocument(@RequestParam("file") MultipartFile file, Authentication authentication) {
        return ResponseEntity.ok(documentService.saveDocument(file, getAuthenticatedEmail(authentication)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}