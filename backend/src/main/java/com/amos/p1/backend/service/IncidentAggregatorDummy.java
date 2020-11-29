package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import com.amos.p1.backend.provider.ProviderRequest;
import com.amos.p1.backend.provider.TomTomRequestDummy;

import java.util.List;

public class IncidentAggregatorDummy implements IncidentAggregator {
    @Override
    public List<Incident> getFromCity(String city) {

        return (List<Incident>) MyRepo.getEntityManager().createNamedQuery("getFromCity")
                .setParameter("city" ,city )
                .getResultList();
    }
}