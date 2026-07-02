@echo off
REM Run the Online Examination & Grading System.
if not exist out (
    echo Please run compile.bat first.
    exit /b 1
)
java -cp out com.logictc.exam.Main
