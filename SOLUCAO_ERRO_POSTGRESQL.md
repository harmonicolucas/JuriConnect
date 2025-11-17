# 🔧 Solução: Erro de Autenticação PostgreSQL

## ❌ Erro Identificado

```
FATAL: password authentication failed for user "postgres"
```

O backend não consegue conectar ao PostgreSQL porque a **senha está incorreta** no arquivo `application.properties`.

---

## ✅ Solução Passo a Passo

### Opção 1: Descobrir/Configurar a Senha Correta

#### 1.1. Testar a Senha Atual

Abra o **pgAdmin** ou **psql** e tente conectar com a senha `buramo3K_`:

```bash
psql -U postgres
# Digite a senha quando solicitado
```

Se funcionar, a senha está correta. Se não funcionar, continue.

#### 1.2. Redefinir a Senha do PostgreSQL

**Método A: Via pgAdmin (Recomendado)**
1. Abra o **pgAdmin**
2. Clique com botão direito em **PostgreSQL** → **Properties**
3. Vá em **Connection** → **Password**
4. Digite a nova senha
5. Salve

**Método B: Via psql (Linha de Comando)**
```bash
# Conecte-se ao PostgreSQL (pode pedir senha)
psql -U postgres

# Dentro do psql, execute:
ALTER USER postgres WITH PASSWORD 'sua_nova_senha';

# Saia
\q
```

**Método C: Via Windows (se instalado como serviço)**
1. Abra **Serviços** do Windows (Win+R → `services.msc`)
2. Encontre **postgresql-x64-XX**
3. Clique com botão direito → **Properties**
4. Vá em **Log On** para ver o usuário
5. Use o **pgAdmin** para redefinir a senha

---

### Opção 2: Atualizar o application.properties

Após descobrir/configurar a senha correta:

1. **Abra o arquivo:**
   ```
   backend/src/main/resources/application.properties
   ```

2. **Atualize a linha da senha:**
   ```properties
   spring.datasource.password=SUA_SENHA_CORRETA_AQUI
   ```

3. **Salve o arquivo**

4. **Reinicie o backend**

---

### Opção 3: Usar Credenciais Diferentes

Se você usa um usuário diferente de `postgres`:

1. **Edite o `application.properties`:**
   ```properties
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

2. **Certifique-se de que o usuário tem permissões:**
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE juriconnect TO seu_usuario;
   ```

---

### Opção 4: Criar Novo Usuário (Recomendado para Produção)

1. **Conecte-se ao PostgreSQL:**
   ```bash
   psql -U postgres
   ```

2. **Crie um novo usuário:**
   ```sql
   CREATE USER juriconnect_user WITH PASSWORD 'senha_segura_123';
   ```

3. **Dê permissões:**
   ```sql
   GRANT ALL PRIVILEGES ON DATABASE juriconnect TO juriconnect_user;
   ```

4. **Atualize o `application.properties`:**
   ```properties
   spring.datasource.username=juriconnect_user
   spring.datasource.password=senha_segura_123
   ```

---

## 🔍 Verificação Rápida

### Teste de Conexão Manual

1. **Abra o terminal/CMD**

2. **Teste a conexão:**
   ```bash
   psql -U postgres -d juriconnect
   ```

3. **Se pedir senha e conectar:** A senha está correta, atualize o `application.properties`

4. **Se não conectar:** Siga a Opção 1.2 para redefinir a senha

---

## 📝 Exemplo de application.properties Correto

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/juriconnect
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

# File Upload Configuration
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# JWT Configuration
jwt.secret=juriconnect-secret-key-2024-mozambique-very-secure-key
jwt.expiration=86400000

# CORS Configuration
cors.allowed-origins=http://localhost:3000

# File Storage
file.upload-dir=./uploads
```

---

## ⚠️ Importante

1. **Certifique-se de que o PostgreSQL está rodando:**
   - Verifique nos **Serviços do Windows**
   - Ou execute: `pg_ctl status`

2. **Certifique-se de que o banco `juriconnect` existe:**
   ```sql
   CREATE DATABASE juriconnect;
   ```

3. **Após atualizar a senha, reinicie o backend**

---

## 🎯 Passos Rápidos (Resumo)

1. ✅ Descubra/configure a senha do PostgreSQL
2. ✅ Atualize `backend/src/main/resources/application.properties`
3. ✅ Certifique-se de que o banco `juriconnect` existe
4. ✅ Reinicie o backend
5. ✅ Verifique se conectou com sucesso

---

## 🐛 Se Ainda Não Funcionar

### Verificar se PostgreSQL está rodando:
```bash
# Windows
net start postgresql-x64-XX

# Ou verificar nos Serviços do Windows
```

### Verificar porta:
```properties
# Se PostgreSQL estiver em outra porta, atualize:
spring.datasource.url=jdbc:postgresql://localhost:5433/juriconnect
```

### Verificar host:
```properties
# Se PostgreSQL estiver em outro host:
spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/juriconnect
```

---

**Após corrigir, o backend deve iniciar normalmente!** ✅

