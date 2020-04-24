FROM openjdk:11

MAINTAINER Maksim Buryshynets <arhor18@gmail.com>

ADD ./build/libs/diploma-config-server-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/diploma-config-server-0.0.1-SNAPSHOT.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8888/actuator/health || exit 1

EXPOSE 8888