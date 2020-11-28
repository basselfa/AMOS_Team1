package com.amos.p1.backend.service;


import com.amos.p1.backend.data.Incident;

import java.util.List;

public interface IncidentAggregator {

    List<Incident> getFromCity(String city);
}
