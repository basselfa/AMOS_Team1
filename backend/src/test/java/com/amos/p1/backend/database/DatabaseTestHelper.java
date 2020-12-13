package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;

import java.util.ArrayList;
import java.util.List;

public class DatabaseTestHelper {

    public static void insertIncident(Incident incident){
        List<Incident> incidents = new ArrayList<>();
        incidents.add(incident);
        MyRepo.insertIncident(incidents);
    }

    public static Incident getIncidentById(long id){
        return getIncidentListById(id).get(0);
    }

    public static List<Incident> getIncidentListById(long id){
        return MyRepo.getEntityManager().createNamedQuery("getFromids", Incident.class)
                .setParameter("id", id)
                .getResultList();
    }
}
