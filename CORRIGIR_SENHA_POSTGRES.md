# 🔐 Como Corrigir a Senha do PostgreSQL

## ⚠️ Problema Atual

A senha no `application.properties` está como `buramo3K_`, mas o PostgreSQL está rejeitando.

---

## ✅ Solução Rápida (3 Passos)

### PASSO 1: Descobrir/Redefinir a Senha

**Opção A: Via pgAdmin (Mais Fácil)**
1. Abra o **pgAdmin 4**
2. Clique com botão direito em **Servers** → **PostgreSQL** → **Properties**
3. Vá na aba **Connection**
4. Veja/altere a senha
5. **Anote a senha correta**

**Opção B: Via Linha de Comando**
```bash
# 1. Tente conectar (pode pedir senha)
psql -U postgres

# 2. Se conectar, redefina a senha:
ALTER USER postgres WITH PASSWORD 'minha_nova_senha_123';

# 3. Saia
\q
```

**Opção C: Se não souber a senha**
1. Abra o **pgAdmin**
2. Se não conseguir conectar, pode precisar redefinir via arquivo `pg_hba.conf`
3. Ou reinstale o PostgreSQL com uma senha conhecida

---

### PASSO 2: Atualizar o application.properties

1. **Abra o arquivo:**
   ```
   E:\XXX\backend\src\main\resources\application.properties
   ```

2. **Encontre a linha:**
   ```properties
   spring.datasource.password=buramo3K_
   ```

3. **Substitua pela senha correta:**
   ```properties
   spring.datasource.password=SUA_SENHA_CORRETA_AQUI
   ```

4. **Salve o arquivo** (Ctrl+S)

---

### PASSO 3: Verificar se o Banco Existe

```bash
# Conecte ao PostgreSQL
psql -U postgres

# Verifique se o banco existe
\l

# Se não existir, crie:
CREATE DATABASE juriconnect;

# Saia
\q
```

---

### PASSO 4: Reiniciar o Backend

1. **Pare o backend** (Ctrl+C no terminal)
2. **Inicie novamente:**
   ```bash
   cd E:\XXX\backend
   mvn spring-boot:run
   ```

3. **Verifique se conectou:**
   - Deve aparecer: `Started JuriConnectApplication`
   - **NÃO deve aparecer** o erro de autenticação

---

## 🎯 Alternativa: Criar Novo Usuário

Se preferir criar um usuário específico:

### 1. Execute o Script SQL:

```bash
psql -U postgres -f backend/src/main/resources/db/setup-user.sql
```

**OU** copie e cole no psql:

```sql
CREATE DATABASE juriconnect;
CREATE USER juriconnect_user WITH PASSWORD 'juriconnect123';
GRANT ALL PRIVILEGES ON DATABASE juriconnect TO juriconnect_user;
```

### 2. Atualize o application.properties:

```properties
spring.datasource.username=juriconnect_user
spring.datasource.password=juriconnect123
```

---

## 🔍 Verificação Final

Após corrigir, você deve ver no log do backend:

```
HikariPool-1 - Starting...
HikariPool-1 - Start completed.
Started JuriConnectApplication in X.XXX seconds
```

**NÃO deve aparecer:**
```
FATAL: password authentication failed
```

---

## 📝 Resumo dos Comandos

```bash
# 1. Testar conexão
psql -U postgres

# 2. Se conectar, redefinir senha (dentro do psql)
ALTER USER postgres WITH PASSWORD 'nova_senha';

# 3. Criar banco (se não existir)
CREATE DATABASE juriconnect;

# 4. Atualizar application.properties com a nova senha

# 5. Reiniciar backend
```

---

**Após seguir estes passos, o backend deve iniciar corretamente!** ✅

