# Uses the official openJDK base image to run application
FROM openjdk:17-jre-slim
# Copies compiled JAR file from the build directory into the container
COPY PopReports/target/*-shaded.jar /app/app.jar
# sets the working directory inside the container to /app
WORKDIR /app
# defines command to start application when container runs
ENTRYPOINT ["java", "-jar", "app.jar"]