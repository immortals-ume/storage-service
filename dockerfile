# Use an official openjdk runtime as the base image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file and dependencies into the container
COPY target/storage-service.jar /app/
COPY target/dependency/* /app/dependency/

# Expose the port that the app will run on
EXPOSE 8087

# Run the app when the container starts
CMD ["java", "-jar", "storage-service.jar"]