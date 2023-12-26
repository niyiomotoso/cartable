# Use the official OpenJDK 17 image as the base image
FROM amazoncorretto:17.0.7-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the entire application directory to the container
COPY . .
# Expose the port that the Spring Boot app listens on
EXPOSE 8500
# Build the Spring Boot app using Maven
CMD ["./mvnw", "spring-boot:run"]
