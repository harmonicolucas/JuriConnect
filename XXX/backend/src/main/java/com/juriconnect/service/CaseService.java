package com.juriconnect.service;

import com.juriconnect.dto.CaseDTO;
import com.juriconnect.dto.CreateCaseRequest;
import com.juriconnect.dto.DocumentDTO;
import com.juriconnect.dto.MessageDTO;
import com.juriconnect.dto.UpdateCaseStatusRequest;
import com.juriconnect.model.Client;
import com.juriconnect.model.Document;
import com.juriconnect.model.LegalCase;
import com.juriconnect.model.Lawyer;
import com.juriconnect.model.Message;
import com.juriconnect.model.User;
import com.juriconnect.repository.ClientRepository;
import com.juriconnect.repository.LegalCaseRepository;
import com.juriconnect.repository.LawyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaseService {
    
    @Autowired
    private LegalCaseRepository caseRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private LawyerRepository lawyerRepository;
    
    @Transactional
    public CaseDTO createCase(CreateCaseRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        Lawyer lawyer = lawyerRepository.findById(request.getLawyerId())
                .orElseThrow(() -> new RuntimeException("Advogado não encontrado"));
        
        if (!lawyer.getSpecializations().contains(request.getLegalArea())) {
            throw new RuntimeException("Advogado não possui especialização na área selecionada");
        }
        
        LegalCase legalCase = new LegalCase();
        legalCase.setClient(client);
        legalCase.setLawyer(lawyer);
        legalCase.setLegalArea(request.getLegalArea());
        legalCase.setStatus(LegalCase.CaseStatus.PROCESSO_INICIADO);
        
        legalCase = caseRepository.save(legalCase);
        
        client.setActiveCases(client.getActiveCases() + 1);
        client.setTotalConsultations(client.getTotalConsultations() + 1);
        clientRepository.save(client);
        
        return convertToDTO(legalCase);
    }
    
    public List<CaseDTO> getClientCases() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Client client = clientRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        
        return caseRepository.findByClient(client).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CaseDTO> getLawyerCases() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Lawyer lawyer = lawyerRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Advogado não encontrado"));
        
        return caseRepository.findByLawyer(lawyer).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CaseDTO getCaseById(Long caseId) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Caso não encontrado"));
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!legalCase.getClient().getId().equals(currentUser.getId()) && 
            !legalCase.getLawyer().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        
        return convertToDTO(legalCase);
    }
    
    @Transactional
    public CaseDTO updateCaseStatus(Long caseId, UpdateCaseStatusRequest request) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Caso não encontrado"));
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!legalCase.getLawyer().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Apenas o advogado pode atualizar o status do caso");
        }
        
        legalCase.setStatus(request.getStatus());
        legalCase = caseRepository.save(legalCase);
        
        if (request.getStatus() == LegalCase.CaseStatus.ENCERRADO) {
            Client client = legalCase.getClient();
            client.setActiveCases(Math.max(0, client.getActiveCases() - 1));
            clientRepository.save(client);
        }
        
        return convertToDTO(legalCase);
    }
    
    private CaseDTO convertToDTO(LegalCase legalCase) {
        CaseDTO dto = new CaseDTO();
        dto.setId(legalCase.getId());
        dto.setClientId(legalCase.getClient().getId());
        dto.setClientName(legalCase.getClient().getFullName());
        dto.setLawyerId(legalCase.getLawyer().getId());
        dto.setLawyerName(legalCase.getLawyer().getFullName());
        dto.setLegalArea(legalCase.getLegalArea());
        dto.setStatus(legalCase.getStatus());
        dto.setCreatedAt(legalCase.getCreatedAt());
        dto.setUpdatedAt(legalCase.getUpdatedAt());
        
        dto.setDocuments(legalCase.getDocuments().stream()
                .map(this::convertDocumentToDTO)
                .collect(Collectors.toList()));
        
        dto.setMessages(legalCase.getMessages().stream()
                .map(this::convertMessageToDTO)
                .collect(Collectors.toList()));
        
        return dto;
    }
    
    private DocumentDTO convertDocumentToDTO(Document document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setFileName(document.getFileName());
        dto.setFilePath(document.getFilePath());
        dto.setFileType(document.getFileType());
        dto.setFileSize(document.getFileSize());
        dto.setUploadedByName(document.getUploadedBy().getFullName());
        dto.setUploadedAt(document.getUploadedAt());
        return dto;
    }
    
    private MessageDTO convertMessageToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setSenderId(message.getSender().getId());
        dto.setSenderName(message.getSender().getFullName());
        dto.setSenderRole(message.getSender().getRole().name());
        dto.setContent(message.getContent());
        dto.setSentAt(message.getSentAt());
        dto.setRead(message.getRead());
        return dto;
    }
}

