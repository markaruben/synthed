package com.synthed.controller;

import com.synthed.model.Flashcard;
import com.synthed.model.FlashcardUpdateRequest;
import com.synthed.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlashcard(@PathVariable Long id, @RequestBody FlashcardUpdateRequest request) {
        Optional<Flashcard> updatedFlashcard = flashcardService.updateFlashcard(id, request);

        return updatedFlashcard
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFlashcard(@PathVariable Long id) {
        boolean isDeleted = flashcardService.deleteFlashcard(id);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}