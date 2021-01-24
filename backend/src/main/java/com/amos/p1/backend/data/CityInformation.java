package com.amos.p1.backend.data;

import java.util.Objects;

public class CityInformation {

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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCentreLatitude() {
        return centreLatitude;
    }

    public void setCentreLatitude(String centreLatitude) {
        this.centreLatitude = centreLatitude;
    }

    public String getCentreLongitude() {
        return centreLongitude;
    }

    public void setCentreLongitude(String centreLongitude) {
        this.centreLongitude = centreLongitude;
    }

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
    public int hashCode() {
        return Objects.hash(id, cityName, centreLatitude, centreLongitude, searchRadiusInMeter);
    }
}
