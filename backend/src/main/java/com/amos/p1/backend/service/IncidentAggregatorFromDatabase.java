package com.amos.p1.backend.service;

import  com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.database.MyRepo;

// import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;

public class IncidentAggregatorFromDatabase implements IncidentAggregator {

    @Override
    @SuppressWarnings("unchecked")
    public List<Incident> getFromCity(String city) {
        List<Incident> resultList = MyRepo.getEntityManager()
                .createNamedQuery("getFromCity")
                .setParameter("city", city)
                .getResultList();
        return resultList;
    }


    @Override
    public List<LocalDateTime> getTimestampsFromCity(String city) {
        throw new IllegalStateException("Not yet implemented yet. Sprint 7");
    }

    @Override
    public List<String> getCities() {
        return null;
    }


    // to be refined with entry time - getDataFromTime()
    @Override
    public List<Incident> getAllData() {
        return (List<Incident>) MyRepo.getEntityManager()
                .createNamedQuery("getAllData")
                .getResultList();
    }

   @Override
    public List<Incident> getFromCityAndTimeStamp(String city, LocalDateTime entryTime) {
        return (List<Incident>) MyRepo.getEntityManager()
                .createNamedQuery("getFromCityAndTimeStamp")
                .setParameter("city", city )
                .setParameter("entryTime", entryTime )
                .getResultList();
    }


    /*
    @SuppressWarnings("unchecked")
    public List<Incident> getDataFromTime(LocalDateTime entryTime) {
        return (List<Incident>) MyRepo.getEntityManager().createNamedQuery("getDataFromTime")
                .setParameter("entryTime", entryTime )
                .getResultList();
    }
    */

}