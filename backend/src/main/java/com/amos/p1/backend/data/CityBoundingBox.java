package com.amos.p1.backend.data;

public class CityBoundingBox {

    private String city;
    private Location minCorner;
    private Location maxCorner;

    public CityBoundingBox(){

    }

    public CityBoundingBox(String city, Location minCorner, Location maxCorner) {
        this.city = city;
        this.minCorner = minCorner; // oben links
        this.maxCorner = maxCorner; // unten rechts
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Location getMinCorner() {
        return minCorner;
    }

    public void setMinCorner(Location minCorner) {
        this.minCorner = minCorner;
    }

    public Location getMaxCorner() {
        return maxCorner;
    }

    public void setMaxCorner(Location maxCorner) {
        this.maxCorner = maxCorner;
    }
}
