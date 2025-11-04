package com.juriconnect.controller;

import com.juriconnect.dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    
    @MessageMapping("/case/{caseId}/send")
    @SendTo("/topic/case/{caseId}/messages")
    public MessageDTO sendMessage(@Payload MessageDTO messageDTO) {
        // A mensagem já foi salva via REST API, apenas retornamos
        return messageDTO;
    }
}

