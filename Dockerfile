# syntax=docker/dockerfile:1

########### 1) Build stage ###########
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

# Copy only what's needed for caching
COPY PopReports/pom.xml ./PopReports/pom.xml
# Pull deps for the PopReports project (no reactor needed)
RUN mvn -B -DskipTests -f PopReports/pom.xml dependency:go-offline

# Now copy sources and build
COPY PopReports/src ./PopReports/src
RUN mvn -B -DskipTests -f PopReports/pom.xml package

########### 2) Runtime stage ###########
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the shaded jar built by the module
COPY --from=build /build/PopReports/target/*-shaded.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
