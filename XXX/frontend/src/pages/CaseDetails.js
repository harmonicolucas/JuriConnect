import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Container,
  Paper,
  Typography,
  Box,
  Tabs,
  Tab,
  Button,
} from '@mui/material';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';
import Chat from '../components/Chat';
import DocumentList from '../components/DocumentList';
import CaseTimeline from '../components/CaseTimeline';

const CaseDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [caseData, setCaseData] = useState(null);
  const [tabValue, setTabValue] = useState(0);

  useEffect(() => {
    loadCase();
  }, [id]);

  const loadCase = async () => {
    try {
      const response = await api.get(`/cases/${id}`);
      setCaseData(response.data);
    } catch (error) {
      console.error('Erro ao carregar caso:', error);
    }
  };

  const handleTabChange = (event, newValue) => {
    setTabValue(newValue);
  };

  if (!caseData) {
    return <div>Carregando...</div>;
  }

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" gutterBottom>
          Caso #{caseData.id}
        </Typography>
        <Typography variant="body1" color="text.secondary" gutterBottom>
          Área: {caseData.legalArea} | Status: {caseData.status}
        </Typography>

        <Box sx={{ borderBottom: 1, borderColor: 'divider', mt: 3 }}>
          <Tabs value={tabValue} onChange={handleTabChange}>
            <Tab label="Chat" />
            <Tab label="Documentos" />
            <Tab label="Acompanhamento" />
          </Tabs>
        </Box>

        <Box sx={{ mt: 3 }}>
          {tabValue === 0 && <Chat caseId={id} />}
          {tabValue === 1 && <DocumentList caseId={id} />}
          {tabValue === 2 && <CaseTimeline caseData={caseData} />}
        </Box>
      </Paper>
    </Container>
  );
};

export default CaseDetails;

