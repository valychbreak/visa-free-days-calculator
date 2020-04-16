# Visa-Free days calculator

## Description

Simple application for calculating visa-free days when planning trips.

## Run application

### Build
 - Configure data source properties 
 - Run from root folder:

```./gradlew assembleServerAndClient```

### Startup
 - Apply DB migrations (change db properties):
 
 ```../gradlew flywayInfo -Dflyway.user=visa_calc_user -Dflyway.password=test12 -Dflyway.url=jdbc:postgresql://localhost:5432/visa-calculator-db-test```
 - Run from root folder (replace * with built JAR version):

```java -jar backend/build/libs/backend-*-all.jar```