package com.juriconnect.controller;

import com.juriconnect.dto.MessageDTO;
import com.juriconnect.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    
    @Autowired
    private MessageService messageService;
    
    @PostMapping("/case/{caseId}")
    public ResponseEntity<MessageDTO> sendMessage(
            @PathVariable Long caseId,
            @RequestBody String content) {
        try {
            MessageDTO message = messageService.sendMessage(caseId, content);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/case/{caseId}")
    public ResponseEntity<List<MessageDTO>> getCaseMessages(@PathVariable Long caseId) {
        try {
            List<MessageDTO> messages = messageService.getCaseMessages(caseId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        try {
            messageService.markAsRead(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

