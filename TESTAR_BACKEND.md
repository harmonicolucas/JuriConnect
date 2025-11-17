# 🧪 Como Testar se o Backend Está Funcionando

## ✅ Teste 1: Endpoint Público (Mais Simples)

**No navegador, abra:**
```
http://localhost:8080/api/public/stats
```

**Resultado esperado:**
```json
{
  "totalLawyers": 0,
  "totalClients": 0,
  "satisfactionRate": 4.5,
  "legalAreas": [...]
}
```

**Se funcionar:** ✅ Backend está rodando e respondendo

---

## ✅ Teste 2: Endpoint de Teste

**No navegador, abra:**
```
http://localhost:8080/api/auth/test
```

**Resultado esperado:**
```json
{
  "message": "Backend está funcionando!",
  "error": "SUCCESS"
}
```

**Se funcionar:** ✅ Backend está rodando e o endpoint de auth está acessível

---

## ✅ Teste 3: Verificar Logs do Backend

**No terminal onde o backend está rodando, você deve ver:**

Quando tentar registrar advogado:
```
=== REGISTRO DE ADVOGADO INICIADO ===
Data JSON recebido: {...}
Arquivo recebido: nome_do_arquivo.pdf
Tamanho do arquivo: 12345
JSON deserializado com sucesso
Chamando authService.registerLawyer...
```

**Se NÃO aparecer nada:** A requisição não está chegando ao backend

**Se aparecer e parar:** Há um erro no processamento (veja o erro abaixo)

---

## 🔍 Verificações Adicionais

### 1. Backend Iniciou Corretamente?

**No terminal do backend, você deve ver:**
```
Started JuriConnectApplication in X.XXX seconds
```

**Se não aparecer:** Backend não iniciou corretamente

### 2. Há Erros no Terminal?

**Procure por:**
- `ERROR`
- `Exception`
- `FATAL`
- `password authentication failed`

**Se houver:** Anote o erro e corrija

### 3. Banco de Dados Conectou?

**No terminal, você deve ver:**
```
HikariPool-1 - Start completed.
```

**Se aparecer erro de autenticação:** Senha do PostgreSQL está incorreta

---

## 🐛 Se o Backend Está Rodando mas Não Responde

### Problema 1: Backend Travou

**Sintomas:**
- Terminal não mostra novas mensagens
- Requisições não chegam

**Solução:**
1. Pare o backend (Ctrl+C)
2. Reinicie: `mvn spring-boot:run`

### Problema 2: Erro ao Processar Requisição

**Sintomas:**
- Terminal mostra erro ao tentar registrar
- Requisição chega mas falha

**Solução:**
- Veja o erro no terminal
- Corrija o problema específico

### Problema 3: Timeout

**Sintomas:**
- Requisição demora muito
- Timeout após 30 segundos

**Solução:**
- Verifique se o banco de dados está respondendo
- Verifique o tamanho do arquivo (máx. 10MB)

---

## 📝 Checklist de Diagnóstico

Quando tentar registrar advogado:

1. **Backend está rodando?**
   - Teste: `http://localhost:8080/api/public/stats`

2. **Requisição chega ao backend?**
   - Veja o terminal: deve aparecer `=== REGISTRO DE ADVOGADO INICIADO ===`

3. **Há erros no terminal?**
   - Procure por mensagens de erro após tentar registrar

4. **Banco de dados está conectado?**
   - Terminal deve mostrar: `HikariPool-1 - Start completed`

5. **Arquivo está sendo recebido?**
   - Terminal deve mostrar: `Arquivo recebido: nome_arquivo.pdf`

---

## 🎯 Próximos Passos

1. **Teste o endpoint público:** `http://localhost:8080/api/public/stats`
2. **Tente registrar advogado novamente**
3. **Veja o terminal do backend** - deve aparecer logs
4. **Veja o console do navegador** (F12) - veja a mensagem de erro

---

**Com esses logs, conseguiremos identificar exatamente onde está o problema!** ✅

