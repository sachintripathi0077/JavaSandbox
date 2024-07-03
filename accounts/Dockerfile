# Start with base image with Java runtime
FROM openjdk:17-jdk-slim

# Information around who maintains the image
MAINTAINER MicroBank.com

# Add the application's jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]