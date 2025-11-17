# 🔐 Resolver Problema de Senha PostgreSQL - GUIA RÁPIDO

## ⚠️ O Problema

O erro é sempre o mesmo:
```
FATAL: password authentication failed for user "postgres"
```

A senha no arquivo `application.properties` está **incorreta**.

---

## ✅ SOLUÇÃO RÁPIDA (Escolha uma opção)

### 🎯 OPÇÃO 1: Descobrir a Senha Atual (Mais Rápido)

#### Passo 1: Abrir pgAdmin
1. Abra o **pgAdmin 4** (deve estar instalado com o PostgreSQL)
2. Clique em **Servers** → **PostgreSQL** → **Properties**
3. Vá na aba **Connection**
4. **Veja a senha** que está configurada lá (ou tente a senha que você definiu na instalação)

#### Passo 2: Atualizar application.properties
1. Abra: `E:\XXX\backend\src\main\resources\application.properties`
2. Encontre a linha 7:
   ```properties
   spring.datasource.password=buramo3K_
   ```
3. **Substitua pela senha correta:**
   ```properties
   spring.datasource.password=SUA_SENHA_CORRETA
   ```
4. **Salve** (Ctrl+S)

#### Passo 3: Reiniciar o Backend
- Pare o backend (Ctrl+C)
- Inicie novamente

---

### 🎯 OPÇÃO 2: Redefinir a Senha do PostgreSQL

#### Via pgAdmin:
1. Abra **pgAdmin**
2. Conecte-se ao servidor (pode pedir senha - use a que você sabe)
3. Clique com botão direito em **PostgreSQL** → **Properties**
4. Vá em **Connection** → **Password**
5. Digite uma **nova senha** (ex: `postgres123`)
6. Salve

#### Via Linha de Comando:
```bash
# 1. Conecte-se (pode pedir senha)
psql -U postgres

# 2. Dentro do psql, execute:
ALTER USER postgres WITH PASSWORD 'nova_senha_123';

# 3. Saia
\q
```

#### Depois:
1. Atualize o `application.properties` com a nova senha
2. Reinicie o backend

---

### 🎯 OPÇÃO 3: Criar Novo Usuário (Recomendado)

#### Passo 1: Conectar ao PostgreSQL
```bash
psql -U postgres
# Digite a senha quando solicitado
```

#### Passo 2: Criar Usuário e Banco
```sql
-- Criar banco (se não existir)
CREATE DATABASE juriconnect;

-- Criar novo usuário
CREATE USER juriconnect_user WITH PASSWORD 'juriconnect123';

-- Dar permissões
GRANT ALL PRIVILEGES ON DATABASE juriconnect TO juriconnect_user;

-- Conectar ao banco
\c juriconnect

-- Dar permissões no schema
GRANT ALL ON SCHEMA public TO juriconnect_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO juriconnect_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO juriconnect_user;

-- Sair
\q
```

#### Passo 3: Atualizar application.properties
```properties
spring.datasource.username=juriconnect_user
spring.datasource.password=juriconnect123
```

---

## 🔍 Como Descobrir a Senha do PostgreSQL

### Método 1: Verificar na Instalação
- Lembre-se da senha que você definiu quando instalou o PostgreSQL
- Geralmente é pedida durante a instalação

### Método 2: Verificar no pgAdmin
- Abra pgAdmin
- Tente conectar - pode mostrar a senha salva

### Método 3: Verificar Arquivo de Configuração
- Procure por arquivos `.pgpass` no Windows
- Ou verifique as configurações do serviço PostgreSQL

### Método 4: Redefinir (Se não souber)
- Siga a **OPÇÃO 2** acima para redefinir

---

## 📝 Checklist Rápido

- [ ] Descobri/defini a senha correta do PostgreSQL
- [ ] Atualizei `application.properties` com a senha correta
- [ ] Verifiquei que o banco `juriconnect` existe
- [ ] Reiniciei o backend
- [ ] O backend iniciou sem erros de autenticação

---

## 🎯 Comandos Rápidos (Copie e Cole)

### Testar Conexão:
```bash
psql -U postgres -d juriconnect
```

### Redefinir Senha:
```sql
ALTER USER postgres WITH PASSWORD 'nova_senha';
```

### Criar Banco:
```sql
CREATE DATABASE juriconnect;
```

---

## ⚠️ IMPORTANTE

1. **A senha no `application.properties` DEVE ser exatamente igual à senha do PostgreSQL**
2. **Não use espaços ou caracteres especiais problemáticos**
3. **Após alterar, SEMPRE reinicie o backend**

---

## 🐛 Se Ainda Não Funcionar

### Verificar se PostgreSQL está rodando:
```bash
# Windows - Verificar serviços
services.msc
# Procure por "postgresql" e verifique se está "Running"
```

### Verificar porta:
- Padrão: `5432`
- Se diferente, atualize no `application.properties`:
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5433/juriconnect
  ```

### Testar conexão manual:
```bash
psql -h localhost -p 5432 -U postgres -d juriconnect
```

---

**Após corrigir a senha, o backend deve iniciar normalmente!** ✅

