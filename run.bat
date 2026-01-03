@echo off
chcp 65001 >nul
cd /d C:\Users\41400\Desktop\500
echo ========================================
echo NHS Healthcare Management System
echo ========================================
echo.
echo Creating bin directory...
mkdir bin 2>nul
echo.
echo Compiling Model layer...
javac -encoding UTF-8 -d bin src/model/*.java
if errorlevel 1 goto error
echo Compiling Util layer...
javac -encoding UTF-8 -d bin -cp bin src/util/*.java
if errorlevel 1 goto error
echo Compiling Controller layer...
javac -encoding UTF-8 -d bin -cp bin src/controller/*.java
if errorlevel 1 goto error
echo Compiling View layer...
javac -encoding UTF-8 -d bin -cp bin src/view/*.java
if errorlevel 1 goto error
echo Compiling Main application...
javac -encoding UTF-8 -d bin -cp bin src/HealthcareApp.java
if errorlevel 1 goto error
echo.
echo ========================================
echo Compilation successful! Starting app...
echo ========================================
echo.
java -cp bin HealthcareApp
goto end
:error
echo.
echo Compilation failed!
:end
pause
