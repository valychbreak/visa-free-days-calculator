# Visa-Free days calculator

## Description

Simple application for calculating visa-free days when planning trips.

# Run application

## Setup configuration

 - Copy `resources/application.yml.example` to `resources/application.yml`
 - Change `datasources:` properties (e.g. url, username, password, etc). You might need to configure your own database locally. 

## Build

### Backend
#### Docker image (recommended)

 - Run from `backend/` folder:
 
```docker build -t <tag> .```

#### Build for local run

 - Run from `backend/` folder:

```./gradlew build -x test```

### Frontend

 - Run from `frontend/` folder:
 
 ```npm install && npm run build```
 
Also, you can build Docker image. From `frontend/` folder:

```docker build -t <tag> .``` 

_Note: you can adjust API url if needed in `frontend/.env.development` file_

## Startup
 - (Optional) Configure database:
    - Create docker images volumes folders:
    `mkdir -p $HOME/.docker-image-data/postgresql/data`
    `mkdir -p $HOME/.docker-image-data/pgadmin`
    - Start up docker postgres images:
    ```docker stack deploy -c backend/db-docker-stack.yml postgres```
    - Goto http://localhost:3333/. Login with pgadmin4@pgadmin.org and admin
    - Add a new server:
        - Host: host.docker.internal
        - Port: 5432
        - Username: postgres
        - Password: postgres
    - Create a new user specified in properties (e.g. `visa_calc_user`)
    - Create a new database specified in properties (e.g. `visa-calculator-db-test`)
 - Apply DB migrations (change db properties):
 
 ```../gradlew flywayClean flywayMigrate -Dflyway.user=visa_calc_user -Dflyway.password=test12 -Dflyway.url=jdbc:postgresql://localhost:5432/visa-calculator-db-test```
 - Run frontend application:
    - From `frontend/` folder run: ```npm start```
    - Or you can run built docker image:
    
 ```docker run -p 80:80 <image_tag>```

### Run backend with docker

```docker run -p 8080:80 <image_tag>```

Optionally: you can pass database details to docker image with env variables:

```
-e jdbc_url=""
-e jdbc_username=""
-e jdbc_password=""
-e jdbc_driver=""
```
 
### Run backend without docker

 - Run `Application` class from IDE. Or run from root folder (replace * with built JAR version):

```java -jar backend/build/libs/backend-*-all.jar```

_Note: you can modify port in `micronaut.server.port` in `backend/src/main/resources/application.yml` file_
