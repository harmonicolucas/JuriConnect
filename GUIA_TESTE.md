# 🧪 Guia Completo de Teste - JuriConnect

Este guia fornece instruções passo a passo para executar e testar todas as funcionalidades do sistema JuriConnect.

## 📋 Pré-requisitos

Certifique-se de ter instalado:
- ✅ Java 17 ou superior
- ✅ Maven 3.6+
- ✅ PostgreSQL 12+ (rodando)
- ✅ Node.js 16+ e npm
- ✅ Git (opcional)

---

## 🚀 PASSO 1: Configuração do Banco de Dados

### 1.1. Iniciar PostgreSQL
Certifique-se de que o PostgreSQL está rodando no seu sistema.

### 1.2. Criar o Banco de Dados

Abra o terminal/command prompt e execute:

```sql
-- Conecte-se ao PostgreSQL
psql -U postgres

-- Crie o banco de dados
CREATE DATABASE juriconnect;

-- Verifique se foi criado
\l

-- Saia do psql
\q
```

**OU** use uma ferramenta gráfica como pgAdmin ou DBeaver para criar o banco `juriconnect`.

### 1.3. Verificar Credenciais
O arquivo `application.properties` já está configurado com:
- Usuário: `postgres`
- Senha: `buramo3K_`
- Banco: `juriconnect`

Se suas credenciais forem diferentes, edite o arquivo `backend/src/main/resources/application.properties`.

---

## 🔧 PASSO 2: Executar o Backend

### 2.1. Abrir Terminal no Diretório do Backend

```bash
cd backend
```

### 2.2. Compilar o Projeto

```bash
mvn clean install
```

**Aguarde a compilação terminar** (pode levar alguns minutos na primeira vez).

### 2.3. Executar o Backend

```bash
mvn spring-boot:run
```

**Você verá mensagens como:**
```
Started JuriConnectApplication in X.XXX seconds
```

**O backend estará rodando em:** `http://localhost:8080`

**⚠️ IMPORTANTE:** Mantenha este terminal aberto enquanto testa o sistema!

---

## 🎨 PASSO 3: Executar o Frontend

### 3.1. Abrir NOVO Terminal (deixe o backend rodando)

Abra um **novo terminal** e navegue para o frontend:

```bash
cd frontend
```

### 3.2. Instalar Dependências

```bash
npm install
```

**Aguarde a instalação terminar** (pode levar alguns minutos na primeira vez).

### 3.3. Executar o Frontend

```bash
npm start
```

**O navegador abrirá automaticamente em:** `http://localhost:3000`

**⚠️ IMPORTANTE:** Mantenha este terminal aberto também!

---

## ✅ PASSO 4: Verificar se Tudo Está Funcionando

### 4.1. Verificar Backend
Abra o navegador e acesse:
```
http://localhost:8080/api/public/stats
```

**Deve retornar JSON com estatísticas:**
```json
{
  "totalLawyers": 0,
  "totalClients": 0,
  "satisfactionRate": 4.5,
  "legalAreas": [...]
}
```

### 4.2. Verificar Frontend
O frontend deve abrir automaticamente em `http://localhost:3000` mostrando a **Landing Page**.

---

## 🧪 PASSO 5: Teste Completo das Funcionalidades

### 📝 TESTE 1: Registro de Cliente

1. **Na Landing Page**, clique em **"Registar como Cliente"**
2. **Preencha o formulário:**
   - Nome Completo: `João Silva`
   - Email: `joao@teste.com`
   - Contacto: `+258 84 123 456`
   - Palavra-passe: `senha123`
   - Confirmar Palavra-passe: `senha123`
   - ✅ Marque "Aceito os Termos e Políticas"
3. Clique em **"Registar"**
4. **Resultado esperado:** Redirecionamento automático para o Dashboard do Cliente

---

### 📝 TESTE 2: Registro de Advogado

1. **Faça logout** (ícone no canto superior direito)
2. Volte para a **Landing Page**
3. Clique em **"Registar como Advogado"**
4. **Preencha o formulário:**
   - Nome Completo: `Dr. Maria Santos`
   - Email: `maria@teste.com`
   - Contacto: `+258 85 987 654`
   - Número da Carteira OAM: `OAM12345`
   - Palavra-passe: `senha123`
   - Confirmar Palavra-passe: `senha123`
   - **Áreas de Atuação:** Selecione pelo menos uma (ex: `PENAL`, `CIVIL`)
   - **Upload Declaração OAM:** Clique e selecione um arquivo (PDF, DOCX, JPEG ou PNG)
   - ✅ Marque "Aceito os Termos e Políticas"
5. Clique em **"Registar"**
6. **Resultado esperado:** Redirecionamento automático para o Dashboard do Advogado

---

### 📝 TESTE 3: Login

1. **Faça logout** do advogado
2. Clique em **"Login"** na Landing Page
3. **Teste Login do Cliente:**
   - Email: `joao@teste.com`
   - Palavra-passe: `senha123`
   - Clique em **"Entrar"**
   - **Resultado esperado:** Dashboard do Cliente

