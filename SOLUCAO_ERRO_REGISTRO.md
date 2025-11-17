# 🔧 Solução: Erro ao Registar Cliente

## ⚠️ Problema

Ao tentar registrar um cliente, aparece: **"Erro ao registar. Tente novamente"**

---

## ✅ Soluções Implementadas

### 1. Melhorias no Backend
- ✅ Controller agora retorna mensagens de erro específicas
- ✅ Tratamento de validações melhorado
- ✅ Mensagens de erro mais descritivas

### 2. Melhorias no Frontend
- ✅ Exibe mensagens de erro mais específicas
- ✅ Logs no console para debug

---

## 🔍 Verificações Necessárias

### 1. Backend está rodando?
**Verifique:**
- O backend deve estar rodando em `http://localhost:8080`
- Abra o navegador e acesse: `http://localhost:8080/api/public/stats`
- Deve retornar JSON (não erro 404)

**Se não estiver rodando:**
```bash
cd E:\XXX\backend
mvn spring-boot:run
```

### 2. Formato do Contacto
O formato do contacto deve ser **exatamente**:
```
+258 8XX XXX XXX
```

**Exemplos válidos:**
- ✅ `+258 84 123 456`
- ✅ `+258 85 987 654`
- ✅ `+258 82 555 123`

**Exemplos inválidos:**
- ❌ `+25884123456` (sem espaços)
- ❌ `258 84 123 456` (sem +)
- ❌ `+25884123456` (sem espaços)

### 3. Validações Obrigatórias
- ✅ Nome completo preenchido
- ✅ Email válido
- ✅ Contacto no formato correto
- ✅ Palavra-passe preenchida
- ✅ Confirmação de palavra-passe igual
- ✅ Checkbox "Aceito os Termos" marcado

### 4. Banco de Dados
- ✅ PostgreSQL deve estar rodando
- ✅ Banco `juriconnect` deve existir
- ✅ Senha do PostgreSQL deve estar correta no `application.properties`

---

## 🐛 Debug - Como Ver o Erro Real

### No Frontend (Console do Navegador):
1. Abra o **Console do Navegador** (F12)
2. Vá na aba **Console**
3. Tente registrar novamente
4. Veja a mensagem de erro completa no console

### No Backend (Terminal):
1. Veja o terminal onde o backend está rodando
2. Procure por mensagens de erro após tentar registrar
3. Anote a mensagem de erro completa

---

## 📝 Exemplo de Dados Válidos

```json
{
  "fullName": "João Silva",
  "email": "joao@teste.com",
  "contact": "+258 84 123 456",
  "password": "senha123",
  "confirmPassword": "senha123",
  "acceptTerms": true
}
```

---

## 🔧 Passos para Resolver

### Passo 1: Verificar Backend
```bash
# Verifique se está rodando
curl http://localhost:8080/api/public/stats
```

### Passo 2: Verificar Console do Navegador
- Abra F12 → Console
- Tente registrar
- Veja a mensagem de erro

### Passo 3: Verificar Formato do Contacto
- Use exatamente: `+258 8XX XXX XXX`
- Com espaços e o sinal +

### Passo 4: Verificar Banco de Dados
- PostgreSQL rodando?
- Banco `juriconnect` existe?
- Senha correta no `application.properties`?

---

## 🎯 Mensagens de Erro Comuns

### "Email já está em uso"
- **Solução:** Use outro email ou faça login com o email existente

### "Contacto deve estar no formato +258 8XX XXX XXX"
- **Solução:** Use o formato exato com espaços

### "As palavras-passe não coincidem"
- **Solução:** Digite a mesma senha nos dois campos

### "Deve aceitar os Termos e Políticas"
- **Solução:** Marque o checkbox

### "Network Error" ou "Failed to fetch"
- **Solução:** Backend não está rodando ou há problema de CORS

---

## ✅ Após as Correções

1. **Reinicie o backend** (se fez alterações)
2. **Limpe o cache do navegador** (Ctrl+Shift+Delete)
3. **Tente registrar novamente**
4. **Veja a mensagem de erro específica** (agora deve aparecer)

---

**Agora as mensagens de erro serão mais específicas e ajudarão a identificar o problema!** ✅

