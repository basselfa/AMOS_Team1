package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.database.MyRepo;

import java.time.LocalDateTime;
import java.util.List;

public class IncidentAggregatorDummy implements IncidentAggregator {

    @Override
    @SuppressWarnings("unchecked")
    public List<Incident> getFromCity(String city) {
        return (List<Incident>) MyRepo.getEntityManager().createNamedQuery("getFromCity")
                .setParameter("city", city )
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Incident> getDataFromTime(LocalDateTime entryTime) {
        return (List<Incident>) MyRepo.getEntityManager().createNamedQuery("getDataFromTime")
                .setParameter("entryTime", entryTime )
                .getResultList();
    }
}