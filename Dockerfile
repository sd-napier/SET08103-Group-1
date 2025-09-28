# Uses the official openJDK base image to run application
FROM eclipse-temurin:17-jre
# sets the working directory inside the container to /app
WORKDIR /app
# Copies compiled JAR file from the build directory into the container
COPY PopReports/target/*-shaded.jar /app/app.jar
# defines command to start application when container runs
ENTRYPOINT ["java", "-jar", "app.jar"]