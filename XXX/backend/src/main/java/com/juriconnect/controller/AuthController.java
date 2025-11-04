package com.juriconnect.controller;

import com.juriconnect.dto.AuthRequest;
import com.juriconnect.dto.AuthResponse;
import com.juriconnect.dto.ClientRegisterRequest;
import com.juriconnect.dto.ErrorResponse;
import com.juriconnect.dto.LawyerRegisterRequest;
import com.juriconnect.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@Valid @RequestBody ClientRegisterRequest request) {
        try {
            AuthResponse response = authService.registerClient(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage(), "VALIDATION_ERROR"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor: " + e.getMessage(), "INTERNAL_ERROR"));
        }
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        String message = errors.values().stream().findFirst().orElse("Erro de validação");
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(message, "VALIDATION_ERROR"));
    }
    
    @PostMapping(value = "/register/lawyer", consumes = "multipart/form-data")
    public ResponseEntity<?> registerLawyer(
            @RequestPart("data") String dataJson,
            @RequestPart("oamDocument") MultipartFile oamDocument) {
        try {
            System.out.println("=== REGISTRO DE ADVOGADO INICIADO ===");
            System.out.println("Data JSON recebido: " + dataJson);
            System.out.println("Arquivo recebido: " + (oamDocument != null ? oamDocument.getOriginalFilename() : "null"));
            System.out.println("Tamanho do arquivo: " + (oamDocument != null ? oamDocument.getSize() : "null"));
            
            // Deserializar JSON do FormData
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            LawyerRegisterRequest request = objectMapper.readValue(dataJson, LawyerRegisterRequest.class);
            System.out.println("JSON deserializado com sucesso");
            
            // Validar manualmente
            if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Nome completo é obrigatório", "VALIDATION_ERROR"));
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Email é obrigatório", "VALIDATION_ERROR"));
            }
            if (request.getOamNumber() == null || request.getOamNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Número da carteira OAM é obrigatório", "VALIDATION_ERROR"));
            }
            if (request.getSpecializations() == null || request.getSpecializations().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Pelo menos uma área de atuação deve ser selecionada", "VALIDATION_ERROR"));
            }
            
            System.out.println("Chamando authService.registerLawyer...");
            AuthResponse response = authService.registerLawyer(request, oamDocument);
            System.out.println("Registro concluído com sucesso!");
            return ResponseEntity.ok(response);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Erro ao processar dados: " + e.getMessage(), "VALIDATION_ERROR"));
        } catch (RuntimeException e) {
            System.err.println("RuntimeException: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage(), "VALIDATION_ERROR"));
        } catch (Exception e) {
            System.err.println("Exception geral: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor: " + e.getMessage(), "INTERNAL_ERROR"));
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(new ErrorResponse("Backend está funcionando!", "SUCCESS"));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage(), "AUTH_ERROR"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro interno do servidor: " + e.getMessage(), "INTERNAL_ERROR"));
        }
    }
}

