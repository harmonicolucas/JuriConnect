@echo off
echo ========================================
echo   JuriConnect - Iniciando Frontend
echo ========================================
echo.

cd /d "%~dp0"

echo Diretorio atual: %CD%
echo.

if not exist "package.json" (
    echo ERRO: package.json nao encontrado!
    echo Certifique-se de estar no diretorio do frontend.
    pause
    exit /b 1
)

echo Instalando dependencias...
call npm install

if %ERRORLEVEL% NEQ 0 (
    echo ERRO: Falha ao instalar dependencias!
    pause
    exit /b 1
)

echo.
echo Iniciando o frontend...
echo O navegador abrira automaticamente em http://localhost:3000
echo.
call npm start

pause

