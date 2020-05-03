FROM adoptopenjdk/openjdk14-openj9:jdk-14.0.1_7_openj9-0.20.0-alpine-slim

RUN apk --no-cache add curl

ENV jdbc_url=""
ENV jdbc_username=""
ENV jdbc_password=""
ENV jdbc_driver org.postgresql.Driver

COPY build/libs/backend-*-all.jar backend.jar
COPY entrypoint.sh entrypoint.sh
EXPOSE 8443

ENTRYPOINT ["sh", "entrypoint.sh", "backend.jar"]