4. **Faça logout** e teste **Login do Advogado:**
   - Email: `maria@teste.com`
   - Palavra-passe: `senha123`
   - Clique em **"Entrar"**
   - **Resultado esperado:** Dashboard do Advogado

---

### 📝 TESTE 4: Criar Novo Caso (Cliente)

1. **Faça login como Cliente** (`joao@teste.com`)
2. No Dashboard, clique em **"Novo Caso"**
3. **Preencha:**
   - **Área Jurídica:** Selecione uma área (ex: `Direito Penal`)
   - **Upload de Documentos (Opcional):** 
     - Clique na área de upload
     - Selecione um arquivo PDF, DOCX, JPEG ou PNG
   - **Selecione um Advogado:**
     - Após selecionar a área, aparecerão advogados disponíveis
     - Clique no card do advogado desejado (ex: `Dr. Maria Santos`)
4. Clique em **"Criar Caso e Iniciar Chat"**
5. **Resultado esperado:** Redirecionamento para a página de detalhes do caso com o chat aberto

---

### 📝 TESTE 5: Chat em Tempo Real

1. **No caso criado**, você já está na aba **"Chat"**
2. **Envie uma mensagem:**
   - Digite: `Olá, preciso de ajuda com meu caso`
   - Pressione Enter ou clique em **"Enviar"**
3. **Para testar o chat em tempo real:**
   - **Abra uma nova aba/janela** do navegador
   - Faça **login como Advogado** (`maria@teste.com`)
   - No Dashboard do Advogado, clique no ícone de **Chat** do caso
   - **Envie uma resposta:** `Olá! Como posso ajudá-lo?`
4. **Volte para a aba do Cliente** e verifique se a mensagem apareceu automaticamente
5. **Teste o contrário:** Envie uma mensagem do Cliente e veja aparecer no Advogado

**✅ Funcionalidade testada:** Chat em tempo real via WebSocket

---

### 📝 TESTE 6: Upload e Visualização de Documentos

1. **Na página do caso**, clique na aba **"Documentos"**
2. **Upload de documento:**
   - Clique na área de upload ou arraste um arquivo
   - Selecione um arquivo (PDF, DOCX, JPEG, PNG)
   - O arquivo será enviado automaticamente
3. **Verifique:**
   - O documento aparece na lista
   - Mostra nome, tipo, tamanho e quem enviou
4. **Teste do lado do Advogado:**
   - Faça login como Advogado
   - Abra o caso
   - Vá na aba **"Documentos"**
   - **Verifique:** Os documentos enviados pelo cliente aparecem
   - **Envie um documento do advogado** também

**✅ Funcionalidade testada:** Upload e compartilhamento de documentos

---

### 📝 TESTE 7: Atualização de Status do Processo (Advogado)

1. **Faça login como Advogado** (`maria@teste.com`)
2. No **Dashboard**, localize o caso na tabela
3. **Na coluna "Status"**, clique no dropdown
4. **Selecione um novo status:**
   - Ex: `DOCUMENTACAO_RECEBIDA`
   - Ex: `PETICAO_PROTOCOLADA`
   - Ex: `AUDIENCIA_MARCADA`
5. **O status será atualizado automaticamente**
6. **Teste do lado do Cliente:**
   - Faça login como Cliente
   - Abra o caso
   - Clique na aba **"Acompanhamento"**
   - **Verifique:** A timeline mostra o status atualizado

**✅ Funcionalidade testada:** Atualização de status e timeline visual

---

### 📝 TESTE 8: Timeline de Acompanhamento

1. **Como Cliente**, abra um caso
2. Clique na aba **"Acompanhamento"**
3. **Verifique:**
   - A timeline mostra todas as 9 etapas
   - A etapa atual está destacada
   - As etapas anteriores estão marcadas como concluídas
4. **Peça ao Advogado** para atualizar o status várias vezes
5. **Atualize a página** e veja a timeline refletir as mudanças

**✅ Funcionalidade testada:** Timeline visual de acompanhamento

---

### 📝 TESTE 9: Busca de Advogados por Área

1. **Como Cliente**, clique em **"Novo Caso"**
2. **Selecione diferentes áreas jurídicas:**
   - `Direito Penal`
   - `Direito Civil`
   - `Direito Comercial`
3. **Verifique:**
   - A lista de advogados muda conforme a área selecionada
   - Apenas advogados com a especialização aparecem
   - Mostra informações: nome, especializações, casos concluídos, avaliação

**✅ Funcionalidade testada:** Busca filtrada de advogados

---

### 📝 TESTE 10: Dashboard - Estatísticas

#### Dashboard do Cliente:
1. **Login como Cliente**
2. **Verifique no topo:**
   - **Consultas Realizadas:** Número de casos criados
   - **Processos em Andamento:** Casos não encerrados
   - **Satisfação Média:** Média das avaliações

