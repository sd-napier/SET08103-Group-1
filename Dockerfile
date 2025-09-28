# syntax=docker/dockerfile:1

########### 1) Build stage ###########
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

# Copy POMs first for cache-friendly deps
COPY pom.xml ./pom.xml
COPY PopReports/pom.xml ./PopReports/pom.xml
RUN mvn -B -DskipTests -pl PopReports -am dependency:go-offline

# Now add sources and build just the module
COPY PopReports/src ./PopReports/src
RUN mvn -B -DskipTests -pl PopReports -am package

########### 2) Runtime stage ###########
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/PopReports/target/*-shaded.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
