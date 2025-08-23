#!/bin/bash
set -e  # 에러 발생 시 즉시 중단

echo "=== Gradle Build 시작 ==="
./gradlew clean bootJar

echo "=== 기존 Docker Compose 종료 ==="
docker compose down --rmi all -v

echo "=== Docker Compose 실행 ==="
docker compose up --build -d