package com.synthed.service;

import com.synthed.dto.DocumentDTO;
import com.synthed.dto.DocumentSummaryDTO;
import com.synthed.model.Document;
import com.synthed.model.Flashcard;
import com.synthed.model.User;
import com.synthed.repository.DocumentRepository;
import com.synthed.repository.UserRepository;
import com.synthed.utils.DocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final PdfService pdfService;
    private final UserRepository userRepository;
    private final AiService aiService;
    private final ObjectMapper objectMapper;

    public DocumentSummaryDTO saveDocument(MultipartFile file, String username) {
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user: " + username));

        String content = pdfService.extractText(file);

        Document doc = new Document();
        doc.setFileName(file.getOriginalFilename());
        doc.setContent(content);
        doc.setUploadDate(LocalDateTime.now());
        doc.setUser(currentUser);

        String textToProcess = content.length() > 500000 ? content.substring(0, 500000) : content;

        try {
            String rawAiResponse = aiService.generateFlashcardsFromJson(textToProcess);

            String cleanJson = rawAiResponse.replaceAll("```json", "")
                    .replaceAll("```", "");

            JsonNode rootNode = objectMapper.readTree(cleanJson);
            JsonNode flashcardsNode = rootNode;

            if (rootNode.isObject() && rootNode.has("flashcards")) {
                flashcardsNode = rootNode.get("flashcards");
            } else if (rootNode.isObject() && rootNode.has("questions")) {
                flashcardsNode = rootNode.get("questions");
            }

            List<Flashcard> flashcards = new ArrayList<>();

            if (flashcardsNode.isArray()) {
                for (JsonNode node : flashcardsNode) {
                    Flashcard card = new Flashcard();
                    card.setQuestion(node.has("question") ? node.get("question").asText() : "N/A");
                    card.setAnswer(node.has("answer") ? node.get("answer").asText() : "N/A");
                    card.setDocument(doc);
                    flashcards.add(card);
                }
            } else if (flashcardsNode.isObject() && flashcardsNode.has("question")) {
                Flashcard card = new Flashcard();
                card.setQuestion(flashcardsNode.get("question").asText());
                card.setAnswer(flashcardsNode.has("answer") ? flashcardsNode.get("answer").asText() : "N/A");
                card.setDocument(doc);
                flashcards.add(card);
            } else {
                System.err.println("Failed to parase: Invalid JSON structure.");
            }

            doc.setFlashcards(flashcards);
            System.out.println("Successfully generated " + flashcards.size() + " flashcards.");

        } catch (Exception e) {
            System.err.println("Failed to generate flashcards: " + e.getMessage());
            throw new RuntimeException("Failed to process document.", e);
        }

        Document savedDoc = documentRepository.save(doc);
        return DocumentMapper.mapToSummary(savedDoc);
    }

    public List<DocumentSummaryDTO> getAllDocuments(String username) {
        return documentRepository.findAllByUser_Username(username).stream()
                .map(DocumentMapper::mapToSummary)
                .collect(Collectors.toList());
    }

    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documentul with " + id + " not found!"));
        return DocumentMapper.mapToDetail(document);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}