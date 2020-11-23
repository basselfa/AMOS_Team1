package com.amos.p1.backend.demo;

import java.util.ArrayList;
import java.util.List;

public class Incidents {

    private List<Incident> incidents = new ArrayList<>();

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void addIncidents(Incident incident){
        incidents.add(incident);
    }

    @Override
    public String toString() {
        return "Incidents{" +
                "incidents=" + incidents +
                '}';
    }
}
