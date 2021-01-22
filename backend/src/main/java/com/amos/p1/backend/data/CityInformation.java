package com.amos.p1.backend.data;

public class CityInformation {

    private long id;
    private String cityName;
    private String centreLatitude;
    private String centreLongitude;
    private String searchRadiusInMeter;

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

    public String getSearchRadiusInMeter() {
        return searchRadiusInMeter;
    }

    public void setSearchRadiusInMeter(String searchRadiusInMeter) {
        this.searchRadiusInMeter = searchRadiusInMeter;
    }
}
