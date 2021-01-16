package com.amos.p1.backend.service.requestcreator;

import com.amos.p1.backend.data.Request;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class RequestCreatorDummy5CitiesSameTimeStampTest {

    @Test
    void test(){
        RequestCreatorDummy requestCreatorDummy = new RequestCreatorDummy5CitiesSameTimeStamp();
        List<Request> requests = requestCreatorDummy.buildRequests();

        assertThat(requests, hasSize(5));
    }

}