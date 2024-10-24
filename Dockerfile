# Use the official OpenJDK 21 image as the base image
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the local project files to the container
COPY . .

# Compile the application
#RUN ./mvnw clean install -e
#RUN ./mvnw clean install -X
RUN ./mvnw clean install -DskipTests

# Expose the port the app runs on (adjust as needed)
EXPOSE 1405

# Run the application (adjust the JAR name as needed)
CMD ["java", "-jar", "target/ClockInOutProducer.jar"]
