package com.juriconnect.dto;

import com.juriconnect.model.LegalArea;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCaseRequest {
    
    @NotNull(message = "Área jurídica é obrigatória")
    private LegalArea legalArea;
    
    @NotNull(message = "ID do advogado é obrigatório")
    private Long lawyerId;
}

