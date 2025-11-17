# 🔧 Solução: Erro ao Registar Advogado

## ⚠️ Problema

Ao tentar registrar um advogado, aparece: **"Erro ao registar. Tente novamente"**

---

## ✅ Correções Implementadas

### 1. Backend
- ✅ Deserialização correta do JSON do FormData
- ✅ Validação manual dos campos
- ✅ Mensagens de erro específicas
- ✅ Tratamento de exceções melhorado

### 2. Frontend
- ✅ Logs detalhados no console
- ✅ Mensagens de erro mais específicas

---

## 🔍 Verificações Necessárias

### 1. Backend está rodando?
**Verifique:**
- Backend deve estar em `http://localhost:8080`
- Teste: `http://localhost:8080/api/public/stats`

### 2. Formato do Contacto
Deve ser exatamente: `+258 8XX XXX XXX` (com espaços)

**Exemplos válidos:**
- ✅ `+258 84 123 456`
- ✅ `+258 85 987 654`

### 3. Campos Obrigatórios
- ✅ Nome completo
- ✅ Email válido
- ✅ Contacto no formato correto
- ✅ Número da carteira OAM
- ✅ Palavra-passe
- ✅ Confirmação de palavra-passe (igual)
- ✅ **Pelo menos uma área de atuação selecionada**
- ✅ **Documento OAM enviado** (PDF, DOCX, JPEG, PNG)
- ✅ Checkbox "Aceito os Termos" marcado

### 4. Documento OAM
- ✅ Deve ser um arquivo válido
- ✅ Formatos aceitos: PDF, DOCX, JPEG, PNG
- ✅ Tamanho máximo: 10MB

---

## 🐛 Debug - Ver o Erro Real

### No Navegador (F12):
1. Abra o **Console** (F12 → Console)
2. Tente registrar novamente
3. Veja a mensagem de erro completa
4. Procure por: `Erro ao registar advogado:` e `Resposta do servidor:`

### No Backend (Terminal):
1. Veja o terminal onde o backend está rodando
2. Procure por mensagens de erro após tentar registrar
3. Veja o stack trace completo

---

## 📝 Exemplo de Dados Válidos

```json
{
  "fullName": "Dr. Maria Santos",
  "email": "maria@teste.com",
  "contact": "+258 85 987 654",
  "password": "senha123",
  "confirmPassword": "senha123",
  "oamNumber": "OAM12345",
  "specializations": ["PENAL", "CIVIL"],
  "acceptTerms": true
}
```

**E um arquivo OAM:** (PDF, DOCX, JPEG ou PNG)

---

## 🎯 Mensagens de Erro Comuns

### "Email já está em uso"
- **Solução:** Use outro email

### "Número da carteira OAM já está em uso"
- **Solução:** Use outro número OAM

### "Contacto deve estar no formato +258 8XX XXX XXX"
- **Solução:** Use o formato exato com espaços

### "Pelo menos uma área de atuação deve ser selecionada"
- **Solução:** Selecione pelo menos uma área no dropdown

### "Documento da OAM é obrigatório"
- **Solução:** Faça upload do documento OAM

### "Erro ao processar dados"
- **Solução:** Verifique se todos os campos estão preenchidos corretamente

### "Network Error" ou "Failed to fetch"
- **Solução:** Backend não está rodando ou problema de CORS

---

## 🔧 Passos para Resolver

### Passo 1: Verificar Backend
```bash
# Verifique se está rodando
curl http://localhost:8080/api/public/stats
```

### Passo 2: Verificar Console do Navegador
- F12 → Console
- Tente registrar
- Veja a mensagem de erro específica

### Passo 3: Verificar Dados
- Formato do contacto correto?
- Áreas de atuação selecionadas?
- Documento OAM enviado?
- Todos os campos preenchidos?

### Passo 4: Verificar Banco de Dados
- PostgreSQL rodando?
- Banco `juriconnect` existe?
- Senha correta no `application.properties`?

---

## ⚠️ Problemas Específicos do Registro de Advogado

### 1. Upload de Arquivo
- Certifique-se de que o arquivo foi selecionado
- Verifique o tamanho (máx. 10MB)
- Verifique o formato (PDF, DOCX, JPEG, PNG)

### 2. Áreas de Atuação
- **Deve selecionar pelo menos uma área**
- Use Ctrl+Click (ou Cmd+Click no Mac) para selecionar múltiplas

### 3. Formato do JSON
- O frontend envia JSON como string no FormData
- O backend agora deserializa corretamente

---

## ✅ Após as Correções

1. **Reinicie o backend** (se fez alterações)
2. **Limpe o cache do navegador** (Ctrl+Shift+Delete)
3. **Tente registrar novamente**
4. **Veja a mensagem de erro específica** no console

---

## 🎯 Checklist Completo

Antes de registrar, verifique:
- [ ] Backend está rodando
- [ ] Nome completo preenchido
- [ ] Email válido e único
- [ ] Contacto no formato `+258 8XX XXX XXX`
- [ ] Número OAM preenchido e único
- [ ] Palavra-passe preenchida
- [ ] Confirmação igual à palavra-passe
- [ ] **Pelo menos uma área de atuação selecionada**
- [ ] **Documento OAM selecionado e válido**
- [ ] Checkbox "Aceito os Termos" marcado

---

**Agora as mensagens de erro serão específicas e ajudarão a identificar o problema!** ✅

**Verifique o console do navegador (F12) para ver a mensagem de erro real!**

