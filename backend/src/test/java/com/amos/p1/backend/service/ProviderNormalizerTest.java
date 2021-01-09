package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Request;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

class ProviderNormalizerTest {

    @Test
    void testNormalDataRequestAndIncidentSize(){

        ProviderNormalizer providerNormalizer = new ProviderNormalizer(false);
        List<Request> requests = providerNormalizer.parseCurrentRequest();

        CityBoundBoxesService cityBoundBoxesService = new CityBoundingBoxesServiceImpl();
        int cityAmount = cityBoundBoxesService.getCityBoundingBoxes().size();

        assertThat(requests.size(), equalTo(cityAmount));
        for (Request request : requests) {
            assertThat(request.getIncidents().size(), greaterThan(0));
        }
    }

    @Test
    void testDummyRequestAndIncidentSize(){
        ProviderNormalizer providerNormalizer = new ProviderNormalizer(true);
        List<Request> requests = providerNormalizer.parseCurrentRequest();

        assertThat(requests.size(), equalTo(1));
        assertThat(requests.get(0).getIncidents().size(), greaterThan(1));
    }

}