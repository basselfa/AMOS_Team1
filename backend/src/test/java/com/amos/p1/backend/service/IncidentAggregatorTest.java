package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class IncidentAggregatorTest {

    IncidentAggregator incidentAggregator;

    public IncidentAggregatorTest(){
        incidentAggregator = new IncidentAggregatorDummy();
    }

    @Test
    void testGetIncidentsFromCity(){
        List<Incident> incidentList = incidentAggregator.getFromCity("Berlin");
        for (Incident i :incidentList) {
            System.out.println(i);
        }
        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testGetAllIncidents(){
        List<Incident> incidentList = incidentAggregator.getAllData();
        for (Incident i :incidentList) {
            System.out.println(i);
        }
        assertThat(incidentList, hasSize(greaterThan(0)));
    }
}
