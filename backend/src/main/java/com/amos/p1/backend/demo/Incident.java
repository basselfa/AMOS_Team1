package com.amos.p1.backend.demo;

import java.util.ArrayList;
import java.util.List;

public class Incident {

    private int providerId;
    private List<Location> shape = new ArrayList<>();
    private String description;


    public Incident(int providerId, String description) {
        this.providerId = providerId;
        this.description = description;
    }

    public int getProviderId() {
        return providerId;
    }

    public List<Location> getShape() {
        return shape;
    }

    public String getDescription() {
        return description;
    }

    public void addShape(Location location){
        shape.add(location);
    }

    @Override
    public String toString() {
        return "Incident{" +
                "providerId=" + providerId +
                ", shape=" + shape +
                ", description='" + description + '\'' +
                '}';
    }
}
