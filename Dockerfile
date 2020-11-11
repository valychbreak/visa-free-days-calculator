## Stage 1 : NPM build produces built UI application
FROM node:13.12.0-alpine as build-frontend

WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH

COPY frontend/package.json ./
COPY frontend/package-lock.json ./
RUN npm ci
RUN npm install react-scripts@3.4.1 -g --silent

COPY ./frontend .

RUN npm run build


## Stage 2 : Gradle build produces the application executable jar with copied UI assets
FROM gradle:6.7.0-jdk11 as backend-build

COPY --chown=gradle:gradle ./backend /home/gradle/app-src
WORKDIR /home/gradle/app-src
COPY --from=build-frontend /app/build /home/gradle/app-src/src/main/resources/public

RUN gradle build


## Stage 3 : Final image with JRE and application executable jar
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
COPY ./backend/entrypoint.sh entrypoint.sh

EXPOSE 8443

# Run application using executable jar application
ENTRYPOINT ["sh", "entrypoint.sh", "backend.jar"]