package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.database.MyRepo;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AggregatorFromDatabase implements Aggregator {

    @Override
    public List<Incident> getIncidents(String city, Optional<LocalDateTime> timestamp, Optional<List<String>> types) {

        TypedQuery<Incident> query;

        if(timestamp.isPresent()){
            query = MyRepo.getEntityManager()
                .createNamedQuery("getFromCityAndTimeStamp", Incident.class)
                .setParameter("city", city )
                .setParameter("entryTime", timestamp.get());
        }else{
            query = MyRepo.getEntityManager()
                    .createNamedQuery("getFromCity", Incident.class)
                    .setParameter("city", city);
        }

        List<Incident> incidents = query.getResultList();

        if(types.isPresent()){
            incidents = filterIncidentsByType(incidents, types.get());
        }

        return incidents;
    }

    private List<Incident> filterIncidentsByType(List<Incident> incidents, List<String> types) {
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

    public List<Incident> getFromRequestTime(LocalDateTime requestTime) {
        return (List<Incident>) MyRepo.getEntityManager().createNamedQuery("getFromRequestTime")
                .setParameter("requestTime", requestTime )
                .getResultList();
    }

    @Override
    public List<EvaluationCandidate> getEvaluationCandiate(String city, LocalDateTime timestamp) {
        throw new IllegalStateException();
    }

    public List<ComparisonEvaluationDTO> getComparisonEvaluationOverTime(){
        throw new IllegalStateException("needs to be implemented");
    }

}