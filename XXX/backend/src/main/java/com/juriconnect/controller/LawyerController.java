package com.juriconnect.controller;

import com.juriconnect.dto.LawyerDTO;
import com.juriconnect.model.LegalArea;
import com.juriconnect.service.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lawyers")
@CrossOrigin(origins = "http://localhost:3000")
public class LawyerController {
    
    @Autowired
    private LawyerService lawyerService;
    
    @GetMapping
    public ResponseEntity<List<LawyerDTO>> getAllLawyers() {
        try {
            List<LawyerDTO> lawyers = lawyerService.getAllLawyers();
            return ResponseEntity.ok(lawyers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<LawyerDTO>> getAvailableLawyers(@RequestParam LegalArea legalArea) {
        try {
            List<LawyerDTO> lawyers = lawyerService.getAvailableLawyers(legalArea);
            return ResponseEntity.ok(lawyers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LawyerDTO> getLawyerById(@PathVariable Long id) {
        try {
            LawyerDTO lawyer = lawyerService.getLawyerById(id);
            return ResponseEntity.ok(lawyer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

