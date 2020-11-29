package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.Incident;

import java.util.List;

public class TomTomNormalization implements JsonToIncident{
    @Override
    public Incident normalizeOneIncident(String json) {
        Incident incident = new Incident();


        //Json parsed
        incident.setDescription("roadworks");

        return incident;
    }

    @Override
    public List<Incident> normalize(String json) {
        return null;
    }
}
