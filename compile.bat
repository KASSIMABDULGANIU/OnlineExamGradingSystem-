@echo off
REM Compile every Java source file under src\ into the out\ folder.
if exist out rmdir /s /q out
mkdir out
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
del sources.txt
if %errorlevel%==0 (
    echo Build succeeded. Run the app with run.bat
) else (
    echo Build failed. See the messages above.
)
