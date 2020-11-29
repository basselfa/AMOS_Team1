package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;

import java.time.LocalDateTime;
import java.util.List;

public interface IncidentAggregator {

    List<Incident> getFromCity(String city);
    List<Incident> getDataFromTime(LocalDateTime entryTime);
    
}
