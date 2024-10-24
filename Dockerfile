# Use the official Maven image for building the application
FROM maven:3.8.4-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean install -e  # Add -e for detailed error output

# Use the official OpenJDK 21 image for running the application
FROM openjdk:21-jdk

# Copy the built JAR file from the build stage
COPY --from=build /app/target/ClockInOutProducer.jar /usr/local/lib/ClockInOutProducer.jar

# Specify the command to run the JAR file
CMD ["java", "-jar", "/usr/local/lib/ClockInOutProducer.jar"]
