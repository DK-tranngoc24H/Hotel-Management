@echo off
chcp 65001 >nul
cd /d "%~dp0"

where javac >nul 2>nul
if %errorlevel% equ 0 (
    set JAVAC_CMD=javac
    set JAVA_CMD=java
) else (
    set JAVAC_CMD="C:\Users\ADMIN\.vscode\extensions\redhat.java-1.58.0-win32-x64\jre\25.0.4.1-win32-x86_64\bin\javac.exe"
    set JAVA_CMD="C:\Users\ADMIN\.vscode\extensions\redhat.java-1.58.0-win32-x64\jre\25.0.4.1-win32-x86_64\bin\java.exe"
)

echo ==============================================
echo   HOTEL MANAGEMENT - BIEN DICH VA KHOI DONG   
echo ==============================================
echo.
echo [1/2] Dang bien dich cac module...
%JAVAC_CMD% -encoding UTF-8 ClassDungChung\*.java Module1_DoiTuongCoBan\*.java Module2_QuanLyPhong\*.java Module3_DichVuTienIch\*.java Module4_QuanLyDatPhong\*.java Module5_Menu\*.java

if %errorlevel% neq 0 (
    echo.
    echo [LOI] Bien dich that bai! Vui long kiem tra lai ma nguon.
    pause
    exit /b %errorlevel%
)

echo [2/2] Bien dich thanh cong! Dang khoi chay chuong trinh...
echo.
%JAVA_CMD% -cp "ClassDungChung;Module1_DoiTuongCoBan;Module2_QuanLyPhong;Module3_DichVuTienIch;Module4_QuanLyDatPhong;Module5_Menu;." Main
pause

