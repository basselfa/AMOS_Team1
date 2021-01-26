package com.amos.p1.backend.data;

import javax.persistence.*;
import java.util.Objects;
@NamedQuery(
        name = "getAllCityInformation",
        query = "SELECT c FROM CityInformation c "
)
@NamedQuery(
        name = "getCityInformationFromCityName",
        query = "SELECT c FROM CityInformation c WHERE c.cityName= :cityName"
)
@NamedQuery(
        name = "getCityInformationFromId",
                query = "SELECT c FROM CityInformation c WHERE c.id= :id"
                )
@Entity
public class CityInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String cityName;
    private String centreLatitude;
    private String centreLongitude;
    private int searchRadiusInMeter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cityName", nullable = true)
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Basic
    @Column(name = "centreLatitude", nullable = true)
    public String getCentreLatitude() {
        return centreLatitude;
    }
    public void setCentreLatitude(String centreLatitude) {
        this.centreLatitude = centreLatitude;
    }

    @Basic
    @Column(name = "centreLongitude", nullable = true)
    public String getCentreLongitude() {
        return centreLongitude;
    }
    public void setCentreLongitude(String centreLongitude) {
        this.centreLongitude = centreLongitude;
    }

    @Basic
    @Column(name = "searchRadiusInMeter", nullable = true)
    public int getSearchRadiusInMeter() {
        return searchRadiusInMeter;
    }
    public void setSearchRadiusInMeter(int searchRadiusInMeter) {
        this.searchRadiusInMeter = searchRadiusInMeter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityInformation that = (CityInformation) o;
        return id == that.id && Objects.equals(cityName, that.cityName) && Objects.equals(centreLatitude, that.centreLatitude) && Objects.equals(centreLongitude, that.centreLongitude) && Objects.equals(searchRadiusInMeter, that.searchRadiusInMeter);
    }

    @Override
    public String toString() {
        return "CityInformation{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", centreLatitude='" + centreLatitude + '\'' +
                ", centreLongitude='" + centreLongitude + '\'' +
                ", searchRadiusInMeter=" + searchRadiusInMeter +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cityName, centreLatitude, centreLongitude, searchRadiusInMeter);
    }
}
