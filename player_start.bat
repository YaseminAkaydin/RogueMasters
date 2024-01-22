@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

REM Save the current directory
SET start_dir=%cd%

REM Build the player project
echo Building player project...
cd player
call gradlew clean shadowJar
cd %start_dir%

echo Build process completed.

REM Check if IP address was provided
IF "%1"=="" (
    echo No IP address provided. Exiting.
    exit /b
)

REM Start the Player Client
echo Starting Player Client...
start cmd /k javaw -jar player\build\libs\player-1.jar %1 8812
