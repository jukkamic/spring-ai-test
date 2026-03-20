FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the jar we just built locally
COPY target/*.jar app.jar

# Expose the exotic port we configured
EXPOSE 9443

# Start the application
ENTRYPOINT ["java", "-jar", "springai-test-0.0.1-SNAPSHOT.jar"]