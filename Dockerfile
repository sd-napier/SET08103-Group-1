# Use latest version of openJDK
FROM openjdk:latest
#Copy jar to working directory
COPY ./target/App.jar /tmp
#Set working directory
WORKDIR /tmp
#entrypoint
ENTRYPOINT ["java", "-jar", "App.jar"]