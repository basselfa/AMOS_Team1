package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.database.MyRepo;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<Incident> getFromCityAndTypes(String city, List<String> types) {
        List<Incident> incidents = getFromCity(city);

        List<Incident> filteredIncidents = new ArrayList<>();
        for (Incident incident : incidents) {
            if(hasIncidentOneOfThisTypes(incident, types)){
                filteredIncidents.add(incident);
            }
        }

        return filteredIncidents;
    }

    private boolean hasIncidentOneOfThisTypes(Incident incident, List<String> types){
        for (String type : types) {
            if(incident.getType().equals(type)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public List<LocalDateTime> getTimestampsFromCity(String city) {
        List<LocalDateTime> timestampList = (List<LocalDateTime>) MyRepo.getEntityManager()
                .createNamedQuery("getTimestampsFromCity")
                .setParameter("city", city )
                .getResultList();

        return timestampList;
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



    public List<Incident> getFromRequestTime(LocalDateTime requestTime) {
        return (List<Incident>) MyRepo.getEntityManager().createNamedQuery("getFromRequestTime")
                .setParameter("requestTime", requestTime )
                .getResultList();
    }


}