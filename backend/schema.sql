drop table if exists Incident;

drop TABLE if exists Request ;


CREATE TABLE Incident (
                          id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                          trafficId VARCHAR(255) NOT NULL,
                          type VARCHAR(255) NOT NULL,
                          size VARCHAR(255) NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          city VARCHAR(255) NOT NULL,
                          country VARCHAR(255) NOT NULL,
                          lengthInMeter  DOUBLE NOT NULL,
                          startPositionLatitude VARCHAR(255) NOT NULL,
                          startPositionLongitude VARCHAR(255) NOT NULL,
                          startPositionStreet VARCHAR(255) NOT NULL,
                          endPositionLatitude VARCHAR(255) NOT NULL,
                          endPositionLongitude VARCHAR(255) NOT NULL,
                          endPositionStreet VARCHAR(255) NOT NULL,
                          verified INT NOT NULL,
                          provider  VARCHAR(255) NOT NULL,
                          entryTime datetime NOT NULL,
                          endTime datetime NOT NULL,
                          edges VARCHAR(255) NOT NULL

) ;


CREATE TABLE Request (
                         id BIGINT  AUTO_INCREMENT  PRIMARY KEY,
                         incidentsId VARCHAR(255) NOT NULL,
                         requestTime datetime NOT NULL
) ;
