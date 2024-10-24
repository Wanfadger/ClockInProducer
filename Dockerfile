
# Use the official OpenJDK 21 image as the base image
#FROM openjdk:21-jdk

# Set the working directory inside the container
#WORKDIR /app

# Copy the local project files to the container
#COPY . .

# Compile the application
#RUN ./mvn clean install

# Expose the port the app runs on (adjust as needed)
#EXPOSE 8091

# Run the application (adjust the JAR name as needed)
#CMD ["java", "-jar", "target/ClockInOutProducer.jar"]

# Use the official Maven image
FROM maven:3.8.4-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean install

#EXPOSE 8091

# Use a lighter image for the final product
#FROM openjdk:11-jre-slim
FROM openjdk:21-jdk

# Copy the built JAR file from the build stage
COPY --from=build /app/target/ClockInOutProducer.jar /usr/local/lib/ClockInOutProducer.jar

# Specify the command to run the JAR file
CMD ["java", "-jar", "/usr/local/lib/ClockInOutProducer.jar"]
