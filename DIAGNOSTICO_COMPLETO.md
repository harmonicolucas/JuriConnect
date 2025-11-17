# 🔍 Diagnóstico Completo - Backend Rodando mas Não Responde

## ⚠️ Situação

Backend está rodando, mas ao tentar registrar advogado aparece:
**"Erro de rede. Verifique se o backend está rodando em http://localhost:8080"**

---

## ✅ Passo 1: Verificar se Backend Está Realmente Rodando

### Teste no Navegador:
```
http://localhost:8080/api/public/stats
```

**Se retornar JSON:** ✅ Backend está rodando  
**Se não funcionar:** ❌ Backend não está rodando ou travou

### Teste no Terminal:
```bash
curl http://localhost:8080/api/public/stats
```

---

## ✅ Passo 2: Verificar Logs do Backend

**Quando você tentar registrar advogado, o terminal do backend deve mostrar:**

```
=== REGISTRO DE ADVOGADO INICIADO ===
Data JSON recebido: {...}
Arquivo recebido: arquivo.pdf
Tamanho do arquivo: 12345
JSON deserializado com sucesso
Chamando authService.registerLawyer...
```

### Se NÃO aparecer nada:
- **Problema:** Requisição não está chegando ao backend
- **Causas possíveis:**
  - CORS bloqueando
  - URL incorreta
  - Backend travou

### Se aparecer e parar:
- **Problema:** Erro no processamento
- **Solução:** Veja o erro que aparece abaixo

---

## ✅ Passo 3: Verificar Console do Navegador

1. **Abra o Console** (F12 → Console)
2. **Tente registrar novamente**
3. **Veja as mensagens:**
   - `Erro ao registar advogado:`
   - `Código do erro:`
   - `Status:`

### Se Status = 0 ou sem status:
- **Problema:** Requisição não chegou ao backend
- **Causa:** CORS, URL incorreta, ou backend travou

### Se Status = 400, 500, etc:
- **Problema:** Backend recebeu mas retornou erro
- **Solução:** Veja a mensagem de erro específica

---

## ✅ Passo 4: Verificar Network Tab

1. **F12 → Network**
2. **Tente registrar**
3. **Procure por:** `register/lawyer`
4. **Clique na requisição**
5. **Veja:**
   - **Status:** (200 = OK, outros = erro)
   - **Headers:** URL está correta?
   - **Payload:** Dados estão sendo enviados?

---

## 🎯 Problemas Comuns

### Problema 1: Backend Travou Silenciosamente

**Sintomas:**
- Terminal não mostra novas mensagens
- Endpoint `/api/public/stats` não responde

**Solução:**
```bash
# Pare o backend (Ctrl+C)
# Reinicie
cd E:\XXX\backend
mvn spring-boot:run
```

### Problema 2: Banco de Dados Não Conecta

**Sintomas:**
- Terminal mostra: `password authentication failed`
- Backend inicia mas não conecta ao banco

**Solução:**
- Corrija a senha no `application.properties`
- Verifique se PostgreSQL está rodando

### Problema 3: Erro ao Processar Multipart

**Sintomas:**
- Terminal mostra erro ao receber arquivo
- Erro relacionado a `MultipartFile`

**Solução:**
- Verifique o tamanho do arquivo (máx. 10MB)
- Verifique o formato (PDF, DOCX, JPEG, PNG)

### Problema 4: Timeout

**Sintomas:**
- Requisição demora muito
- Timeout após 30 segundos

**Solução:**
- Verifique se o banco está respondendo
- Reduza o tamanho do arquivo

---

## 🔧 Solução Rápida

### 1. Reiniciar Backend
```bash
# Pare (Ctrl+C)
cd E:\XXX\backend
mvn spring-boot:run
```

### 2. Verificar Logs
- Veja o terminal após tentar registrar
- Procure por mensagens de erro

### 3. Testar Endpoint Simples
```
http://localhost:8080/api/public/stats
```

### 4. Verificar Console do Navegador
- F12 → Console
- Veja a mensagem de erro específica

---

## 📝 Informações para Debug

**Quando reportar o problema, inclua:**

1. **O que aparece no terminal do backend** quando você tenta registrar?
2. **O que aparece no console do navegador** (F12)?
3. **O endpoint `/api/public/stats` funciona?**
4. **Há erros no terminal do backend?**
5. **O backend iniciou completamente?** (viu "Started JuriConnectApplication"?)

---

**Com essas informações, conseguiremos identificar o problema exato!** ✅

