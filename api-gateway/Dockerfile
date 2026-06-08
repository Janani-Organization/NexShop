# Stage 1: Build with Java 21 JDK
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copy Maven wrapper and pom first for layer caching
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn .mvn

# Copy source
COPY src ./src
COPY bootstrap.yml ./bootstrap.yml

# Build JAR (skip tests)
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Stage 2: Runtime image (same base as original)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run as non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]