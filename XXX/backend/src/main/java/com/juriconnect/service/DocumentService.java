package com.juriconnect.service;

import com.juriconnect.dto.DocumentDTO;
import com.juriconnect.model.Document;
import com.juriconnect.model.LegalCase;
import com.juriconnect.model.User;
import com.juriconnect.repository.DocumentRepository;
import com.juriconnect.repository.LegalCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    @Autowired
    private LegalCaseRepository caseRepository;
    
    private static final String UPLOAD_DIR = "./uploads";
    
    @Transactional
    public DocumentDTO uploadDocument(Long caseId, MultipartFile file) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Caso não encontrado"));
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!legalCase.getClient().getId().equals(currentUser.getId()) && 
            !legalCase.getLawyer().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        
        String filePath = saveFile(file);
        
        Document document = new Document();
        document.setLegalCase(legalCase);
        document.setUploadedBy(currentUser);
        document.setFileName(file.getOriginalFilename());
        document.setFilePath(filePath);
        document.setFileType(getFileType(file.getOriginalFilename()));
        document.setFileSize(file.getSize());
        
        document = documentRepository.save(document);
        
        return convertToDTO(document);
    }
    
    public List<DocumentDTO> getCaseDocuments(Long caseId) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Caso não encontrado"));
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!legalCase.getClient().getId().equals(currentUser.getId()) && 
            !legalCase.getLawyer().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        
        return documentRepository.findByLegalCase(legalCase).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private String saveFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
    
    private String getFileType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "pdf" -> "PDF";
            case "doc", "docx" -> "DOCX";
            case "jpg", "jpeg" -> "JPEG";
            case "png" -> "PNG";
            default -> extension.toUpperCase();
        };
    }
    
    private DocumentDTO convertToDTO(Document document) {
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
}

