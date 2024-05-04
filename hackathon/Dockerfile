# Use the official OpenJDK image for Java 21 (assuming it's available)
FROM openjdk:21-jdk-slim as build

# Set the working directory inside the Docker container
WORKDIR /app

# Copy the Maven executable wrapper from your project into the Docker image
COPY mvnw .
COPY .mvn .mvn

# Copy your project's pom.xml and source code into the Docker image
COPY pom.xml .
COPY src src

# Give execution rights to the Maven wrapper
RUN chmod +x ./mvnw

# Use Maven to build the project (this will download all dependencies and package the application)
RUN ./mvnw clean package

# Use the OpenJDK image to create a minimal runtime container
FROM openjdk:21-jdk-slim

# Set the deployment directory
WORKDIR /app

# Copy the built JAR file from the build stage into the runtime container
COPY --from=build /target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
