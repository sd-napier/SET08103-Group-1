FROM openjdk:latest
COPY ./target/App.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "App.jar"]