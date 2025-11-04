package com.juriconnect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientRegisterRequest {
    
    @NotBlank(message = "Nome completo é obrigatório")
    private String fullName;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;
    
    @NotBlank(message = "Contacto é obrigatório")
    @Pattern(regexp = "^\\+258 8[0-9]{2} [0-9]{3} [0-9]{3}$", message = "Contacto deve estar no formato +258 8XX XXX XXX")
    private String contact;
    
    @NotBlank(message = "Palavra-passe é obrigatória")
    private String password;
    
    @NotBlank(message = "Confirmação de palavra-passe é obrigatória")
    private String confirmPassword;
    
    private Boolean acceptTerms;
}

