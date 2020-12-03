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
