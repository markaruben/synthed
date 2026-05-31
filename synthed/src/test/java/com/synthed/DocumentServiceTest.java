//package com.synthed;
//
//import com.synthed.dto.DocumentSummaryDTO;
//import com.synthed.repository.DocumentRepository;
//import com.synthed.service.DocumentService;
//import com.synthed.service.PdfService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@Transactional
//public class DocumentServiceTest {
//
//    @Autowired
//    private DocumentService documentService;
//    @MockitoBean
//    private PdfService pdfService;
//
//    @Autowired
//    private DocumentRepository documentRepository;
//
//    private MockMultipartFile fakeFile;
//
//    @BeforeEach
//    void setUp() {
//        fakeFile = new MockMultipartFile("file", "curs_test.pdf", "application/pdf", "Random content".getBytes());
//    }
//
//    @Test
//    void testSaveDocument_ShouldSaveToDatabase() {
//        when(pdfService.extractText(any())).thenReturn("Text Extracted for testing");
//
//        DocumentSummaryDTO savedDoc = documentService.saveDocument(fakeFile);
//
//        assertNotNull(savedDoc.getId(), "The Document should have a generated ID!");
//        assertEquals("curs_test.pdf", savedDoc.getFileName(), "The file name should be correct!");
//
//        long count = documentRepository.count();
//        assertEquals(1, count, "There should be a document in the database!");
//    }
//
//}
