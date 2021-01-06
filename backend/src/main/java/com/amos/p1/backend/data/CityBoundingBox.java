package com.amos.p1.backend.data;

public class CityBoundingBox {

    private String city;
    private Location minCorner; // unten links
    private Location maxCorner; // oben rechts

    public CityBoundingBox() {

    }

    public CityBoundingBox(String city, Location minCorner, Location maxCorner) {
        this.city = city;
        this.minCorner = minCorner;
        this.maxCorner = maxCorner;
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

    public Location getCenterPoint() {
        float latMin = Float.parseFloat(minCorner.getLatitude());
        float longMin = Float.parseFloat(minCorner.getLongitude());
        float latMax = Float.parseFloat(maxCorner.getLatitude());
        float longMax = Float.parseFloat(maxCorner.getLongitude());

        return new Location(
                (latMin + (latMax - latMin) / 2) + "",
                (longMin + (longMax - longMin) / 2) + ""
        );
    }
}
