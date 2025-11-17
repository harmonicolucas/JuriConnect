# 🔧 Solução: Arquivo Muito Grande

## ⚠️ Problema Identificado

**Erro no backend:**
```
Maximum upload size exceeded
```

**Causa:** O arquivo OAM que você está tentando enviar é **maior que 10MB** (limite anterior).

---

## ✅ Correções Implementadas

### 1. Limite Aumentado
- ✅ **Antes:** 10MB
- ✅ **Agora:** 50MB

### 2. Mensagem de Erro Melhorada
- ✅ Agora mostra mensagem clara: "Arquivo muito grande. Tamanho máximo: 50MB"

### 3. Validação no Frontend
- ✅ Valida tamanho antes de enviar
- ✅ Mostra mensagem imediatamente

---

## 🔧 O Que Fazer Agora

### Opção 1: Reiniciar Backend (Recomendado)

1. **Pare o backend** (Ctrl+C no terminal)
2. **Reinicie:**
   ```bash
   cd E:\XXX\backend
   mvn spring-boot:run
   ```
3. **Aguarde iniciar completamente**
4. **Tente registrar novamente**

### Opção 2: Reduzir Tamanho do Arquivo

Se o arquivo for maior que 50MB:

1. **Comprima o PDF** (use ferramentas online)
2. **Ou converta para JPEG/PNG** com menor resolução
3. **Ou divida o documento** em partes menores

---

## 📝 Tamanhos Máximos

- **PDF:** Máx. 50MB
- **DOCX:** Máx. 50MB
- **JPEG/PNG:** Máx. 50MB

**Dica:** Para documentos OAM, geralmente PDFs são pequenos. Se estiver muito grande, pode estar com imagens de alta resolução. Comprima ou reduza a qualidade.

---

## ✅ Após Reiniciar

1. **Reinicie o backend** (com o novo limite de 50MB)
2. **Tente registrar advogado novamente**
3. **Se o arquivo ainda for muito grande:** Reduza o tamanho do arquivo

---

## 🎯 Verificação

**Após reiniciar o backend, teste:**

1. **Tente registrar advogado**
2. **Se o arquivo for menor que 50MB:** Deve funcionar
3. **Se ainda der erro de tamanho:** Reduza o arquivo

---

**Problema resolvido! Reinicie o backend e tente novamente!** ✅

