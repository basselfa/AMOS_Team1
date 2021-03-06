package com.amos.p1.backend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Location {
    private String latitude;
    private String longitude;

    public Location(){

    }

    public Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(double latitude, double longitude){
        this.latitude = String.valueOf(latitude);
        this.longitude = String.valueOf(longitude);
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonIgnore
    public double getLatitudeAsFloat(){
        return Double.parseDouble(latitude);
    }

    @JsonIgnore
    public double getLongitudeAsFloat(){
        return Double.parseDouble(longitude);
    }
}
