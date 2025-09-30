# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR into the container
COPY target/seMethods-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
