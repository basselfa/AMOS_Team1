package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizer;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerDummyBerlinSmall;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AggregatorDirectlyFromProvider implements Aggregator {

    @Override
    public List<Incident> getIncidents(String city, Optional<LocalDateTime> timestamp, Optional<List<String>> types) {
        ProviderNormalizer providerNormalizer = new ProviderNormalizerDummyBerlinSmall();
        List<Incident> incidents = providerNormalizer
                .parseCurrentRequest()
                .get(0).getIncidents();

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
       // todo: make Strems! return types.stream().anyMatch(type-> type.equals(incident.getType()));
        for (String type : types) {
            if(incident.getType().equals(type)) {
                return true;
            }
        }

        return false;
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
    public List<EvaluationCandidate> getEvaluationCandidate(String city, LocalDateTime timestamp) {
        throw new IllegalStateException("needs to be implemented");
    }

    @Override
    public List<ComparisonEvaluationDTO> getComparisonEvaluationOverTime(String city) {
        throw new IllegalStateException("needs to be implemented");
    }
}