#### Dashboard do Advogado:
1. **Login como Advogado**
2. **Verifique no topo:**
   - **Clientes Atendidos:** Total de casos
   - **Clientes em Atendimento:** Casos ativos
   - **Avaliação Média:** Média das avaliações recebidas

**✅ Funcionalidade testada:** Estatísticas nos dashboards

---

### 📝 TESTE 11: Listagem de Casos

#### Como Cliente:
1. No Dashboard, veja a **tabela "Meus Casos"**
2. **Verifique:**
   - Todos os casos criados aparecem
   - Mostra: ID, Advogado, Área, Status, Data
   - Botão para abrir o chat

#### Como Advogado:
1. No Dashboard, veja a **tabela "Meus Casos"**
2. **Verifique:**
   - Todos os casos atribuídos aparecem
   - Mostra: ID, Cliente, Área, Status (editável), Data
   - Botão para abrir o chat

**✅ Funcionalidade testada:** Listagem e gestão de casos

---

### 📝 TESTE 12: Avaliação de Serviço (Opcional - quando caso estiver encerrado)

1. **Como Advogado**, atualize o status do caso para **"ENCERRADO"**
2. **Como Cliente**, abra o caso encerrado
3. **Deve aparecer opção para avaliar:**
   - Rating de 1 a 5 estrelas
   - Campo de comentário
4. **Envie a avaliação**
5. **Como Advogado**, verifique se a avaliação média foi atualizada no Dashboard

**✅ Funcionalidade testada:** Sistema de avaliações

---

## 🔍 Verificação de Funcionalidades Core

### ✅ Checklist Completo:

- [ ] Landing Page com estatísticas
- [ ] Registro de Cliente
- [ ] Registro de Advogado (com upload OAM)
- [ ] Login (Cliente e Advogado)
- [ ] Dashboard do Cliente
- [ ] Dashboard do Advogado
- [ ] Criação de novo caso
- [ ] Busca de advogados por área jurídica
- [ ] Upload de documentos
- [ ] Chat em tempo real (WebSocket)
- [ ] Atualização de status do processo
- [ ] Timeline de acompanhamento
- [ ] Listagem de casos
- [ ] Estatísticas nos dashboards
- [ ] Logout funcional

---

## 🐛 Solução de Problemas Comuns

### Problema: Backend não inicia
**Solução:**
- Verifique se o PostgreSQL está rodando
- Verifique as credenciais no `application.properties`
- Verifique se a porta 8080 está livre

### Problema: Frontend não conecta ao backend
**Solução:**
- Verifique se o backend está rodando em `http://localhost:8080`
- Verifique o console do navegador (F12) para erros
- Verifique CORS no `SecurityConfig.java`

### Problema: Chat não funciona
**Solução:**
- Verifique se o WebSocket está configurado corretamente
- Verifique o console do navegador para erros de conexão
- Certifique-se de que ambos (cliente e advogado) estão logados

### Problema: Upload de arquivos falha
**Solução:**
- Verifique se a pasta `backend/uploads` existe
- Verifique o tamanho do arquivo (máx. 10MB)
- Verifique o formato do arquivo (PDF, DOCX, JPEG, PNG)

### Problema: Banco de dados não conecta
**Solução:**
- Verifique se o PostgreSQL está rodando
- Verifique usuário e senha
- Verifique se o banco `juriconnect` foi criado

---

## 📊 Dados de Teste Sugeridos

### Cliente 1:
- Email: `joao@teste.com`
- Senha: `senha123`

### Cliente 2:
- Email: `ana@teste.com`
- Senha: `senha123`

### Advogado 1:
- Email: `maria@teste.com`
- Senha: `senha123`
- OAM: `OAM12345`
- Áreas: `PENAL`, `CIVIL`

### Advogado 2:
- Email: `carlos@teste.com`
- Senha: `senha123`
- OAM: `OAM67890`
- Áreas: `COMERCIAL`, `TRABALHISTA`

---

## 🎯 Teste de Fluxo Completo

1. **Registre 2 clientes e 2 advogados**
2. **Cliente 1 cria um caso** com Advogado 1
3. **Cliente 1 envia documentos** no caso
4. **Advogado 1 recebe notificação** e abre o caso
5. **Advogado 1 atualiza status** para "Documentação Recebida"
6. **Cliente 1 e Advogado 1 conversam** via chat
7. **Advogado 1 atualiza status** várias vezes
8. **Cliente 1 acompanha** pela timeline
9. **Advogado 1 encerra o caso**
10. **Cliente 1 avalia** o serviço

**✅ Fluxo completo testado!**

---

## 📝 Notas Finais

- **Mantenha ambos os terminais abertos** (backend e frontend)
- **Use dois navegadores ou abas anônimas** para testar cliente e advogado simultaneamente
- **Verifique o console do navegador (F12)** para ver erros ou logs
- **Verifique os logs do backend** no terminal para debug

---

**Boa sorte com os testes! 🚀**

Se encontrar algum problema, verifique os logs e o console do navegador para mais detalhes.

