import React, { useState, useEffect } from 'react';
import {
  Box,
  List,
  ListItem,
  ListItemText,
  Button,
  Typography,
} from '@mui/material';
import { CloudUpload, Download } from '@mui/icons-material';
import { useDropzone } from 'react-dropzone';
import api from '../services/api';

const DocumentList = ({ caseId }) => {
  const [documents, setDocuments] = useState([]);

  useEffect(() => {
    loadDocuments();
  }, [caseId]);

  const loadDocuments = async () => {
    try {
      const { data } = await api.get(`/documents/case/${caseId}`);
      setDocuments(data);
    } catch (err) {
      console.error('Erro ao carregar documentos:', err);
    }
  };

  const { getRootProps, getInputProps } = useDropzone({
    accept: {
      'application/pdf': ['.pdf'],
      'application/msword': ['.doc'],
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document': ['.docx'],
      'image/jpeg': ['.jpg', '.jpeg'],
      'image/png': ['.png'],
    },
    onDrop: async (files) => {
      for (const file of files) {
        const formData = new FormData();
        formData.append('file', file);

        try {
          await api.post(`/documents/case/${caseId}`, formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
          });
          loadDocuments();
        } catch (err) {
          console.error('Erro no upload:', err);
        }
      }
    },
  });

  const handleDownload = (filePath) => {
    window.open(`http://localhost:8080/${filePath}`, '_blank');
  };

  return (
    <Box>

      {/* Área de upload */}
      <Box
        {...getRootProps()}
        sx={{
          border: '2px dashed #ccc',
          borderRadius: 2,
          p: 3,
          textAlign: 'center',
          cursor: 'pointer',
          mb: 3,
          '&:hover': { borderColor: 'primary.main' },
        }}
      >
        <input {...getInputProps()} />
        <CloudUpload sx={{ fontSize: 48, color: 'text.secondary', mb: 1 }} />
        <Typography>Clique ou arraste arquivos para enviar</Typography>
      </Box>

      {/* Lista de Documentos */}
      <List>
        {documents.map((doc) => {
          const sizeKB = (doc.fileSize / 1024).toFixed(2);

          return (
            <ListItem
              key={doc.id}
              secondaryAction={
                <Button
                  startIcon={<Download />}
                  onClick={() => handleDownload(doc.filePath)}
                >
                  Download
                </Button>
              }
            >
              <ListItemText
                primary={doc.fileName}
                secondary={`${doc.fileType} • ${sizeKB} KB • Enviado por ${doc.uploadedByName}`}
              />
            </ListItem>
          );
        })}
      </List>
    </Box>
  );
};

export default DocumentList;
