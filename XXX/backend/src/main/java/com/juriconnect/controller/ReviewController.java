package com.juriconnect.controller;

import com.juriconnect.dto.ReviewRequest;
import com.juriconnect.model.Review;
import com.juriconnect.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @PostMapping("/case/{caseId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long caseId,
            @Valid @RequestBody ReviewRequest request) {
        try {
            Review review = reviewService.createReview(caseId, request);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

