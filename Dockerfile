# Use an official Maven image to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom and source code
COPY . .

# Build the application
RUN mvn clean package

# Use lightweight JDK image to run the app
FROM eclipse-temurin:17-jdk-alpine

# Copy the built jar file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app uses
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
