import React from 'react';
import {
  Box,
  Stepper,
  Step,
  StepLabel,
  StepContent,
  Typography,
  Paper,
} from '@mui/material';

const CASE_STATUSES = [
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

const STATUS_LABELS = {
  PROCESSO_INICIADO: 'Processo Iniciado',
  DOCUMENTACAO_RECEBIDA: 'Documentação Recebida',
  PETICAO_PROTOCOLADA: 'Petição Protocolada',
  AUDIENCIA_MARCADA: 'Audiência Marcada',
  JULGAMENTO_REALIZADO: 'Julgamento Realizado',
  SENTENCA_PROFERIDA: 'Sentença Proferida',
  ACORDO_EXTRAJUDICIAL: 'Acordo Extrajudicial',
  EXECUCAO_SENTENCA: 'Execução de Sentença',
  ENCERRADO: 'Encerrado',
};

const CaseTimeline = ({ caseData }) => {
  const currentStatusIndex = CASE_STATUSES.indexOf(caseData.status);

  return (
    <Box>
      <Typography variant="h6" gutterBottom>
        Acompanhamento do Processo
      </Typography>
      <Paper elevation={2} sx={{ p: 3, mt: 2 }}>
        <Stepper activeStep={currentStatusIndex} orientation="vertical">
          {CASE_STATUSES.map((status, index) => (
            <Step key={status} completed={index <= currentStatusIndex}>
              <StepLabel>{STATUS_LABELS[status]}</StepLabel>
              {index === currentStatusIndex && (
                <StepContent>
                  <Typography variant="body2" color="text.secondary">
                    Status atual do processo
                  </Typography>
                </StepContent>
              )}
            </Step>
          ))}
        </Stepper>
      </Paper>
    </Box>
  );
};

export default CaseTimeline;

