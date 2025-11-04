package com.juriconnect.controller;

import com.juriconnect.dto.CaseDTO;
import com.juriconnect.dto.CreateCaseRequest;
import com.juriconnect.dto.UpdateCaseStatusRequest;
import com.juriconnect.service.CaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
@CrossOrigin(origins = "http://localhost:3000")
public class CaseController {
    
    @Autowired
    private CaseService caseService;
    
    @PostMapping
    public ResponseEntity<CaseDTO> createCase(@Valid @RequestBody CreateCaseRequest request) {
        try {
            CaseDTO caseDTO = caseService.createCase(request);
            return ResponseEntity.ok(caseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/client")
    public ResponseEntity<List<CaseDTO>> getClientCases() {
        try {
            List<CaseDTO> cases = caseService.getClientCases();
            return ResponseEntity.ok(cases);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/lawyer")
    public ResponseEntity<List<CaseDTO>> getLawyerCases() {
        try {
            List<CaseDTO> cases = caseService.getLawyerCases();
            return ResponseEntity.ok(cases);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CaseDTO> getCaseById(@PathVariable Long id) {
        try {
            CaseDTO caseDTO = caseService.getCaseById(id);
            return ResponseEntity.ok(caseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<CaseDTO> updateCaseStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCaseStatusRequest request) {
        try {
            CaseDTO caseDTO = caseService.updateCaseStatus(id, request);
            return ResponseEntity.ok(caseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

