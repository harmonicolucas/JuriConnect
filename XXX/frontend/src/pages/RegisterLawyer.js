import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import {
  Container,
  Paper,
  TextField,
  Button,
  Typography,
  Box,
  Alert,
  Checkbox,
  FormControlLabel,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Chip,
} from '@mui/material';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';

const LEGAL_AREAS = [
  'PENAL',
  'COMERCIAL',
  'CIVIL',
  'TRABALHISTA',
  'ADMINISTRATIVO',
  'IMOBILIARIO',
  'FISCAL',
  'DIREITOS_HUMANOS',
  'MARITIMO',
  'AMBIENTAL',
];

const RegisterLawyer = () => {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    contact: '',
    password: '',
    confirmPassword: '',
    oamNumber: '',
    specializations: [],
    acceptTerms: false,
  });
  const [oamDocument, setOamDocument] = useState(null);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const value = e.target.type === 'checkbox' ? e.target.checked : e.target.value;
    setFormData({
      ...formData,
      [e.target.name]: value,
    });
  };

  const handleSpecializationsChange = (e) => {
    setFormData({
      ...formData,
      specializations: e.target.value,
    });
  };

  const handleFileChange = (e) => {
    setOamDocument(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (formData.password !== formData.confirmPassword) {
      setError('As palavras-passe não coincidem');
      return;
    }

    if (!formData.acceptTerms) {
      setError('Deve aceitar os Termos e Políticas');
      return;
    }

    if (formData.specializations.length === 0) {
      setError('Selecione pelo menos uma área de atuação');
      return;
    }

    if (!oamDocument) {
      setError('Documento da OAM é obrigatório');
      return;
    }

    // Validar tamanho do arquivo (50MB = 50 * 1024 * 1024 bytes)
    const maxSize = 50 * 1024 * 1024; // 50MB
    if (oamDocument.size > maxSize) {
      setError('Arquivo muito grande. Tamanho máximo permitido: 50MB. Por favor, use um arquivo menor.');
      return;
    }

    try {
      const formDataToSend = new FormData();
      formDataToSend.append('data', JSON.stringify({
        fullName: formData.fullName,
        email: formData.email,
        contact: formData.contact,
        password: formData.password,
        confirmPassword: formData.confirmPassword,
        oamNumber: formData.oamNumber,
        specializations: formData.specializations,
        acceptTerms: formData.acceptTerms,
      }));
      formDataToSend.append('oamDocument', oamDocument);

      // Não definir Content-Type manualmente - deixar o axios/browser definir automaticamente para FormData
      const response = await api.post('/auth/register/lawyer', formDataToSend);
      
      const { token, email, role, userId, fullName } = response.data;
      
      login(token, { email, role, userId, fullName });
      navigate('/dashboard/lawyer');
    } catch (err) {
      console.error('Erro ao registar advogado:', err);
      console.error('Código do erro:', err.code);
      console.error('Mensagem:', err.message);
      console.error('Resposta do servidor:', err.response?.data);
      console.error('Status:', err.response?.status);
      
      let errorMessage = 'Erro ao registar. Tente novamente.';
      
      if (err.code === 'ECONNABORTED' || err.message?.includes('timeout')) {
        errorMessage = 'Tempo de espera esgotado. Verifique se o backend está rodando.';
      } else if (err.response?.status === 413 || err.response?.status === 400) {
        if (err.response?.data?.message?.includes('tamanho') || err.response?.data?.message?.includes('size')) {
          errorMessage = 'Arquivo muito grande. Tamanho máximo permitido: 50MB. Por favor, use um arquivo menor.';
        } else {
          errorMessage = err.response?.data?.message || 'Erro ao processar requisição.';
        }
      } else if (!err.response) {
        errorMessage = 'Erro de rede. Verifique se o backend está rodando em http://localhost:8080';
      } else if (err.response?.data?.message) {
        errorMessage = err.response.data.message;
      } else if (err.response?.data?.error) {
        errorMessage = err.response.data.error;
      } else if (err.message) {
        errorMessage = err.message;
      }
      
      setError(errorMessage);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box sx={{ mt: 8, mb: 4 }}>
        <Paper elevation={3} sx={{ p: 4 }}>
          <Typography variant="h4" component="h1" gutterBottom align="center">
            Registar como Advogado
          </Typography>
          
          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}

          <form onSubmit={handleSubmit}>
            <TextField
              fullWidth
              label="Nome Completo"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="Email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="Contacto (+258 8XX XXX XXX)"
              name="contact"
              value={formData.contact}
              onChange={handleChange}
              margin="normal"
              required
              placeholder="+258 8XX XXX XXX"
            />
            <TextField
              fullWidth
              label="Número da Carteira OAM"
              name="oamNumber"
              value={formData.oamNumber}
              onChange={handleChange}
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="Palavra-passe"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="Confirmar Palavra-passe"
              name="confirmPassword"
              type="password"
              value={formData.confirmPassword}
              onChange={handleChange}
              margin="normal"
              required
            />
            <FormControl fullWidth margin="normal">
              <InputLabel>Áreas de Atuação</InputLabel>
              <Select
                multiple
                value={formData.specializations}
                onChange={handleSpecializationsChange}
                renderValue={(selected) => (
                  <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                    {selected.map((value) => (
                      <Chip key={value} label={value} size="small" />
                    ))}
                  </Box>
                )}
              >
                {LEGAL_AREAS.map((area) => (
                  <MenuItem key={area} value={area}>
                    {area}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <Box sx={{ mt: 2, mb: 2 }}>
              <input
                accept=".pdf,.doc,.docx,.jpg,.jpeg,.png"
                style={{ display: 'none' }}
                id="oam-document"
                type="file"
                onChange={handleFileChange}
              />
              <label htmlFor="oam-document">
                <Button variant="outlined" component="span" fullWidth>
                  {oamDocument ? oamDocument.name : 'Upload Declaração OAM'}
                </Button>
              </label>
            </Box>
            <FormControlLabel
              control={
                <Checkbox
                  name="acceptTerms"
                  checked={formData.acceptTerms}
                  onChange={handleChange}
                />
              }
              label="Aceito os Termos e Políticas"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Registar
            </Button>
          </form>

          <Box textAlign="center" sx={{ mt: 2 }}>
            <Typography variant="body2">
              Já tem uma conta? <Link to="/login">Fazer Login</Link>
            </Typography>
          </Box>
        </Paper>
      </Box>
    </Container>
  );
};

export default RegisterLawyer;

