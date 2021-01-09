drop table if exists Incident;

drop TABLE if exists Request ;

drop TABLE if exists  EvaluationCandidate;


CREATE TABLE Incident (
                          id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          trafficId VARCHAR(255) ,
                          type VARCHAR(255) ,
                          size VARCHAR(255) ,
                          description VARCHAR(255) ,
                          city VARCHAR(255) ,
                          country VARCHAR(255) ,
                          startPositionLatitude VARCHAR(255) ,
                          startPositionLongitude VARCHAR(255) ,
                          startPositionStreet VARCHAR(255) ,
                          endPositionLatitude VARCHAR(255) ,
                          endPositionLongitude VARCHAR(255) ,
                          endPositionStreet VARCHAR(255)  ,
                          verified INT  ,
                          provider  VARCHAR(255)  ,
                          entryTime datetime  ,
                          endTime datetime  ,
                          edges VARCHAR(11255),
                          lengthInMeter DOUBLE,
                          requestId BIGINT UNSIGNED

) ;


CREATE TABLE Request (
                         id BIGINT  AUTO_INCREMENT  PRIMARY KEY,
                         requestTime datetime,
                         cityName VARCHAR(11255)

) ;

CREATE TABLE EvaluationCandidate (
                         id BIGINT  AUTO_INCREMENT  PRIMARY KEY,
                         tomTomIncidentId BIGINT   UNSIGNED,
                         hereIncidentId BIGINT   UNSIGNED,
                         requestId BIGINT UNSIGNED,
                         confidenceDescription VARCHAR(11255)

) ;
