package com.amos.p1.backend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NamedQuery(
        name = "getFromids",
        query = "SELECT i FROM Incident i WHERE i.id IN :id"
)
@NamedQuery(
        name = "getFromCity",
        query = "SELECT i FROM Incident i WHERE i.city = :city"
)
@NamedQuery(
        name = "getDataFromTime",
        query = "SELECT i FROM Incident i WHERE i.entryTime >= :entryTime"
)
@NamedQuery(
        name = "getAllData",
        query = "SELECT i FROM Incident i"
)

@NamedQuery(
        name = "getFromCityAndTimeStamp",
        query = "SELECT i FROM Incident i WHERE i.city = :city AND i.entryTime >= :entryTime"
)
@Entity
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String trafficId;
    private String type; // type of incident
    private String size;
    private String description;
    private String city;
    private String country;
    private double lengthInMeter;
    private String startPositionLatitude;
    private String startPositionLongitude;
    private String startPositionStreet;
    private String endPositionLatitude;
    private String endPositionLongitude;
    private String endPositionStreet;
    private int verified; // 0:false, 1:true

    private String provider; // 0:Here, 1:Tomtom
    // reference https://vladmihalcea.com/date-timestamp-jpa-hibernate/
    private LocalDateTime entryTime;
    private LocalDateTime endTime;

    private String edges; // 12.124234:53.536453,


    public Incident() {
        super();
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Incident(String trafficId, String type, String size, String description,
                    String city, String country, double lengthInMeter,
                    String startPositionLatitude, String startPositionLongitude,
                    String startPositionStreet, String endPositionLatitude,
                    String endPositionLongitude, String endPositionStreet,
                    int verified, String provider,
                    LocalDateTime entryTime, LocalDateTime endTime, String edges) {
        super();
        this.type = type;
        this.size = size;
        this.description = description;
        this.city = city;
        this.country = country;
        this.startPositionLatitude = startPositionLatitude;
        this.startPositionLongitude = startPositionLongitude;
        this.startPositionStreet = startPositionStreet;
        this.endPositionLatitude = endPositionLatitude;
        this.endPositionLongitude = endPositionLongitude;
        this.endPositionStreet = endPositionStreet;
        this.verified = verified;
        this.provider = provider;
        this.entryTime = entryTime;
        this.trafficId = trafficId;
        this.edges = edges;
        this.endTime = endTime;
        this.lengthInMeter = lengthInMeter;
    }

    @Basic
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "lengthInMeter", nullable = true)
    public double getLengthInMeter() {
        return lengthInMeter;
    }

    public void setLengthInMeter(double lengthInMeter) {
        this.lengthInMeter = lengthInMeter;
    }

    @Basic
    @Column(name = "trafficId", nullable = true)
    public String getTrafficId() {
        return trafficId;
    }

    public void setTrafficId(String trafficId) {
        this.trafficId = trafficId;
    }

    @Basic
    @Column(name = "entryTime", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }


    @Basic
    @Column(name = "type", nullable = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "size", nullable = true)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Basic
    @Column(name = "description", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "country", nullable = true)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "city", nullable = true)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Basic
    @Column(name = "startPositionLongitude", nullable = true)
    public String getStartPositionLongitude() {
        return startPositionLongitude;
    }

    public void setStartPositionLongitude(String startPositionLongitude) {
        this.startPositionLongitude = startPositionLongitude;
    }

    @Basic
    @Column(name = "startPositionLatitude", nullable = true)
    public String getStartPositionLatitude() {
        return startPositionLatitude;
    }

    public void setStartPositionLatitude(String startPositionLatitude) {
        this.startPositionLatitude = startPositionLatitude;
    }

    @Basic
    @Column(name = "startPositionStreet", nullable = true)
    public String getStartPositionStreet() {
        return startPositionStreet;
    }

    public void setStartPositionStreet(String startPositionStreet) {
        this.startPositionStreet = startPositionStreet;
    }

    @Basic
    @Column(name = "endPositionLatitude", nullable = true)
    public String getEndPositionLatitude() {
        return endPositionLatitude;
    }

    public void setEndPositionLatitude(String endPositionLatitude) {
        this.endPositionLatitude = endPositionLatitude;
    }

    @Basic
    @Column(name = "endPositionLongitude", nullable = true)
    public String getEndPositionLongitude() {
        return endPositionLongitude;
    }

    public void setEndPositionLongitude(String endPositionLongitude) {
        this.endPositionLongitude = endPositionLongitude;
    }

    @Basic
    @Column(name = "endPositionStreet", nullable = true)
    public String getEndPositionStreet() {
        return endPositionStreet;
    }

    public void setEndPositionStreet(String endPositionStreet) {
        this.endPositionStreet = endPositionStreet;
    }

    @Basic
    @Column(name = "verified", nullable = true)
    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }


    @Basic
    @Column(name = "provider", nullable = true)
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Basic
    @Column(name = "edges", nullable = true)
    public String getEdges() {
        return edges;
    }

    public void setEdges(String edges) {
        this.edges = edges;
    }

    public void setEdgesAsLocations(Locations locations) {
        List<Location> locationsList = locations.getLocationsList();

        if (locationsList.size() == 0) {
            edges = "";
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Location location : locationsList) {
            stringBuilder
                    .append(location.getLatitude())
                    .append(":")
                    .append(location.getLongitude())
                    .append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        edges = stringBuilder.toString();
        //TODO Locations as edges string
    }

    public Locations getEdgesAsLocations() {
        return new Locations(edges);
    }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", trafficId='" + trafficId + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", lengthInMeter=" + lengthInMeter +
                ", startPositionLatitude='" + startPositionLatitude + '\'' +
                ", startPositionLongitude='" + startPositionLongitude + '\'' +
                ", startPositionStreet='" + startPositionStreet + '\'' +
                ", endPositionLatitude='" + endPositionLatitude + '\'' +
                ", endPositionLongitude='" + endPositionLongitude + '\'' +
                ", endPositionStreet='" + endPositionStreet + '\'' +
                ", verified=" + verified +
                ", provider='" + provider + '\'' +
                ", entryTime=" + entryTime +
                ", edges='" + edges + '\'' +
                '}';
    }

    public enum IncidentTypes {    //do not change order!
        ACCIDENT,
        CONGESTION,
        DISABLEDVEHICLE,
        ROADHAZARD,
        ROADWORKS,
        PLANNEDEVENT,
        DETOUR,
        MISC,       // TODO: MISC FOR "UNKNOWN" OR "NO CATEGORY"
        WEATHER,
        ROADCLOSURE,
        LANERESTRICTION;

        public int getId() {
            return ordinal();
        }
    }
}