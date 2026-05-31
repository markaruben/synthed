package com.synthed.controller;

import com.synthed.service.PdfService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final PdfService pdfService;

    public TestController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/upload")
    public String uploadCheck(@RequestParam("file") MultipartFile file) {
        String text = pdfService.extractText(file);
        return "SUCCES! Extracted " + text.length() + " characters.\n\nPreview:\n" + text.substring(0, Math.min(text.length(), 200)) + "...";
    }
}
