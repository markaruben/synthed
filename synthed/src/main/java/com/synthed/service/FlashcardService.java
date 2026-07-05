package com.synthed.service;

import com.synthed.model.Flashcard;
import com.synthed.model.FlashcardUpdateRequest;
import com.synthed.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Transactional
    public Optional<Flashcard> updateFlashcard(Long id, FlashcardUpdateRequest request) {
        return flashcardRepository.findById(id).map(flashcard -> {
            flashcard.setQuestion(request.getQuestion());
            flashcard.setAnswer(request.getAnswer());
            return flashcardRepository.save(flashcard);
        });
    }

    @Transactional
    public boolean deleteFlashcard(Long id) {
        if (flashcardRepository.existsById(id)) {
            flashcardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}