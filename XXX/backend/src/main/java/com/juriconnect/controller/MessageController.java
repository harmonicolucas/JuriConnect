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

    @GetMapping("/case/{caseId}")
    public ResponseEntity<List<MessageDTO>> getCaseMessages(@PathVariable Long caseId) {
        try {
            return ResponseEntity.ok(messageService.getCaseMessages(caseId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/case/{caseId}", consumes = "text/plain")
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable Long caseId, @RequestBody String content) {
        try {
            return ResponseEntity.ok(messageService.sendMessage(caseId, content));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
