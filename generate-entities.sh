#!/bin/bash

# Script to generate JPA entities from PostgreSQL database
# Requires: postgresql, java, gradle

echo "=== Database to JPA Entity Generator ==="
echo "Database: PostgreSQL"
echo "Target: Spring Boot JPA Entities"
echo

# Set database connection
DB_URL="jdbc:postgresql://aws-0-ap-northeast-1.pooler.supabase.com:6543/postgres"
DB_USER="postgres.gomnsfhwgtgkgqwumedv"
DB_PASSWORD="123456"
DB_DRIVER="org.postgresql.Driver"

# Set output directory
OUTPUT_DIR="src/main/java/com/vnpt/system/entity"

echo "Creating output directory: $OUTPUT_DIR"
mkdir -p $OUTPUT_DIR

echo "Connecting to database..."
echo "URL: $DB_URL"
echo "User: $DB_USER"
echo

# Run entity generation
echo "Running entity generation..."
./gradlew generateEntities

echo "Entity generation completed!"
echo "Generated entities are in: $OUTPUT_DIR"
