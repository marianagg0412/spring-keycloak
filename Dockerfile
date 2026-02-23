# Multi-stage Dockerfile for Spring Boot 3/4 app (Java 21)

# Stage 1: build with Maven (optional if you want to build inside Docker)
FROM eclipse-temurin:21-jdk as builder
WORKDIR /workspace
COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY src src
RUN chmod +x mvnw
RUN ./mvnw -DskipTests package -P !native

# Stage 2: run
FROM eclipse-temurin:21-jre
ARG JAR_FILE=target/keycloak-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
