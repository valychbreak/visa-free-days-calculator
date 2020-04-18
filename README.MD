# Visa-Free days calculator

## Description

Simple application for calculating visa-free days when planning trips.

## Run application

### Build
 - Configure data source properties in `application.yml`
 - Run from root folder:

```./gradlew assembleServerAndClient```

### Startup
 - (Optional) Configure database:
    - Create docker images volumes folders:
    `mkdir -p $HOME/.docker-image-data/postgresql/data`
    `mkdir -p $HOME/.docker-image-data/pgadmin`
    - Start up docker postgres images:
    ```docker stack deploy -c backend/db-docker-stack.yml postgres```
    - Create a new user specified in properties (e.g. `visa_calc_user`)
    - Create a new database specified in properties (e.g. `visa-calculator-db-test`)
 - Apply DB migrations (change db properties):
 
 ```../gradlew flywayInfo -Dflyway.user=visa_calc_user -Dflyway.password=test12 -Dflyway.url=jdbc:postgresql://localhost:5432/visa-calculator-db-test```
 - Run from root folder (replace * with built JAR version):

```java -jar backend/build/libs/backend-*-all.jar```