package com.juriconnect.repository;

import com.juriconnect.model.Client;
import com.juriconnect.model.Lawyer;
import com.juriconnect.model.LegalCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegalCaseRepository extends JpaRepository<LegalCase, Long> {
    List<LegalCase> findByClient(Client client);
    List<LegalCase> findByLawyer(Lawyer lawyer);
    List<LegalCase> findByClientAndStatusNot(Client client, LegalCase.CaseStatus status);
    List<LegalCase> findByLawyerAndStatusNot(Lawyer lawyer, LegalCase.CaseStatus status);
}

