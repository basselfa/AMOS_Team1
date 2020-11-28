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
    void test(){
        List<Incident> incidentList = incidentAggregator.getFromCity("Berlin");

        assertThat(incidentList, hasSize(greaterThan(0)));
    }

}
