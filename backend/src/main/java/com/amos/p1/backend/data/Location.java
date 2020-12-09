package com.amos.p1.backend.data;

public class Location {
    private String latitude;
    private String longitude;

    public Location(){

    }

    public Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        longitude = longitude;
    }

    public void setLatitude(String latitude) {
        latitude = latitude;
    }

}
