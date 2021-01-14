package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.CityBoundBoxesService;
import com.amos.p1.backend.service.CityBoundingBoxesServiceImpl;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ProviderNormalizerImplTest {

    @Test
    void testNormalDataRequestAndIncidentSize(){
        fail();
        LocalDateTime now = LocalDateTime.now();

        ProviderNormalizerImpl providerNormalizer = new ProviderNormalizerImpl(now);
        List<Request> requests = providerNormalizer.parseCurrentRequest();

        CityBoundBoxesService cityBoundBoxesService = new CityBoundingBoxesServiceImpl();
        int cityAmount = cityBoundBoxesService.getCityBoundingBoxes().size();

        assertThat(requests.size(), equalTo(cityAmount));
        for (Request request : requests) {
            assertThat(request.getIncidents().size(), greaterThan(0));
        }
    }

}