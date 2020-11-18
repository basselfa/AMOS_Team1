
# Traffic data extraction, aggregation and visualization

Aggregates traffic data from different APIs, normalizes it and visualizes it in the frontend onto a map.

Frontend and Backend communicating over REST-Endpoints.

## Frontend
Loads the data from the Backend via the REST-API and visualizes it in a map.
Also other forms of data visualization and manipulation will be included.

[Vue.js](https://vuejs.org/) Frontend with [Vuex](https://vuex.vuejs.org/) as the state managment pattern.

Yet to be decided if openstreetmap is used for data visualization.
Should be evident which source the visualized data is from.



## Backend 
Backend loads the data from 3 different APIs normalizes it and saves it in the database. 

Java Backend using [Maven](https://maven.apache.org/) as an automated build tool and Dependency Manager. 

[Spring Boot](https://spring.io/projects/spring-boot) for setting up the REST-Endpoints which are defined by using the [OpenAPIs Swagger Editor](https://swagger.io/specification/).

[JPA](https://de.wikipedia.org/wiki/Java_Persistence_API) is used for easy storing and reading the database.

Database is relational. e.g. MySQL or PostgreSQL.
