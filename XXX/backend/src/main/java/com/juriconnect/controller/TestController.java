package com.juriconnect.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    
    @GetMapping("/ping")
    public ResponseEntity<Map<String, String>> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Backend está funcionando!");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/multipart")
    public ResponseEntity<Map<String, Object>> testMultipart(
            @RequestPart(value = "data", required = false) String data,
            @RequestPart(value = "file", required = false) org.springframework.web.multipart.MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("dataReceived", data != null);
        response.put("fileReceived", file != null);
        if (file != null) {
            response.put("fileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());
        }
        return ResponseEntity.ok(response);
    }
}

