# JuriConnect - Plataforma de Intermediação Jurídica

**JuriConnect** é uma plataforma web de intermediação jurídica desenvolvida para o contexto moçambicano, cujo objetivo é conectar cidadãos e advogados de forma segura, transparente e eficiente.

## 🎯 Funcionalidades Principais

### Para Clientes
- Registro e autenticação
- Busca de advogados por área jurídica
- Criação de casos legais
- Upload de documentos
- Chat em tempo real com advogados
- Acompanhamento visual das etapas processuais
- Avaliação de serviços

### Para Advogados
- Registro com validação OAM
- Dashboard com estatísticas
- Gestão de casos
- Atualização de status de processos
- Chat em tempo real com clientes
- Visualização de documentos

## 🛠️ Tecnologias Utilizadas

### Backend
- **Spring Boot 3.2.0** - Framework Java
- **PostgreSQL** - Banco de dados
- **Spring Security** - Autenticação e autorização
- **JWT** - Tokens de autenticação
- **WebSocket** - Chat em tempo real
- **Maven** - Gerenciamento de dependências

### Frontend
- **React 18** - Biblioteca JavaScript
- **Material-UI** - Componentes de interface
- **React Router** - Roteamento
- **Axios** - Cliente HTTP
- **SockJS + STOMP** - WebSocket para chat

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL 12+
- Node.js 16+ e npm
- Git

## 🚀 Instalação e Configuração

### 1. Clone o repositório

```bash
git clone <repository-url>
cd juriconnect
```

### 2. Configuração do Banco de Dados

Crie um banco de dados PostgreSQL:

```sql
CREATE DATABASE juriconnect;
```

### 3. Configuração do Backend

Edite o arquivo `backend/src/main/resources/application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/juriconnect
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 4. Executar o Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

O backend estará disponível em `http://localhost:8080`

### 5. Executar o Frontend

```bash
cd frontend
npm install
npm start
```

O frontend estará disponível em `http://localhost:3000`

## 📁 Estrutura do Projeto

```
juriconnect/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/juriconnect/
│   │   │   │   ├── controller/     # Controllers REST
│   │   │   │   ├── service/         # Lógica de negócio
│   │   │   │   ├── repository/      # Repositórios JPA
│   │   │   │   ├── model/           # Entidades
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── security/        # Configuração de segurança
│   │   │   │   └── config/          # Configurações
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── pages/                   # Páginas principais
│   │   ├── components/               # Componentes reutilizáveis
│   │   ├── context/                  # Context API
│   │   ├── services/                 # Serviços API
│   │   └── App.js
│   └── package.json
└── README.md
```

## 🔐 Áreas Jurídicas Suportadas

1. Direito Penal
2. Direito Comercial
3. Direito Civil
4. Direito Trabalhista
5. Direito Administrativo
6. Direito Imobiliário
7. Direito Fiscal
8. Direitos Humanos
9. Direito Marítimo
10. Direito Ambiental

## 📊 Status de Processos

1. Processo Iniciado
2. Documentação Recebida
3. Petição Protocolada
4. Audiência Marcada
5. Julgamento Realizado
6. Sentença Proferida
7. Acordo Extrajudicial
8. Execução de Sentença
9. Encerrado

## 🔌 API Endpoints Principais

### Autenticação
- `POST /api/auth/register/client` - Registro de cliente
- `POST /api/auth/register/lawyer` - Registro de advogado
- `POST /api/auth/login` - Login

### Casos
- `POST /api/cases` - Criar novo caso
- `GET /api/cases/client` - Listar casos do cliente
- `GET /api/cases/lawyer` - Listar casos do advogado
- `GET /api/cases/{id}` - Detalhes do caso
- `PUT /api/cases/{id}/status` - Atualizar status

### Advogados
- `GET /api/lawyers` - Listar todos os advogados
- `GET /api/lawyers/available?legalArea={area}` - Advogados por área
- `GET /api/lawyers/{id}` - Detalhes do advogado

### Mensagens
- `POST /api/messages/case/{caseId}` - Enviar mensagem
- `GET /api/messages/case/{caseId}` - Listar mensagens

### Documentos
- `POST /api/documents/case/{caseId}` - Upload de documento
- `GET /api/documents/case/{caseId}` - Listar documentos

## 🧪 Testes

Para executar os testes do backend:

```bash
cd backend
mvn test
```

## 📝 Licença

Este projeto é desenvolvido para fins educacionais.

## 👥 Contribuição

Contribuições são bem-vindas! Por favor, abra uma issue ou pull request.

## 📧 Contato

Para mais informações, entre em contato através do repositório.

---

**Desenvolvido para o contexto moçambicano 🇲🇿**

