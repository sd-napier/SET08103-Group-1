FROM amazoncorretto:17
COPY ./target/App.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "App.jar"]