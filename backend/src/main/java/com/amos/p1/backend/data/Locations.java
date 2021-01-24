package com.amos.p1.backend.data;

import java.util.*;

public class Locations {


    private List<Location> locationsList = new ArrayList<>() ;

    public Locations(){

    }

    public Locations(String edges) {

        if(edges.equals("")){
            locationsList = new ArrayList<>();
            return;
        }

        try {
            String[] edgesSplit = edges.split(",");

            for (String Location : edgesSplit) {
            for (String location : edgesSplit) {

                String[] edgeSplitToParts = location.split(":");
                locationsList.add(new Location(edgeSplitToParts[0], edgeSplitToParts[1]));
            }
        }catch (Exception ex){
            throw new IllegalStateException("Cant convert edges: " + edges);
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
