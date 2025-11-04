package com.juriconnect.dto;

import com.juriconnect.model.LegalCase;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCaseStatusRequest {
    
    @NotNull(message = "Status é obrigatório")
    private LegalCase.CaseStatus status;
}

