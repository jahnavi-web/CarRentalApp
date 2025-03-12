# Use OpenJDK 17 as base image
FROM openjdk:21

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/CarRentalApp-0.0.1-SNAPSHOT.jar app.jar


# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
