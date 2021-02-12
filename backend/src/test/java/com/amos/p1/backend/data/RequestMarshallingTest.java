package com.amos.p1.backend.data;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.database.DatabaseTestHelper;
import com.amos.p1.backend.database.DummyIncident;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class RequestMarshallingTest {

    private static final Logger log = LoggerFactory.getLogger(RequestMarshallingTest.class);

    private String json;

    public RequestMarshallingTest(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Request value = createRequest();
            json = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Request createRequest() {
        Incident incident = DummyIncident.createIncident();
        List<Incident> incidents = new ArrayList<>();
        incidents.add(incident);

        Request request = new Request();
        request.setRequestTime(LocalDateTime.of(2020, 10, 30, 16, 30));
        request.setIncidents(incidents);

        return request;
    }

    @Test
    void printJson() {
        log.info(Helper.getPrettyJson(json));
    }

    @Test
    void testLocalTime(){
        assertThat(json, hasJsonPath("$.requestTime.dayOfMonth", equalTo(30)));
        assertThat(json, hasJsonPath("$.requestTime.monthValue", equalTo(10)));
        assertThat(json, hasJsonPath("$.requestTime.year", equalTo(2020)));

        assertThat(json, hasJsonPath("$.requestTime.hour", equalTo(16)));
        assertThat(json, hasJsonPath("$.requestTime.minute", equalTo(30)));
        assertThat(json, hasJsonPath("$.requestTime.second", equalTo(0)));
    }

    @Test
    void testIncidentAmount(){
        assertThat(json, hasJsonPath("$.incidents", hasSize(1)));
    }

}
