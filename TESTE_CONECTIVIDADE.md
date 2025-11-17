# 🧪 Teste de Conectividade - Backend

## ✅ Teste 1: Endpoint Simples (Ping)

**No navegador, abra:**
```
http://localhost:8080/api/test/ping
```

**Resultado esperado:**
```json
{
  "status": "OK",
  "message": "Backend está funcionando!",
  "timestamp": "2025-11-14T..."
}
```

**Se funcionar:** ✅ Backend está rodando e respondendo

---

## ✅ Teste 2: Endpoint Público

**No navegador:**
```
http://localhost:8080/api/public/stats
```

**Se funcionar:** ✅ Backend está rodando

---

## ✅ Teste 3: Teste Multipart (Simula Registro de Advogado)

**Use o console do navegador (F12):**

```javascript
const formData = new FormData();
formData.append('data', JSON.stringify({test: 'test'}));
formData.append('file', new Blob(['test'], {type: 'text/plain'}), 'test.txt');

fetch('http://localhost:8080/api/test/multipart', {
  method: 'POST',
  body: formData
})
.then(r => r.json())
.then(console.log)
.catch(console.error);
```

**Resultado esperado:**
```json
{
  "status": "OK",
  "dataReceived": true,
  "fileReceived": true,
  "fileName": "test.txt",
  "fileSize": 4
}
```

**Se funcionar:** ✅ Multipart está funcionando

---

## 🔍 Diagnóstico

### Se Teste 1 funciona mas Teste 3 não:
- **Problema:** Multipart não está funcionando
- **Solução:** Verifique configuração de upload

### Se nenhum teste funciona:
- **Problema:** Backend não está rodando ou travou
- **Solução:** Reinicie o backend

### Se todos funcionam mas registro não:
- **Problema:** Erro específico no processamento
- **Solução:** Veja logs do backend

---

**Execute esses testes e me diga os resultados!** ✅

