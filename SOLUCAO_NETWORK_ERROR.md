# 🔧 Solução: Network Error ao Registrar Advogado

## ⚠️ Problema

Ao tentar registrar advogado, aparece: **"Network Error"** ou **"Erro de rede"**

---

## ✅ Correções Implementadas

### 1. Frontend (Axios)
- ✅ Remoção automática de Content-Type para FormData
- ✅ Timeout de 30 segundos
- ✅ Tratamento melhorado de erros de rede
- ✅ Mensagens de erro mais específicas

### 2. Backend
- ✅ CORS configurado corretamente
- ✅ Endpoint `/api/auth/**` permitido sem autenticação

---

## 🔍 Verificações IMEDIATAS

### 1. Backend está rodando? (MAIS IMPORTANTE)

**Teste no navegador:**
```
http://localhost:8080/api/public/stats
```

**Se retornar JSON:** ✅ Backend está rodando  
**Se der erro 404 ou não conectar:** ❌ Backend NÃO está rodando

**Solução:**
```bash
cd E:\XXX\backend
mvn spring-boot:run
```

**Aguarde ver:**
```
Started JuriConnectApplication in X.XXX seconds
```

### 2. Porta 8080 está livre?

**Verifique se outra aplicação está usando a porta 8080:**
```bash
# Windows PowerShell
netstat -ano | findstr :8080
```

**Se houver outro processo:**
- Pare o processo ou mude a porta no `application.properties`

### 3. CORS está configurado?

✅ Já está configurado no `SecurityConfig.java`  
✅ Permite `http://localhost:3000`

---

## 🐛 Debug Passo a Passo

### Passo 1: Verificar Backend

**No navegador, abra:**
```
http://localhost:8080/api/public/stats
```

**Resultado esperado:**
```json
{
  "totalLawyers": 0,
  "totalClients": 0,
  ...
}
```

**Se não funcionar:**
- Backend não está rodando
- Inicie o backend primeiro

### Passo 2: Verificar Console do Navegador

1. **Abra o Console** (F12 → Console)
2. **Tente registrar novamente**
3. **Veja as mensagens de erro:**
   - `Erro ao registar advogado:`
   - `Código do erro:`
   - `Mensagem:`
   - `Status:`

### Passo 3: Verificar Network Tab

1. **Abra o DevTools** (F12)
2. **Vá na aba Network**
3. **Tente registrar novamente**
4. **Procure por:** `register/lawyer`
5. **Clique na requisição**
6. **Veja:**
   - **Status:** (200 = OK, 400 = erro, sem status = network error)
   - **Headers:** Verifique se a URL está correta
   - **Payload:** Verifique se os dados estão sendo enviados

---

## 🎯 Problemas Comuns e Soluções

### Problema 1: "Network Error" - Backend não está rodando

**Sintomas:**
- Console mostra: `ERR_CONNECTION_REFUSED`
- Network tab mostra: `Failed to fetch` ou `net::ERR_CONNECTION_REFUSED`

**Solução:**
```bash
# 1. Vá para o diretório do backend
cd E:\XXX\backend

# 2. Inicie o backend
mvn spring-boot:run

# 3. Aguarde ver: "Started JuriConnectApplication"
```

### Problema 2: "Network Error" - Timeout

**Sintomas:**
- Console mostra: `ECONNABORTED` ou `timeout`
- Requisição demora muito

**Solução:**
- Verifique se o backend está processando (veja o terminal)
- Pode ser problema com upload de arquivo grande
- Reduza o tamanho do arquivo OAM

### Problema 3: "Network Error" - CORS

**Sintomas:**
- Console mostra: `CORS policy` ou `Access-Control-Allow-Origin`
- Network tab mostra status 0 ou erro CORS

**Solução:**
- ✅ CORS já está configurado
- Verifique se o backend está rodando na porta 8080
- Verifique se o frontend está em `http://localhost:3000`

### Problema 4: "Network Error" - Formato do FormData

**Sintomas:**
- Backend recebe mas retorna erro 400
- Console mostra erro de validação

**Solução:**
- ✅ Já corrigido - Content-Type agora é removido automaticamente
- Reinicie o frontend se necessário

---

## 📝 Checklist de Verificação

Antes de tentar registrar, verifique:

- [ ] **Backend está rodando?** (Teste: `http://localhost:8080/api/public/stats`)
- [ ] **Frontend está rodando?** (Deve estar em `http://localhost:3000`)
- [ ] **PostgreSQL está rodando?** (Verifique nos Serviços do Windows)
- [ ] **Banco `juriconnect` existe?**
- [ ] **Senha do PostgreSQL está correta no `application.properties`?**
- [ ] **Porta 8080 está livre?**
- [ ] **Nenhum firewall bloqueando?**

---

## 🔧 Comandos de Verificação Rápida

### Verificar se backend está rodando:
```bash
# No navegador
http://localhost:8080/api/public/stats

# Ou no terminal
curl http://localhost:8080/api/public/stats
```

### Verificar porta 8080:
```bash
# Windows PowerShell
netstat -ano | findstr :8080
```

### Verificar PostgreSQL:
```bash
# Windows - Verificar serviços
services.msc
# Procure por "postgresql"
```

---

## 🎯 Solução Rápida (3 Passos)

### 1. Verificar Backend
```bash
# Abra no navegador
http://localhost:8080/api/public/stats
```

**Se não funcionar:**
```bash
cd E:\XXX\backend
mvn spring-boot:run
```

### 2. Verificar Console do Navegador
- F12 → Console
- Veja a mensagem de erro específica

### 3. Tentar Registrar Novamente
- Com o backend rodando
- Veja a mensagem de erro no console

---

## ⚠️ IMPORTANTE

**"Network Error" geralmente significa:**
1. **Backend não está rodando** (90% dos casos)
2. **Backend travou ou está com erro**
3. **Problema de CORS** (menos comum)

**Sempre verifique primeiro se o backend está rodando!**

---

## 🐛 Se Ainda Não Funcionar

### 1. Verifique os Logs do Backend
- Veja o terminal onde o backend está rodando
- Procure por erros ou exceções

### 2. Verifique o Console do Navegador
- F12 → Console
- Veja todas as mensagens de erro

### 3. Verifique a Network Tab
- F12 → Network
- Tente registrar
- Veja a requisição `register/lawyer`
- Clique e veja os detalhes

### 4. Teste com Postman/Insomnia
```bash
POST http://localhost:8080/api/auth/register/lawyer
Content-Type: multipart/form-data

data: {"fullName":"Teste","email":"teste@teste.com",...}
oamDocument: [arquivo]
```

---

**Após verificar que o backend está rodando, tente registrar novamente!** ✅

