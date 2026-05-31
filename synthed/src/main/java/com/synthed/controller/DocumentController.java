package com.synthed.controller;

import com.synthed.dto.DocumentDTO;
import com.synthed.dto.DocumentSummaryDTO;
import com.synthed.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping
    public ResponseEntity<List<DocumentSummaryDTO>> getAllDocuments(Principal principal) {
        return ResponseEntity.ok(documentService.getAllDocuments(principal.getName()));
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentSummaryDTO> uploadDocument(@RequestParam("file") MultipartFile file, Principal principal) {
        return ResponseEntity.ok(documentService.saveDocument(file, principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id, Principal principal) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
