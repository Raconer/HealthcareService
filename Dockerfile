FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY ./build/libs/HealthcareService.jar app.jar

RUN apt-get update \
  && apt-get install -y netcat \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/*

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "until nc -z mysql 3306; do echo 'Waiting for mysql...'; sleep 2; done; java -Dspring.profiles.active=docker -jar app.jar"]