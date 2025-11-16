package com.juriconnect.repository;

import com.juriconnect.model.Document;
import com.juriconnect.model.LegalCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByLegalCase(LegalCase legalCase);
}

