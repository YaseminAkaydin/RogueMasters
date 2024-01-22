@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

REM Save the current directory
SET start_dir=%cd%

REM Start the Dungeon Server
echo Starting Dungeon Server...
start cmd /k java -jar dungeon\build\libs\DungeonServer-1.jar

REM Start the Enemy Server
echo Starting Enemy Server...
start cmd /k java -jar enemy\build\libs\enemy-1.jar

echo Servers are starting...
