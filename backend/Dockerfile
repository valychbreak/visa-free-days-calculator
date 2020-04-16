FROM adoptopenjdk/openjdk13-openj9:jdk-13.0.2_8_openj9-0.18.0-alpine-slim

RUN apk --no-cache add curl

ENV jdbc_url=
ENV jdbc_username=
ENV jdbc_password=
ENV jdbc_driver org.postgresql.Driver

COPY build/libs/backend-*-all.jar backend.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-XX:+IdleTuningGcOnIdle", "-Xtune:virtualized", "-Ddatasources.default.url=${jdbc_url}", "-Ddatasources.default.username=${jdbc_username}", "-Ddatasources.default.password=${jdbc_password}", "-Ddatasources.default.driverClassName=${jdbc_driver}", "-jar", "backend.jar"]
