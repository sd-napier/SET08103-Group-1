# syntax=docker/dockerfile:1

########### 1) Build stage ###########
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

# Copy parent and module POMs first for better caching
COPY pom.xml ./pom.xml
COPY PopReports/pom.xml ./PopReports/pom.xml

# Go offline for the PopReports module (downloads deps only once)
RUN mvn -B -DskipTests -pl PopReports -am dependency:go-offline

# Now add sources and build just the module
COPY PopReports/src ./PopReports/src
RUN mvn -B -DskipTests -pl PopReports -am package

########### 2) Runtime stage ###########
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the shaded (fat) JAR that the module produces
COPY --from=build /build/PopReports/target/*-shaded.jar /app/app.jar

# EXPOSE 8080   # if your app listens on 8080, uncomment
ENTRYPOINT ["java","-jar","/app/app.jar"]
