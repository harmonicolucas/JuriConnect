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
} from '@mui/material';
import { Scale, Gavel, People, TrendingUp } from '@mui/icons-material';
import api from '../services/api';

const LandingPage = () => {
  const navigate = useNavigate();
  const [stats, setStats] = useState({
    totalLawyers: 0,
    totalClients: 0,
    satisfactionRate: 0,
    legalAreas: [],
  });

  useEffect(() => {
    api.get('/public/stats')
      .then(response => setStats(response.data))
      .catch(error => console.error('Erro ao carregar estatísticas:', error));
  }, []);

  const legalAreas = [
    'Direito Penal',
    'Direito Comercial',
    'Direito Civil',
    'Direito Trabalhista',
    'Direito Administrativo',
    'Direito Imobiliário',
    'Direito Fiscal',
    'Direitos Humanos',
    'Direito Marítimo',
    'Direito Ambiental',
  ];

  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Scale sx={{ mr: 2 }} />
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            JuriConnect
          </Typography>
          <Button color="inherit" onClick={() => navigate('/login')}>
            Login
          </Button>
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ mt: 8, mb: 8 }}>
        <Box textAlign="center" mb={6}>
          <Typography variant="h2" component="h1" gutterBottom>
            JuriConnect
          </Typography>
          <Typography variant="h5" color="text.secondary" gutterBottom>
            Plataforma de Intermediação Jurídica
          </Typography>
          <Typography variant="body1" color="text.secondary" sx={{ mt: 2, mb: 4 }}>
            Conecte-se com advogados certificados e acompanhe seus processos legais em tempo real
          </Typography>
          <Box sx={{ mt: 4 }}>
            <Button
              variant="contained"
              size="large"
              sx={{ mr: 2 }}
              onClick={() => navigate('/register/client')}
            >
              Registar como Cliente
            </Button>
            <Button
              variant="outlined"
              size="large"
              onClick={() => navigate('/register/lawyer')}
            >
              Registar como Advogado
            </Button>
          </Box>
        </Box>

        <Grid container spacing={4} sx={{ mb: 8 }}>
          <Grid item xs={12} md={3}>
            <Card>
              <CardContent>
                <People sx={{ fontSize: 40, color: 'primary.main', mb: 2 }} />
                <Typography variant="h4">{stats.totalLawyers}</Typography>
                <Typography color="text.secondary">Advogados</Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={3}>
            <Card>
              <CardContent>
                <People sx={{ fontSize: 40, color: 'primary.main', mb: 2 }} />
                <Typography variant="h4">{stats.totalClients}</Typography>
                <Typography color="text.secondary">Clientes</Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={3}>
            <Card>
              <CardContent>
                <TrendingUp sx={{ fontSize: 40, color: 'primary.main', mb: 2 }} />
                <Typography variant="h4">{stats.satisfactionRate.toFixed(1)}</Typography>
                <Typography color="text.secondary">Taxa de Satisfação</Typography>
              </CardContent>
            </Card>
          </Grid>
          <Grid item xs={12} md={3}>
            <Card>
              <CardContent>
                <Gavel sx={{ fontSize: 40, color: 'primary.main', mb: 2 }} />
                <Typography variant="h4">{legalAreas.length}</Typography>
                <Typography color="text.secondary">Áreas Jurídicas</Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Box sx={{ mb: 6 }}>
          <Typography variant="h4" gutterBottom align="center">
            Áreas de Atuação Jurídica
          </Typography>
          <Grid container spacing={2} sx={{ mt: 2 }}>
            {legalAreas.map((area, index) => (
              <Grid item xs={12} sm={6} md={4} key={index}>
                <Card>
                  <CardContent>
                    <Typography variant="h6">{area}</Typography>
                  </CardContent>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Box>
      </Container>
    </Box>
  );
};

export default LandingPage;

