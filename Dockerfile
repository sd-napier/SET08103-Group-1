# Uses the official openJDK base image to run application
FROM openjdk
# Copies compiled JAR file from the build directory into the container
COPY ./PopReports/target/population-reports-0.1.0.jar ./app/app.jar
# sets the working directory inside the container to /app
WORKDIR /app
# defines command to start application when container runs
ENTRYPOINT ["java", "-jar", "app.jar"]