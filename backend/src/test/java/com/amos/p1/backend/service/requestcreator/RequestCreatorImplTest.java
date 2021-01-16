package com.amos.p1.backend.service.requestcreator;

import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.CityBoundBoxesService;
import com.amos.p1.backend.service.CityBoundingBoxesServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestCreatorImplTest {

    @Test
    void testNormalDataRequestAndIncidentSize(){
        fail();
        LocalDateTime now = LocalDateTime.now();

        RequestCreatorImpl requestCreator = new RequestCreatorImpl();
        requestCreator.setTimeStamp(now);

        List<Request> requests = requestCreator.buildRequests();

        CityBoundBoxesService cityBoundBoxesService = new CityBoundingBoxesServiceImpl();
        int cityAmount = cityBoundBoxesService.getCityBoundingBoxes().size();

        assertThat(requests.size(), equalTo(cityAmount));
        for (Request request : requests) {
            assertThat(request.getIncidents().size(), greaterThan(0));
        }
    }

}