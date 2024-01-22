@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

REM Save the current directory
SET start_dir=%cd%

REM Build the enemy project
echo Building enemy project...
cd enemy
call gradlew bootJar
cd %start_dir%

REM Build the dungeon project
echo Building dungeon project...
cd dungeon
call gradlew clean shadowJar
cd %start_dir%

echo Build process completed.

