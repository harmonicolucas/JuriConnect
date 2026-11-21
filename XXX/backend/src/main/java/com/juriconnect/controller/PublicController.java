package com.juriconnect.controller;

import com.juriconnect.model.LegalArea;
import com.juriconnect.model.Review;
import com.juriconnect.repository.ClientRepository;
import com.juriconnect.repository.LawyerRepository;
import com.juriconnect.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "http://localhost:3000")
public class PublicController {

    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        List<Review> reviews = reviewRepository.findAll();
        double satisfactionRate = 0;
        if (!reviews.isEmpty()) {
            double average = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
            satisfactionRate = average / 5.0 * 100.0;
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("totalLawyers", lawyerRepository.count());
        body.put("totalClients", clientRepository.count());
        body.put("satisfactionRate", satisfactionRate);
        body.put("legalAreas", Arrays.stream(LegalArea.values()).map(LegalArea::getDescription).toList());
        return body;
    }
}
