# ⚡ Quick Start - JuriConnect

Guia rápido para iniciar o sistema em 5 minutos.

## 🚀 Início Rápido

### 1. Banco de Dados
```sql
CREATE DATABASE juriconnect;
```

### 2. Backend (Terminal 1)
```bash
cd backend
mvn spring-boot:run
```
✅ Backend rodando em: `http://localhost:8080`

### 3. Frontend (Terminal 2 - NOVO TERMINAL)
```bash
cd frontend
npm install
npm start
```
✅ Frontend abrindo em: `http://localhost:3000`

## 🧪 Teste Rápido

1. **Registre um Cliente:**
   - Email: `teste@cliente.com`
   - Senha: `senha123`

2. **Registre um Advogado:**
   - Email: `teste@advogado.com`
   - Senha: `senha123`
   - OAM: `OAM12345`
   - Selecione áreas jurídicas
   - Faça upload de documento OAM

3. **Cliente cria caso:**
   - Selecione área jurídica
   - Selecione o advogado
   - Crie o caso

4. **Teste o chat:**
   - Cliente envia mensagem
   - Advogado responde (em outra aba)
   - Veja mensagens aparecerem em tempo real

5. **Teste documentos:**
   - Cliente faz upload
   - Advogado vê o documento

6. **Teste timeline:**
   - Advogado atualiza status
   - Cliente vê na timeline

## ✅ Pronto!

Sistema funcionando! 🎉

