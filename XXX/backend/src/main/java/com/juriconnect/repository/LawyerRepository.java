package com.juriconnect.repository;

import com.juriconnect.model.Lawyer;
import com.juriconnect.model.LegalArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LawyerRepository extends JpaRepository<Lawyer, Long> {
    Optional<Lawyer> findByOamNumber(String oamNumber);
    boolean existsByOamNumber(String oamNumber);
    List<Lawyer> findBySpecializationsContaining(LegalArea legalArea);
    List<Lawyer> findByActiveTrue();
}

