# Use a lightweight Java 17 base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR from target/ into the container
COPY target/Ai-tutor-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app will run on
EXPOSE 8080

# Run the Spring Boot app, using the PORT environment variable from Render
ENTRYPOINT ["java", "-Dserver.port=$PORT", "-jar", "app.jar"]

