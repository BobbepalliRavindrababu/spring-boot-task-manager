
# ==========================================
# Stage 1: Build the Spring Boot application
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy Maven project configuration
COPY pom.xml .

# Download dependencies
RUN mvn -B dependency:go-offline

# Copy application source code
COPY src ./src

# Compile and package the application
RUN mvn -B clean package -DskipTests


# ==========================================
# Stage 2: Run the application
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy generated JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Application port
EXPOSE 9090

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
