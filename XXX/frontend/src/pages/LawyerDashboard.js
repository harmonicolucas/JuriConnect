import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Button,
  Grid,
  Card,
  CardContent,
  Box,
  AppBar,
  Toolbar,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Select,
  MenuItem,
  FormControl,
} from '@mui/material';
import { Logout, Chat } from '@mui/icons-material';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';

const LawyerDashboard = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const [cases, setCases] = useState([]);
  const [stats, setStats] = useState({
    clientsAttended: 0,
    activeClients: 0,
    averageRating: 0,
  });

  useEffect(() => {
    loadCases();
    loadStats();
  }, []);

  const loadCases = async () => {
    try {
      const response = await api.get('/cases/lawyer');
      setCases(response.data);
    } catch (error) {
      console.error('Erro ao carregar casos:', error);
    }
  };

  const loadStats = async () => {
    // Placeholder - implementar endpoint de estatísticas do advogado
    setStats({
      clientsAttended: cases.length,
      activeClients: cases.filter(c => c.status !== 'ENCERRADO').length,
      averageRating: 4.5,
    });
  };

  const handleStatusChange = async (caseId, newStatus) => {
    try {
      await api.put(`/cases/${caseId}/status`, { status: newStatus });
      loadCases();
    } catch (error) {
      console.error('Erro ao atualizar status:', error);
    }
  };

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const statusOptions = [
    'PROCESSO_INICIADO',
    'DOCUMENTACAO_RECEBIDA',
    'PETICAO_PROTOCOLADA',
    'AUDIENCIA_MARCADA',
    'JULGAMENTO_REALIZADO',
    'SENTENCA_PROFERIDA',
    'ACORDO_EXTRAJUDICIAL',
    'EXECUCAO_SENTENCA',
    'ENCERRADO',
  ];

  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            JuriConnect - Dashboard do Advogado
          </Typography>
          <Typography sx={{ mr: 2 }}>Olá, {user?.fullName}</Typography>
          <IconButton color="inherit" onClick={handleLogout}>
            <Logout />
          </IconButton>
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Grid container spacing={3} sx={{ mb: 4 }}>
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <Typography color="text.secondary" gutterBottom>
                  Clientes Atendidos
                </Typography>
                <Typography variant="h4">{stats.clientsAttended}</Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <Typography color="text.secondary" gutterBottom>
                  Clientes em Atendimento
                </Typography>
                <Typography variant="h4">{stats.activeClients}</Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <Typography color="text.secondary" gutterBottom>
                  Avaliação Média
                </Typography>
                <Typography variant="h4">{stats.averageRating.toFixed(1)}</Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Typography variant="h5" sx={{ mb: 3 }}>
          Meus Casos
        </Typography>

        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell>Cliente</TableCell>
                <TableCell>Área Jurídica</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Data</TableCell>
                <TableCell>Ações</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {cases.map((caseItem) => (
                <TableRow key={caseItem.id}>
                  <TableCell>{caseItem.id}</TableCell>
                  <TableCell>{caseItem.clientName}</TableCell>
                  <TableCell>{caseItem.legalArea}</TableCell>
                  <TableCell>
                    <FormControl size="small" sx={{ minWidth: 200 }}>
                      <Select
                        value={caseItem.status}
                        onChange={(e) => handleStatusChange(caseItem.id, e.target.value)}
                      >
                        {statusOptions.map((status) => (
                          <MenuItem key={status} value={status}>
                            {status.replace(/_/g, ' ')}
                          </MenuItem>
                        ))}
                      </Select>
                    </FormControl>
                  </TableCell>
                  <TableCell>
                    {new Date(caseItem.createdAt).toLocaleDateString('pt-MZ')}
                  </TableCell>
                  <TableCell>
                    <IconButton
                      size="small"
                      onClick={() => navigate(`/case/${caseItem.id}`)}
                      title="Abrir Chat"
                    >
                      <Chat />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Container>
    </Box>
  );
};

export default LawyerDashboard;

