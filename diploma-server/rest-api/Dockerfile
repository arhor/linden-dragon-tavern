FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine

MAINTAINER Maksim Buryshynets

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=build/libs/*.jar
ARG JAVA_OPTS=""

COPY ${JAR_FILE} diploma-app.jar

EXPOSE 8080:8080

ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:MaxRAM=100m", "-Xss512k", "-Dspring.profiles.active=default", "-jar","/diploma-app.jar"]