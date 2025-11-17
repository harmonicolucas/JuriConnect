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
} from '@mui/material';
import { Logout, Add, Chat, Description, Timeline } from '@mui/icons-material';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';

const ClientDashboard = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const [cases, setCases] = useState([]);
  const [stats, setStats] = useState({
    totalConsultations: 0,
    activeCases: 0,
    averageSatisfaction: 0,
  });

  useEffect(() => {
    loadCases();
    loadStats();
  }, []);

  const loadCases = async () => {
    try {
      const response = await api.get('/cases/client');
      setCases(response.data);
    } catch (error) {
      console.error('Erro ao carregar casos:', error);
    }
  };

  const loadStats = async () => {
    // Placeholder - implementar endpoint de estatísticas do cliente
    setStats({
      totalConsultations: cases.length,
      activeCases: cases.filter(c => c.status !== 'ENCERRADO').length,
      averageSatisfaction: 4.5,
    });
  };

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            JuriConnect - Dashboard do Cliente
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
                  Consultas Realizadas
                </Typography>
                <Typography variant="h4">{stats.totalConsultations}</Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <Typography color="text.secondary" gutterBottom>
                  Processos em Andamento
                </Typography>
                <Typography variant="h4">{stats.activeCases}</Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={4}>
            <Card>
              <CardContent>
                <Typography color="text.secondary" gutterBottom>
                  Satisfação Média
                </Typography>
                <Typography variant="h4">{stats.averageSatisfaction.toFixed(1)}</Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Box sx={{ mb: 3, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <Typography variant="h5">Meus Casos</Typography>
          <Button
            variant="contained"
            startIcon={<Add />}
            onClick={() => navigate('/case/new')}
          >
            Novo Caso
          </Button>
        </Box>

        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell>Advogado</TableCell>
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
                  <TableCell>{caseItem.lawyerName}</TableCell>
                  <TableCell>{caseItem.legalArea}</TableCell>
                  <TableCell>{caseItem.status}</TableCell>
                  <TableCell>
                    {new Date(caseItem.createdAt).toLocaleDateString('pt-MZ')}
                  </TableCell>
                  <TableCell>
                    <IconButton
                      size="small"
                      onClick={() => navigate(`/case/${caseItem.id}`)}
                      title="Ver Detalhes"
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

export default ClientDashboard;

