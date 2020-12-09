package com.amos.p1.backend.data;

import java.util.*;

public class Locations {


    private List<Location> locationsList = new ArrayList<>() ;

    public Locations(){

    }

    public Locations(String edges) {
        String[] edgesSplit = edges.split(",");

        for (String Location : edgesSplit) {
            String[] edgeSplitToParts = Location.split(":");
            locationsList.add(new Location(edgeSplitToParts[0],edgeSplitToParts[1]));
        }
    }

    public void addLocation(Location location){
        locationsList.add(location);
    }
    public List<Location> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(List<Location> locationsList) {
        this.locationsList = locationsList;
    }

}