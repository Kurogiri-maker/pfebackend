# Stage 1: Run Java application using JRE image
# Use a Java runtime image as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file to the container
COPY --from=build /app/target/csv-0.0.1-SNAPSHOT.jar ./app.jar

# Run the JAR file when the container starts
CMD ["java", "-jar", "app.jar"]
