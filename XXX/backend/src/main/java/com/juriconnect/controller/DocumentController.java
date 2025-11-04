package com.juriconnect.controller;

import com.juriconnect.dto.DocumentDTO;
import com.juriconnect.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController {
    
    @Autowired
    private DocumentService documentService;
    
    @PostMapping("/case/{caseId}")
    public ResponseEntity<DocumentDTO> uploadDocument(
            @PathVariable Long caseId,
            @RequestParam("file") MultipartFile file) {
        try {
            DocumentDTO document = documentService.uploadDocument(caseId, file);
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/case/{caseId}")
    public ResponseEntity<List<DocumentDTO>> getCaseDocuments(@PathVariable Long caseId) {
        try {
            List<DocumentDTO> documents = documentService.getCaseDocuments(caseId);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

