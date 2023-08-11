# Use Ubuntu as the base image
FROM ubuntu:latest

# Update the package index and install necessary tools
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk-headless && \
    apt-get clean

# Set environment variables for Java
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV PATH $PATH:$JAVA_HOME/bin

# Copy the JAR file into the container
COPY ./target/boots-0.0.1-SNAPSHOT.jar /app/boot.jar

# Set the working directory
WORKDIR /app

EXPOSE 4001

# Command to run the JAR file
CMD ["java", "-jar", "/app/boot.jar"]

