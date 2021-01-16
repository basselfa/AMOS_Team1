package com.amos.p1.backend.service.requestcreator;

import java.time.LocalDateTime;

public class RequestCreatorDummyBerlinSmall extends RequestCreatorDummy {

    public RequestCreatorDummyBerlinSmall(){

        LocalDateTime timestamp = LocalDateTime.of(2020, 1,1,0,0,0);

        addRequestData("Berlin", timestamp, T2020_01_01_HERE_BERLIN_SMALL, T2020_01_01_TOMTOM_BERLIN_SMALL);

    }

}
