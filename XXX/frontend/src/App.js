import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { AuthProvider } from './context/AuthContext';
import PrivateRoute from './components/PrivateRoute';

import LandingPage from './pages/LandingPage';
import Login from './pages/Login';
import RegisterClient from './pages/RegisterClient';
import RegisterLawyer from './pages/RegisterLawyer';
import ClientDashboard from './pages/ClientDashboard';
import LawyerDashboard from './pages/LawyerDashboard';
import CreateCase from './pages/CreateCase';
import CaseDetails from './pages/CaseDetails';
import Chat from './components/Chat';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <Router>
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register/client" element={<RegisterClient />} />
            <Route path="/register/lawyer" element={<RegisterLawyer />} />
            <Route
              path="/dashboard/client"
              element={
                <PrivateRoute>
                  <ClientDashboard />
                </PrivateRoute>
              }
            />
            <Route
              path="/dashboard/lawyer"
              element={
                <PrivateRoute>
                  <LawyerDashboard />
                </PrivateRoute>
              }
            />
            <Route
              path="/case/new"
              element={
                <PrivateRoute>
                  <CreateCase />
                </PrivateRoute>
              }
            />
            <Route
              path="/case/:id"
              element={
                <PrivateRoute>
                  <CaseDetails />
                </PrivateRoute>
              }
            />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </Router>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;

