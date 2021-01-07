package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;

import java.time.LocalDateTime;
import java.util.List;

public interface IncidentAggregator {

    List<Incident> getFromCity(String city);

    List<Incident> getFromCityAndTypes(String city, List<String> filter);

    List<Incident> getFromCityAndTimeStamp(String city, LocalDateTime timestamp);

    List<LocalDateTime> getTimestampsFromCity(String city);

    List<String> getCities();

    List<Incident> getAllData();

}
