package com.amos.p1.backend.service.requestcreator;

import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestCreatorImplTest {

    @Test
    void testNormalDataRequestAndIncidentSize(){
//        fail("Fail because we dont want to overuse the free tier of provider APIs");
        LocalDateTime now = LocalDateTime.now();

        CityBoundingBoxesService cityBoundingBoxesService = new CityBoundingBoxesServiceImpl();

        RequestCreatorImpl requestCreator = new RequestCreatorImpl();
        requestCreator.setCityBoundingBoxes(cityBoundingBoxesService);
        requestCreator.setTimeStamp(now);

        List<Request> requests = requestCreator.buildRequests();

        int cityAmount = cityBoundingBoxesService.getCityBoundingBoxes().size();

        assertThat(requests.size(), equalTo(cityAmount));
        for (Request request : requests) {
            assertThat(request.getIncidents().size(), greaterThan(0));
        }
    }

}