package com.amos.p1.backend.data;

import java.util.*;

public class Locations {

    private List<Location> locationsList = null ;

    public Locations(String edges) {
        locationsList =  new <Location>ArrayList() ;
        String[] edgesSplit = edges.split(",");

        for (String Location : edgesSplit) {
            String[] edgeSplitToParts = Location.split(":");
            locationsList.add(new Location(edgesSplit[0],edgesSplit[1]));
        }
    }

    public List<Location> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(List<Location> locationsList) {
        this.locationsList = locationsList;
    }

}
