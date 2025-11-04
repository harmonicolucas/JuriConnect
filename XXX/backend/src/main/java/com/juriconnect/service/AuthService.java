package com.juriconnect.service;

import com.juriconnect.dto.AuthRequest;
import com.juriconnect.dto.AuthResponse;
import com.juriconnect.dto.ClientRegisterRequest;
import com.juriconnect.dto.LawyerRegisterRequest;
import com.juriconnect.model.Client;
import com.juriconnect.model.Lawyer;
import com.juriconnect.model.User;
import com.juriconnect.repository.ClientRepository;
import com.juriconnect.repository.LawyerRepository;
import com.juriconnect.repository.UserRepository;
import com.juriconnect.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private LawyerRepository lawyerRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    private static final String UPLOAD_DIR = "./uploads";
    
    @Transactional
    public AuthResponse registerClient(ClientRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("As palavras-passe não coincidem");
        }
        
        if (request.getAcceptTerms() == null || !request.getAcceptTerms()) {
            throw new RuntimeException("Deve aceitar os Termos e Políticas");
        }
        
        Client client = new Client();
        client.setEmail(request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setFullName(request.getFullName());
        client.setContact(request.getContact());
        client.setRole(User.UserRole.CLIENT);
        client.setActive(true);
        
        client = clientRepository.save(client);
        
        String token = tokenProvider.generateToken(client.getEmail(), client.getRole().name());
        return new AuthResponse(token, client.getEmail(), client.getRole().name(), 
                client.getId(), client.getFullName());
    }
    
    @Transactional
    public AuthResponse registerLawyer(LawyerRegisterRequest request, MultipartFile oamDocument) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        
        if (lawyerRepository.existsByOamNumber(request.getOamNumber())) {
            throw new RuntimeException("Número da carteira OAM já está em uso");
        }
        
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("As palavras-passe não coincidem");
        }
        
        if (request.getAcceptTerms() == null || !request.getAcceptTerms()) {
            throw new RuntimeException("Deve aceitar os Termos e Políticas");
        }
        
        if (oamDocument == null || oamDocument.isEmpty()) {
            throw new RuntimeException("Documento da OAM é obrigatório");
        }
        
        String documentPath = saveFile(oamDocument);
        
        Lawyer lawyer = new Lawyer();
        lawyer.setEmail(request.getEmail());
        lawyer.setPassword(passwordEncoder.encode(request.getPassword()));
        lawyer.setFullName(request.getFullName());
        lawyer.setContact(request.getContact());
        lawyer.setRole(User.UserRole.LAWYER);
        lawyer.setActive(true);
        lawyer.setOamNumber(request.getOamNumber());
        lawyer.setOamDocumentPath(documentPath);
        lawyer.setSpecializations(request.getSpecializations());
        
        lawyer = lawyerRepository.save(lawyer);
        
        String token = tokenProvider.generateToken(lawyer.getEmail(), lawyer.getRole().name());
        return new AuthResponse(token, lawyer.getEmail(), lawyer.getRole().name(), 
                lawyer.getId(), lawyer.getFullName());
    }
    
    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = (User) authentication.getPrincipal();
        String token = tokenProvider.generateToken(user.getEmail(), user.getRole().name());
        
        return new AuthResponse(token, user.getEmail(), user.getRole().name(), 
                user.getId(), user.getFullName());
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
}

