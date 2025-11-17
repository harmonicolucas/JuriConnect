# 🔍 Debug: Backend Rodando mas Network Error

## ⚠️ Situação Atual

- ✅ Backend está rodando
- ❌ Ao registrar advogado: "Erro de rede. Verifique se o backend está rodando"

---

## 🎯 Diagnóstico Passo a Passo

### PASSO 1: Verificar se Backend Está Respondendo

**Teste no navegador:**
```
http://localhost:8080/api/public/stats
```

**Se funcionar:** ✅ Backend está rodando  
**Se não funcionar:** ❌ Backend travou ou não está rodando

---

### PASSO 2: Verificar Logs do Backend (MUITO IMPORTANTE)

**Quando você tentar registrar advogado, olhe o terminal do backend.**

**Você DEVE ver:**
```
=== REGISTRO DE ADVOGADO INICIADO ===
Data JSON recebido: {...}
Arquivo recebido: arquivo.pdf
```

**Se NÃO aparecer nada:**
- ❌ A requisição **NÃO está chegando** ao backend
- **Possíveis causas:**
  - CORS bloqueando
  - URL incorreta
  - Backend travou silenciosamente

**Se aparecer e parar:**
- ✅ A requisição chegou
- ❌ Há um erro no processamento
- **Veja o erro que aparece abaixo**

---

### PASSO 3: Verificar Console do Navegador

1. **F12 → Console**
2. **Tente registrar novamente**
3. **Veja:**
   - `Erro ao registar advogado:`
   - `Código do erro:`
   - `Status:` (0 = não chegou, 400/500 = chegou mas erro)

---

### PASSO 4: Verificar Network Tab

1. **F12 → Network**
2. **Tente registrar**
3. **Procure:** `register/lawyer`
4. **Clique na requisição**
5. **Veja:**
   - **Status:** (200 = OK, 0 = não chegou, outros = erro)
   - **Request URL:** Deve ser `http://localhost:8080/api/auth/register/lawyer`
   - **Request Method:** Deve ser `POST`

---

## 🔧 Soluções Baseadas no Diagnóstico

### Cenário 1: Terminal NÃO mostra logs

**Problema:** Requisição não chega ao backend

**Soluções:**
1. **Verificar URL:**
   - Deve ser: `http://localhost:8080/api/auth/register/lawyer`
   - Verifique no Network tab

2. **Verificar CORS:**
   - Backend já está configurado
   - Verifique se frontend está em `http://localhost:3000`

3. **Reiniciar Backend:**
   ```bash
   # Pare (Ctrl+C)
   cd E:\XXX\backend
   mvn spring-boot:run
   ```

4. **Verificar Firewall:**
   - Desative temporariamente o firewall
   - Teste novamente

---

### Cenário 2: Terminal mostra logs mas para

**Problema:** Erro no processamento

**Soluções:**
1. **Veja o erro completo no terminal**
2. **Erro mais comum:** Senha do PostgreSQL incorreta
3. **Corrija o problema específico**

---

### Cenário 3: Status 0 no Network Tab

**Problema:** Requisição não chega ao servidor

**Soluções:**
1. **Backend travou:** Reinicie
2. **URL incorreta:** Verifique
3. **CORS:** Já configurado, mas verifique

---

## 📝 Informações que Preciso

**Para ajudar melhor, me diga:**

1. **O que aparece no terminal do backend** quando você tenta registrar?
   - Aparece `=== REGISTRO DE ADVOGADO INICIADO ===`?
   - Ou não aparece nada?

2. **O endpoint `/api/public/stats` funciona?**
   - Abra no navegador e me diga o resultado

3. **O que aparece no console do navegador?** (F12)
   - Qual é a mensagem de erro completa?

4. **O que aparece no Network tab?** (F12 → Network)
   - Status da requisição `register/lawyer`?

---

## 🎯 Teste Rápido

### 1. Teste Backend:
```
http://localhost:8080/api/public/stats
```

### 2. Teste Endpoint de Auth:
```
http://localhost:8080/api/auth/test
```

### 3. Tente Registrar Advogado
- Veja o terminal do backend
- Veja o console do navegador
- Veja o Network tab

---

**Com essas informações, conseguiremos identificar o problema exato!** ✅

