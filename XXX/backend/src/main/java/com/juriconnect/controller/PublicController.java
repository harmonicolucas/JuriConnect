package com.juriconnect.controller;

import com.juriconnect.model.LegalArea;
import com.juriconnect.repository.LawyerRepository;
import com.juriconnect.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "http://localhost:3000")
public class PublicController {
    
    @Autowired
    private LawyerRepository lawyerRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalLawyers", lawyerRepository.findByActiveTrue().size());
        stats.put("totalClients", clientRepository.count());
        stats.put("satisfactionRate", 4.5); // Placeholder
        stats.put("legalAreas", LegalArea.values());
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/legal-areas")
    public ResponseEntity<LegalArea[]> getLegalAreas() {
        return ResponseEntity.ok(LegalArea.values());
    }
}

