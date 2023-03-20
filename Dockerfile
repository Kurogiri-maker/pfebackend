# Stage 1: Compile and package Java application using Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn package -DskipTests

# Stage 2: Run Java application using JRE image
# Use a Java runtime image as the base image
FROM openjdk:17-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file to the container
COPY --from=build /app/target/csv-0.0.1-SNAPSHOT.jar ./app.jar

# Run the JAR file when the container starts
CMD ["java", "-jar", "app.jar"]
