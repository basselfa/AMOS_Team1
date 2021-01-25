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

    //https://stackoverflow.com/questions/2839533/adding-distance-to-a-gps-coordinate
    public CityBoundingBox(CityInformation cityInformation) {

        final double centreLatitude = Double.parseDouble(cityInformation.getCentreLatitude());
        final double centreLongitude = Double.parseDouble(cityInformation.getCentreLongitude());
        final double distance = cityInformation.getSearchRadiusInMeter();

        final double latRadian = Math.toRadians(centreLatitude);

        final double degLatKm = 110.574235;
        final double degLongKm = 110.572833 * Math.cos(latRadian);
        final double deltaLat = distance / 1000.0 / degLatKm;
        final double deltaLong = distance / 1000.0 / degLongKm;

        final double minLat = centreLatitude - deltaLat;
        final double minLong = centreLongitude - deltaLong;
        final double maxLat = centreLatitude + deltaLat;
        final double maxLong = centreLongitude + deltaLong;

        city = cityInformation.getCityName();
        minCorner = new Location(minLat, minLong);
        maxCorner = new Location(maxLat, maxLong);
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
        double latMin = Double.parseDouble(minCorner.getLatitude());
        double longMin = Double.parseDouble(minCorner.getLongitude());
        double latMax = Double.parseDouble(maxCorner.getLatitude());
        double longMax = Double.parseDouble(maxCorner.getLongitude());

        return new Location(
                (latMin + (latMax - latMin) / 2) + "",
                (longMin + (longMax - longMin) / 2) + ""
        );
    }
}
