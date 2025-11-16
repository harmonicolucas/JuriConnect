package com.juriconnect.repository;

import com.juriconnect.model.Lawyer;
import com.juriconnect.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByLawyer(Lawyer lawyer);
    boolean existsByLegalCaseId(Long caseId);
}

