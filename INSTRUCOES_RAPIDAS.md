# 🚀 Instruções Rápidas - JuriConnect

## ⚡ Solução Rápida para o Erro

### Problema: Você executou no diretório errado!

**Solução:** Siga estes passos EXATOS:

---

## 📋 Passo a Passo Correto

### 1️⃣ Abra um NOVO Terminal (PowerShell ou CMD)

**IMPORTANTE:** Feche o terminal antigo e abra um novo!

### 2️⃣ Navegue para o Frontend

```bash
cd E:\XXX\frontend
```

### 3️⃣ Verifique se está no lugar certo

```bash
dir
```

Você deve ver: `package.json`, `src`, `public`

### 4️⃣ Instale as dependências

```bash
npm install
```

⏳ **Aguarde terminar** (pode levar 2-5 minutos na primeira vez)

### 5️⃣ Inicie o frontend

```bash
npm start
```

✅ O navegador abrirá automaticamente em `http://localhost:3000`

---

## 🎯 Alternativa: Use o Script Batch

1. **Abra o Explorador de Arquivos**
2. **Navegue até:** `E:\XXX\frontend`
3. **Clique duas vezes em:** `start-frontend.bat`
4. **Pronto!** O script faz tudo automaticamente

---

## ⚠️ Erro Comum

### ❌ ERRADO:
```
C:\Windows\System32> npm start
```

### ✅ CORRETO:
```
E:\XXX\frontend> npm start
```

**Sempre execute no diretório `E:\XXX\frontend`!**

---

## 🔧 Se Ainda Não Funcionar

### Verifique Node.js:
```bash
node --version
```
Deve mostrar: `v16.x.x` ou superior

### Reinstale as dependências:
```bash
cd E:\XXX\frontend
rmdir /s /q node_modules
del package-lock.json
npm install
npm start
```

---

## 📝 Comandos Completos (Copie e Cole)

```bash
cd E:\XXX\frontend
npm install
npm start
```

**Isso é tudo!** 🎉

---

## 🎬 Próximos Passos

Após o frontend iniciar:

1. **Backend deve estar rodando** em `http://localhost:8080`
2. **Frontend abrirá** em `http://localhost:3000`
3. **Teste o sistema** seguindo o `GUIA_TESTE.md`

---

**Boa sorte!** 🚀

