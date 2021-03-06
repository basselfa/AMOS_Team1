drop table if exists Incident;

drop TABLE if exists Request ;

drop TABLE if exists  EvaluationCandidate;

drop TABLE if exists  CityInformation;


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
                          edges MEDIUMTEXT,
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
                         score INT,
                         hereIncidentId BIGINT   UNSIGNED,
                         requestId BIGINT UNSIGNED,
                         confidenceDescription VARCHAR(11255)

) ;



CREATE TABLE CityInformation (
                         id BIGINT  AUTO_INCREMENT  PRIMARY KEY,
                         cityName VARCHAR(255),
                         centreLatitude VARCHAR(255),
                         centreLongitude VARCHAR(255),
                         searchRadiusInMeter INT
) ;

