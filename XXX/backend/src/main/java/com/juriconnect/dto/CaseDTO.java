package com.juriconnect.dto;

import com.juriconnect.model.LegalArea;
import com.juriconnect.model.LegalCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private Long lawyerId;
    private String lawyerName;
    private LegalArea legalArea;
    private LegalCase.CaseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<DocumentDTO> documents;
    private List<MessageDTO> messages;
}

