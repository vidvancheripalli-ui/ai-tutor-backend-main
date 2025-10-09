# -----------------------------
# Stage 1: Build with Maven
# -----------------------------
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and config
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .

# Make mvnw executable
RUN chmod +x mvnw

# Copy source code
COPY src/ src/

# Build the Spring Boot JAR, skip tests for faster build
RUN ./mvnw clean package -DskipTests

# -----------------------------
# Stage 2: Run the app
# -----------------------------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port Render uses
EXPOSE 8080

# Run the Spring Boot app with dynamic Render port
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
