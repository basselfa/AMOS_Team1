package com.amos.p1.backend.service;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class IncidentAggregatorDirectlyFromProviderTest {

    IncidentAggregator incidentAggregator = new IncidentAggregatorDirectlyFromProvider();;

    @Test
    void testGetIncidentsFromCity(){
        List<Incident> incidentList = incidentAggregator.getFromCity("Berlin");

        System.out.println(incidentList);
        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testGetIncidentsFromCityAndWithType(){
        List<String> types = new ArrayList<>();
        types.add("1");
        types.add("10");

        List<Incident> incidentList = incidentAggregator.getFromCityAndTypes("Berlin", types);

        assertThat(incidentList, hasSize(greaterThan(0)));

        incidentList.forEach(incident -> {
            assertThat(incident.getType(),
                    anyOf(equalTo("1"),
                          equalTo("10"))
            );
        });
    }

    @Test
    void testGetAllIncidents(){
        List<Incident> incidentList = incidentAggregator.getAllData();

        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testMarshallingOneIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = incidentAggregator.getFromCity("Berlin");

        String json = objectMapper.writeValueAsString(berlinIncidents.get(0));
        assertThat(json, notNullValue());

    }

    @Test
    void testMarshallingAllIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = incidentAggregator.getFromCity("Berlin");

        String json = objectMapper.writeValueAsString(berlinIncidents);
        assertThat(json, notNullValue());

    }
}
