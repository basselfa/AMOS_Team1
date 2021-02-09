# Backend
The backend use Spring Boot as an application Framework. 
REST-Endpoints will be exposed to communicate with the service.

Endpoints are documented by swagger-ui: http://localhost:8082/swagger-ui

## Profiles
In this project two spring profiles are used. 

The ```default``` will read dummy data from the provider. This helps to keep the request to the provider
to a minimum.

The ```production``` will read real data from the providers.

# Prerequisite
- JDK 8
- docker
- docker-compose  
- Maven


# Start Application
1. Start database with: ```docker-compose -f testdatabase-compose.yml up```. If you don't want to use docker-compose
  see Database section to get properties of the database to create manually a database
1. Start backend with:
    - Development environment: ```mvn spring-boot:run -Dspring-boot.run.profiles=default```
    - Production environment: ```mvn spring-boot:run -Dspring-boot.run.profiles=production```
1. Stop backend: ```Ctrl + C```
1. Stop database:
    - ```Ctrl + C```
    - ```docker-compose -f testdatabase-compose.yml down```

# Database
- Is a MySql Database
- running on ```localhost:3306```
- mysql root password: ```root```
- mysql password: ```root```
- mysql database name: ```testdb3```

# Docker
To create a docker image from the backend use this steps. ```production``` profile will be used:
1. Create .jar file from backend: ```mvn -B package -Dmaven.test.skip=true```
2. Create docker image from backend: ```docker build -t amostraffic/backend:<your tag>```