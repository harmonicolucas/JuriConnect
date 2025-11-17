import React, { useState, useEffect, useRef } from 'react';
import {
  Box, TextField, Button, Paper, Typography, List, ListItem,
} from '@mui/material';
import { Send } from '@mui/icons-material';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import api from '../services/api';
import { useAuth } from '../context/AuthContext';

const Chat = ({ caseId }) => {
  const { user } = useAuth();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const messagesEndRef = useRef(null);

  // Carrega mensagens + WebSocket
  useEffect(() => {
    const loadAndConnect = async () => {
      await loadMessages();
      connectWebSocket();
    };

    loadAndConnect();

    return () => stompClient?.deactivate();
  }, [caseId]);

  useEffect(() => scrollToBottom(), [messages]);

  const loadMessages = async () => {
    try {
      const { data } = await api.get(`/messages/case/${caseId}`);
      setMessages(data);
    } catch (error) {
      console.error('Erro ao carregar mensagens:', error);
    }
  };

  const connectWebSocket = () => {
    const socket = new SockJS('http://localhost:8080/ws');
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        client.subscribe(`/topic/case/${caseId}/messages`, (msg) => {
          setMessages((prev) => [...prev, JSON.parse(msg.body)]);
        });
      },
    });

    client.activate();
  };

  const sendMessage = async () => {
    if (!newMessage.trim()) return;

    try {
      await api.post(`/messages/case/${caseId}`, newMessage, {
        headers: { 'Content-Type': 'text/plain' },
      });
      setNewMessage('');
    } catch (error) {
      console.error('Erro ao enviar mensagem:', error);
    }
  };

  const scrollToBottom = () =>
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });

  return (
    <Box sx={{ height: 600, display: 'flex', flexDirection: 'column' }}>
      {/* Área de mensagens */}
      <Paper variant="outlined" sx={{ flex: 1, overflow: 'auto', p: 2, mb: 2 }}>
        <List>
          {messages.map((msg) => {
            const isMine = msg.senderId === user?.userId;
            return (
              <ListItem key={msg.id} sx={{ justifyContent: isMine ? 'flex-end' : 'flex-start' }}>
                <Box
                  sx={{
                    maxWidth: '70%',
                    p: 1.5,
                    borderRadius: 2,
                    bgcolor: isMine ? 'primary.main' : 'grey.300',
                    color: isMine ? 'white' : 'text.primary',
                  }}
                >
                  <Typography variant="body2" fontWeight="bold">{msg.senderName}</Typography>
                  <Typography variant="body1">{msg.content}</Typography>
                  <Typography variant="caption" sx={{ opacity: 0.7 }}>
                    {new Date(msg.sentAt).toLocaleString('pt-MZ')}
                  </Typography>
                </Box>
              </ListItem>
            );
          })}
          <div ref={messagesEndRef} />
        </List>
      </Paper>

      {/* Campo de envio */}
      <Box sx={{ display: 'flex', gap: 1 }}>
        <TextField
          fullWidth
          placeholder="Digite sua mensagem..."
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
        />
        <Button variant="contained" onClick={sendMessage} startIcon={<Send />}>
          Enviar
        </Button>
      </Box>
    </Box>
  );
};

export default Chat;
