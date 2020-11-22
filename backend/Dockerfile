## Stage 1 : Gradle build produces the application executable jar with copied UI assets
FROM gradle:6.7.0-jdk11 as backend-build

COPY --chown=gradle:gradle . /home/gradle/app-src
WORKDIR /home/gradle/app-src

RUN gradle clean build -x test


## Stage 2 : Final image with JRE and application executable jar
FROM adoptopenjdk/openjdk11:alpine-jre

# Add curl for heath-check
RUN apk --no-cache add curl

# DB details override variables
ENV jdbc_url=""
ENV jdbc_username=""
ENV jdbc_password=""
ENV jdbc_driver org.postgresql.Driver

# Copy JAR into container image
COPY --from=backend-build /home/gradle/app-src/build/libs/backend-*-all.jar backend.jar
COPY ./entrypoint.sh entrypoint.sh

EXPOSE 80

# Run application using executable jar application
ENTRYPOINT ["sh", "entrypoint.sh", "backend.jar"]
