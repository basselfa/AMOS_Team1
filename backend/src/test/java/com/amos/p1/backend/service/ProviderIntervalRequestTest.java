package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class ProviderIntervalRequestTest {

    private final ProviderIntervalRequest providerIntervalRequest = new ProviderIntervalRequest();

    @BeforeEach
    void setUp(){
        clearDatabase();
    }

    private void clearDatabase() {
        //TODO
    }

    @Test
    void testSavingToDatabase(){
        //providerIntervalRequest.providerCronJob();

        //TODO check database
    }

    @Test
    void testGettingBerlinTomTomIncidents(){
        List<Incident> berlinIncidents = providerIntervalRequest.getRecentTomTomIncidentsFromCity("Berlin");

        System.out.println(berlinIncidents.get(0));

        assertThat(berlinIncidents, hasSize(greaterThan(0)));
    }

}
