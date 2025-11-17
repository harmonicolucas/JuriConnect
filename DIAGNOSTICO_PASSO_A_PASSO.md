# 🔍 Diagnóstico Passo a Passo - Backend Rodando mas Network Error

## ⚠️ Situação

- ✅ Você diz que o backend está rodando
- ❌ Ao registrar advogado: "Erro de rede. Verifique se o backend está rodando"

---

## 🎯 PASSO 1: Confirmar que Backend Está Realmente Rodando

### Teste no Navegador:
```
http://localhost:8080/api/test/ping
```

**Resultado esperado:**
```json
{
  "status": "OK",
  "message": "Backend está funcionando!",
  "timestamp": "..."
}
```

**Se funcionar:** ✅ Backend está rodando  
**Se não funcionar:** ❌ Backend NÃO está rodando ou travou

---

## 🎯 PASSO 2: Verificar Terminal do Backend

**Olhe o terminal onde o backend está rodando.**

### Você deve ver:
```
Started JuriConnectApplication in X.XXX seconds
```

**Se não aparecer:** Backend não iniciou completamente

### Quando você tentar registrar advogado, DEVE aparecer:
```
=== REGISTRO DE ADVOGADO INICIADO ===
Data JSON recebido: {...}
Arquivo recebido: arquivo.pdf
```

**Se NÃO aparecer nada:**
- ❌ Requisição não está chegando ao backend
- **Possíveis causas:**
  - Backend travou
  - URL incorreta
  - CORS bloqueando

**Se aparecer e parar:**
- ✅ Requisição chegou
- ❌ Há um erro no processamento
- **Veja o erro que aparece**

---

## 🎯 PASSO 3: Verificar Console do Navegador

1. **F12 → Console**
2. **Tente registrar advogado**
3. **Veja TODAS as mensagens:**
   - `Erro ao registar advogado:`
   - `Código do erro:`
   - `Status:`
   - `Mensagem:`

**Anote TODAS as mensagens!**

---

## 🎯 PASSO 4: Verificar Network Tab

1. **F12 → Network**
2. **Limpe a lista** (ícone de limpar)
3. **Tente registrar advogado**
4. **Procure por:** `register/lawyer`
5. **Clique na requisição**
6. **Veja:**
   - **Status:** (200 = OK, 0 = não chegou, 400/500 = erro)
   - **Request URL:** Deve ser `http://localhost:8080/api/auth/register/lawyer`
   - **Request Method:** Deve ser `POST`
   - **Headers:** Veja se há erros

---

## 🔧 Soluções Baseadas no Diagnóstico

### Cenário A: Teste `/api/test/ping` NÃO funciona

**Problema:** Backend não está rodando ou travou

**Solução:**
1. **Pare o backend** (Ctrl+C no terminal)
2. **Reinicie:**
   ```bash
   cd E:\XXX\backend
   mvn spring-boot:run
   ```
3. **Aguarde ver:** `Started JuriConnectApplication`
4. **Teste novamente:** `http://localhost:8080/api/test/ping`

---

### Cenário B: Teste funciona mas registro não - Terminal NÃO mostra logs

**Problema:** Requisição não chega ao endpoint de registro

**Soluções:**
1. **Verificar URL no Network tab:**
   - Deve ser: `http://localhost:8080/api/auth/register/lawyer`
   - Se diferente, há problema no frontend

2. **Verificar CORS:**
   - Já está configurado
   - Mas verifique se frontend está em `http://localhost:3000`

3. **Reiniciar backend:**
   ```bash
   # Pare (Ctrl+C)
   mvn spring-boot:run
   ```

---

### Cenário C: Terminal mostra logs mas para

**Problema:** Erro no processamento

**Soluções:**
1. **Veja o erro completo no terminal**
2. **Erro mais comum:** Senha do PostgreSQL incorreta
3. **Corrija o problema específico**

---

### Cenário D: Status 0 no Network Tab

**Problema:** Requisição não chega ao servidor

**Soluções:**
1. **Backend travou:** Reinicie
2. **Firewall bloqueando:** Desative temporariamente
3. **URL incorreta:** Verifique no Network tab

---

## 📝 Checklist de Verificação

Antes de reportar, verifique:

- [ ] **Backend iniciou completamente?** (viu "Started JuriConnectApplication"?)
- [ ] **Teste `/api/test/ping` funciona?** (no navegador)
- [ ] **Terminal mostra logs ao tentar registrar?** (deve aparecer `=== REGISTRO DE ADVOGADO INICIADO ===`)
- [ ] **Há erros no terminal?** (procure por ERROR, Exception, FATAL)
- [ ] **Status no Network tab?** (200, 0, 400, 500?)
- [ ] **Console do navegador mostra o quê?** (F12 → Console)

---

## 🎯 Informações que Preciso

**Para ajudar melhor, me diga:**

1. **O teste `/api/test/ping` funciona?**
   - Abra: `http://localhost:8080/api/test/ping`
   - O que aparece?

2. **O que aparece no terminal do backend quando você tenta registrar?**
   - Aparece `=== REGISTRO DE ADVOGADO INICIADO ===`?
   - Ou não aparece nada?
   - Ou aparece erro?

3. **O que aparece no console do navegador?** (F12)
   - Qual é a mensagem completa?

4. **Status no Network tab?** (F12 → Network → register/lawyer)
   - Qual é o status? (200, 0, 400, 500?)

---

## 🚀 Teste Rápido Agora

### 1. Teste Backend:
```
http://localhost:8080/api/test/ping
```

### 2. Se funcionar, tente registrar advogado
- Veja o terminal do backend
- Veja o console do navegador (F12)
- Veja o Network tab (F12 → Network)

### 3. Me diga:
- O teste `/api/test/ping` funcionou?
- O que apareceu no terminal?
- O que apareceu no console?

---

**Com essas informações, conseguiremos identificar o problema exato!** ✅

