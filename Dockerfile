# Stage 1: Build
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven config and project files
COPY pom.xml .
COPY mvnw .
COPY .mvn/ .mvn/
COPY src/ src/

# Build the Spring Boot JAR
RUN ./mvnw clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port Render uses
EXPOSE 8080

# Run the app with dynamic port
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
