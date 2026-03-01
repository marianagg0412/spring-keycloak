# Multi-stage Dockerfile for Spring Boot 3/4 app (Java 21)

# ===== Stage 1: Build =====
FROM eclipse-temurin:21-jdk as builder
WORKDIR /workspace

COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY src src

RUN chmod +x mvnw
RUN ./mvnw -DskipTests package -P !native

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]