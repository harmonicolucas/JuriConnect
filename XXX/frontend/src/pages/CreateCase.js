import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Paper,
  Typography,
  Button,
  Box,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Grid,
  Card,
  CardContent,
  CardMedia,
  Alert,
} from '@mui/material';
import { useDropzone } from 'react-dropzone';
import api from '../services/api';

const LEGAL_AREAS = [
  { value: 'PENAL', label: 'Direito Penal' },
  { value: 'COMERCIAL', label: 'Direito Comercial' },
  { value: 'CIVIL', label: 'Direito Civil' },
  { value: 'TRABALHISTA', label: 'Direito Trabalhista' },
  { value: 'ADMINISTRATIVO', label: 'Direito Administrativo' },
  { value: 'IMOBILIARIO', label: 'Direito Imobiliário' },
  { value: 'FISCAL', label: 'Direito Fiscal' },
  { value: 'DIREITOS_HUMANOS', label: 'Direitos Humanos' },
  { value: 'MARITIMO', label: 'Direito Marítimo' },
  { value: 'AMBIENTAL', label: 'Direito Ambiental' },
];

const CreateCase = () => {
  const navigate = useNavigate();
  const [selectedArea, setSelectedArea] = useState('');
  const [lawyers, setLawyers] = useState([]);
  const [selectedLawyer, setSelectedLawyer] = useState(null);
  const [documents, setDocuments] = useState([]);
  const [error, setError] = useState('');

  const { getRootProps, getInputProps } = useDropzone({
    accept: {
      'application/pdf': ['.pdf'],
      'application/msword': ['.doc'],
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document': ['.docx'],
      'image/jpeg': ['.jpg', '.jpeg'],
      'image/png': ['.png'],
    },
    onDrop: (acceptedFiles) => {
      setDocuments([...documents, ...acceptedFiles]);
    },
  });

  useEffect(() => {
    if (selectedArea) {
      loadLawyers();
    }
  }, [selectedArea]);

  const loadLawyers = async () => {
    try {
      const response = await api.get(`/lawyers/available?legalArea=${selectedArea}`);
      setLawyers(response.data);
    } catch (error) {
      console.error('Erro ao carregar advogados:', error);
    }
  };

  const handleCreateCase = async () => {
    if (!selectedArea || !selectedLawyer) {
      setError('Selecione a área jurídica e um advogado');
      return;
    }

    try {
      // Criar caso
      const caseResponse = await api.post('/cases', {
        legalArea: selectedArea,
        lawyerId: selectedLawyer.id,
      });

      const caseId = caseResponse.data.id;

      // Upload de documentos
      for (const doc of documents) {
        const formData = new FormData();
        formData.append('file', doc);
        await api.post(`/documents/case/${caseId}`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });
      }

      navigate(`/case/${caseId}`);
    } catch (error) {
      setError('Erro ao criar caso. Tente novamente.');
    }
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={3} sx={{ p: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Novo Caso
        </Typography>

        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        <Box sx={{ mt: 4 }}>
          <FormControl fullWidth sx={{ mb: 3 }}>
            <InputLabel>Área Jurídica</InputLabel>
            <Select
              value={selectedArea}
              onChange={(e) => setSelectedArea(e.target.value)}
            >
              {LEGAL_AREAS.map((area) => (
                <MenuItem key={area.value} value={area.value}>
                  {area.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <Box sx={{ mb: 3 }}>
            <Typography variant="h6" gutterBottom>
              Upload de Documentos (Opcional)
            </Typography>
            <Box
              {...getRootProps()}
              sx={{
                border: '2px dashed #ccc',
                borderRadius: 2,
                p: 3,
                textAlign: 'center',
                cursor: 'pointer',
                '&:hover': {
                  borderColor: 'primary.main',
                },
              }}
            >
              <input {...getInputProps()} />
              <Typography>
                Arraste arquivos aqui ou clique para selecionar
              </Typography>
              <Typography variant="body2" color="text.secondary">
                PDF, DOCX, JPEG, PNG (máx. 10MB)
              </Typography>
            </Box>
            {documents.length > 0 && (
              <Box sx={{ mt: 2 }}>
                {documents.map((doc, index) => (
                  <Typography key={index} variant="body2">
                    {doc.name}
                  </Typography>
                ))}
              </Box>
            )}
          </Box>

          {selectedArea && (
            <Box sx={{ mb: 3 }}>
              <Typography variant="h6" gutterBottom>
                Advogados Disponíveis
              </Typography>
              <Grid container spacing={2}>
                {lawyers.map((lawyer) => (
                  <Grid item xs={12} sm={6} md={4} key={lawyer.id}>
                    <Card
                      sx={{
                        cursor: 'pointer',
                        border: selectedLawyer?.id === lawyer.id ? 2 : 0,
                        borderColor: 'primary.main',
                      }}
                      onClick={() => setSelectedLawyer(lawyer)}
                    >
                      <CardContent>
                        <Typography variant="h6">{lawyer.fullName}</Typography>
                        <Typography variant="body2" color="text.secondary">
                          {lawyer.specializations.join(', ')}
                        </Typography>
                        <Typography variant="body2">
                          Casos concluídos: {lawyer.casesCompleted}
                        </Typography>
                        <Typography variant="body2">
                          Avaliação: {lawyer.averageRating.toFixed(1)} ⭐
                        </Typography>
                      </CardContent>
                    </Card>
                  </Grid>
                ))}
              </Grid>
            </Box>
          )}

          <Button
            variant="contained"
            size="large"
            onClick={handleCreateCase}
            disabled={!selectedArea || !selectedLawyer}
            fullWidth
          >
            Criar Caso e Iniciar Chat
          </Button>
        </Box>
      </Paper>
    </Container>
  );
};

export default CreateCase;

