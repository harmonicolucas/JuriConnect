package com.juriconnect.service;

import com.juriconnect.dto.MessageDTO;
import com.juriconnect.model.LegalCase;
import com.juriconnect.model.Message;
import com.juriconnect.model.User;
import com.juriconnect.repository.LegalCaseRepository;
import com.juriconnect.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private LegalCaseRepository caseRepository;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Transactional
    public MessageDTO sendMessage(Long caseId, String content) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Caso não encontrado"));
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!legalCase.getClient().getId().equals(currentUser.getId()) && 
            !legalCase.getLawyer().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        
        Message message = new Message();
        message.setLegalCase(legalCase);
        message.setSender(currentUser);
        message.setContent(content);
        message.setRead(false);
        
        message = messageRepository.save(message);
        
        MessageDTO dto = convertToDTO(message);
        
        // Enviar via WebSocket
        messagingTemplate.convertAndSend("/topic/case/" + caseId + "/messages", dto);
        
        return dto;
    }
    
    public List<MessageDTO> getCaseMessages(Long caseId) {
        LegalCase legalCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Caso não encontrado"));
        
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!legalCase.getClient().getId().equals(currentUser.getId()) && 
            !legalCase.getLawyer().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado");
        }
        
        return messageRepository.findByLegalCaseOrderBySentAtAsc(legalCase).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
        message.setRead(true);
        messageRepository.save(message);
    }
    
    private MessageDTO convertToDTO(Message message) {
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

