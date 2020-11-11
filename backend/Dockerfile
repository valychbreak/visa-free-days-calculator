## Stage 1 : Gradle build produces the application executable jar
FROM gradle:6.7.0-jdk11 as builder

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build


## Stage 2 : Final image with JRE and application executable jar
FROM adoptopenjdk/openjdk11:alpine-jre

RUN apk --no-cache add curl

ENV jdbc_url=""
ENV jdbc_username=""
ENV jdbc_password=""
ENV jdbc_driver org.postgresql.Driver

# Copy JAR into container image
COPY --from=builder /home/gradle/src/build/libs/backend-*-all.jar backend.jar
COPY entrypoint.sh entrypoint.sh

EXPOSE 8443

# Run application using executable jar application
ENTRYPOINT ["sh", "entrypoint.sh", "backend.jar"]
