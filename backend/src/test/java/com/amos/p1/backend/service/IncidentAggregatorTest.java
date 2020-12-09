package com.amos.p1.backend.service;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class IncidentAggregatorTest {

    IncidentAggregator incidentAggregator;

    public IncidentAggregatorTest(){
        incidentAggregator = new IncidentAggregatorDirectlyFromProvider();
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

    @Test
    void testMarshallingOneIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = incidentAggregator.getFromCity("Berlin");

        String json = objectMapper.writeValueAsString(berlinIncidents.get(0));

        System.out.println(Helper.getPrettyJson(json));

    }

    @Test
    void testMarshallingAllIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = incidentAggregator.getFromCity("Berlin");

        String json = objectMapper.writeValueAsString(berlinIncidents);

        System.out.println(Helper.getPrettyJsonList(json));

    }
}
