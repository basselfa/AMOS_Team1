package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationOverTimeDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.ProviderIntervalRequest;
import com.amos.p1.backend.service.ProviderNormalizer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AggregatorDirectlyFromProvider implements Aggregator {
    @Override
    public List<Incident> getFromCity(String city) {

        ProviderNormalizer providerNormalizer = new ProviderNormalizer();
        return providerNormalizer.getRecentTomTomIncidentsFromCity("Berlin");
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
       // todo: make Strems! return types.stream().anyMatch(type-> type.equals(incident.getType()));
        for (String type : types) {
            if(incident.getType().equals(type)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Incident> getFromCityAndTimeStamp(String city, LocalDateTime timestamp) {
        throw new IllegalStateException("Not yet implemented");

    }

    @Override
    public List<LocalDateTime> getTimestampsFromCity(String city) {
        throw new IllegalStateException("Not yet implemented");

    }

    @Override
    public List<String> getCities() {
        return null;
    }

    @Override
    public List<Incident> getAllData() {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public List<EvaluationCandidate> getEvaluationCandiate(String city, LocalDateTime timestamp) {
        throw new IllegalStateException("needs to be implemented");
    }

    @Override
    public ComparisonEvaluationOverTimeDTO getComparisonEvaluationOverTime() {
        throw new IllegalStateException("needs to be implemented");
    }
}
