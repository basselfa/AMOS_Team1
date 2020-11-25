package com.amos.p1.backend.from_client_to_backend;

import java.util.ArrayList;
import java.util.List;

public class Report {

    private final List<Incident> incidents = new ArrayList<>();

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void addIncident(int x, int y, String type){
        incidents.add(new Incident());
    }

    @Override
    public String toString() {
        return "Report{" +
                "incidents=" + incidents +
                '}';
    }
}
