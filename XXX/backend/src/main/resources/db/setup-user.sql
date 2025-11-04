-- Script para criar usuário e banco de dados do JuriConnect
-- Execute este script como superusuário (postgres)

-- 1. Criar o banco de dados (se ainda não existir)
CREATE DATABASE juriconnect;

-- 2. Criar um usuário específico para o JuriConnect (opcional, mas recomendado)
CREATE USER juriconnect_user WITH PASSWORD 'juriconnect123';

-- 3. Dar todas as permissões ao usuário
GRANT ALL PRIVILEGES ON DATABASE juriconnect TO juriconnect_user;

-- 4. Conectar ao banco e dar permissões no schema
\c juriconnect
GRANT ALL ON SCHEMA public TO juriconnect_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO juriconnect_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO juriconnect_user;

-- Se preferir usar o usuário postgres, apenas certifique-se de que o banco existe:
-- CREATE DATABASE juriconnect;

