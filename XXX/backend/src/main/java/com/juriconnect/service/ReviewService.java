package com.juriconnect.service;

import com.juriconnect.dto.ReviewRequest;
import com.juriconnect.model.Client;
import com.juriconnect.model.LegalCase;
import com.juriconnect.model.Lawyer;
import com.juriconnect.model.Review;
import com.juriconnect.model.User;
import com.juriconnect.repository.LegalCaseRepository;
import com.juriconnect.repository.LawyerRepository;
import com.juriconnect.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private LegalCaseRepository caseRepository;
    
    @Autowired
    private LawyerRepository lawyerRepository;
    
    @Transactional
    public Review createReview(Long caseId, ReviewRequest request) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Caso não encontrado"));
        
        if (legalCase.getStatus() != LegalCase.CaseStatus.ENCERRADO) {
            throw new RuntimeException("Apenas casos encerrados podem ser avaliados");
        }
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!legalCase.getClient().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Apenas o cliente pode avaliar o caso");
        }
        
        if (reviewRepository.existsByLegalCaseId(caseId)) {
            throw new RuntimeException("Caso já foi avaliado");
        }
        
        Review review = new Review();
        review.setLegalCase(legalCase);
        review.setClient(legalCase.getClient());
        review.setLawyer(legalCase.getLawyer());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        
        review = reviewRepository.save(review);
        
        // Atualizar avaliação média do advogado
        updateLawyerRating(legalCase.getLawyer());
        
        // Atualizar satisfação média do cliente
        updateClientSatisfaction(legalCase.getClient());
        
        return review;
    }
    
    private void updateLawyerRating(Lawyer lawyer) {
        List<Review> reviews = reviewRepository.findByLawyer(lawyer);
        if (!reviews.isEmpty()) {
            double average = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            lawyer.setAverageRating(average);
            lawyer.setTotalRatings(reviews.size());
            lawyerRepository.save(lawyer);
        }
    }
    
    private void updateClientSatisfaction(Client client) {
        // Implementar lógica similar se necessário
    }
}

