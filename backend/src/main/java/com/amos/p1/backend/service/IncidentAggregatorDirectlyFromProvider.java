package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;

import java.time.LocalDateTime;
import java.util.List;

public class IncidentAggregatorDirectlyFromProvider implements IncidentAggregator {
    @Override
    public List<Incident> getFromCity(String city) {

        ProviderIntervalRequest providerIntervalRequest = new ProviderIntervalRequest();
        return providerIntervalRequest.getRecentTomTomIncidentsFromCity("Berlin");
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
}
