
# Traffic data extraction, aggregation and visualization

Aggregates traffic data from different APIs, normalizes it and visualizes it in the frontend onto a map.

Frontend and Backend communicating over REST-Endpoints.

## Frontend
Loads the data from the Backend via the REST-API and visualizes it in a map.
Also other forms of data visualization and manipulation will be included.

[Vue.js](https://vuejs.org/) Frontend using the design framework [Vuetify](https://vuex.vuejs.org/) and [Vuex](https://vuex.vuejs.org/) as the state managment pattern.

For the visualization of the traffic data on a map, we use OpenStreetMap and the JavaScript library Leaflet.
## Backend 
Backend loads the data from 3 different APIs normalizes it and saves it in the database. 

Java Backend using [Maven](https://maven.apache.org/) as an automated build tool and Dependency Manager. 

[Spring Boot](https://spring.io/projects/spring-boot) for setting up the REST-Endpoints which are defined by using the [OpenAPIs Swagger Editor](https://swagger.io/specification/).

[JPA](https://de.wikipedia.org/wiki/Java_Persistence_API) is used for easy storing and reading the database.

Database is relational. e.g. MySQL or PostgreSQL.

## Acknowledgments
Prof. Dr. Dirk Riehle, Theo Vassiliou-Gioles, André Anders

## License
[MIT](https://github.com/basselfa/AMOS_Team1/blob/main/LICENSE)