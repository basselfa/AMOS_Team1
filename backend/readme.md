# Backend
The backend use Spring boot as an application Framework. REST-Endpoints will be expoded to communicate with the service.

# Prerequisite
- JDK 8
- Maven

# Setup
1. Download the repo
2. run ```mvn clean compile```

# Start Application
Run ```mvn spring-boot:run```

# Tests
There need to be a separate MySql database running locally for testing. The database need to have following configuration:
- running on localhost:3306
- mysql root password: root
- mysql password: root
- mysql database name: testdb3

If you use docker and docker-compose you can start the pre-config test database by
```docker-compose -f testdatabase-compose.yml up```. After testing use  ```docker-compose -f testdatabase-compose.yml up```
to tear down the database.

# How things work:
- Marshalling: https://github.com/basselfa/AMOS_Team1/blob/main/backend/src/test/java/com/amos/p1/backend/data/IncidentMarshallingTest.java

# Developer Information
Read the test cases /src/test/java/com/amos/p1/backend to understand the application.

# Additonal links
https://docs.google.com/presentation/d/1xn0D3tKBOOIgonkMIg4ATI6QnPh65W5Go9ZCJoBH6xs/edit?usp=sharing
