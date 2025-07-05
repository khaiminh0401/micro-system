@echo off
REM Script to generate JPA entities from PostgreSQL database
REM Requires: postgresql, java, gradle

echo === Database to JPA Entity Generator ===
echo Database: PostgreSQL
echo Target: Spring Boot JPA Entities
echo.

REM Set database connection
set DB_URL=jdbc:postgresql://aws-0-ap-northeast-1.pooler.supabase.com:6543/postgres
set DB_USER=postgres.gomnsfhwgtgkgqwumedv
set DB_PASSWORD=123456
set DB_DRIVER=org.postgresql.Driver

REM Set output directory
set OUTPUT_DIR=src\main\java\com\vnpt\system\entity

echo Creating output directory: %OUTPUT_DIR%
if not exist %OUTPUT_DIR% mkdir %OUTPUT_DIR%

echo Connecting to database...
echo URL: %DB_URL%
echo User: %DB_USER%
echo.

REM Run entity generation
echo Running entity generation...
gradlew.bat generateEntities

echo Entity generation completed!
echo Generated entities are in: %OUTPUT_DIR%
pause
