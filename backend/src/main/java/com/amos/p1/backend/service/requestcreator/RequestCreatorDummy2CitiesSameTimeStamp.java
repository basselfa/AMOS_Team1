package com.amos.p1.backend.service.requestcreator;

import java.time.LocalDateTime;

public class RequestCreatorDummy2CitiesSameTimeStamp extends RequestCreatorDummy {

    public RequestCreatorDummy2CitiesSameTimeStamp(){

        LocalDateTime timestamp = LocalDateTime.of(2020, 1, 14, 10, 0, 0);

        addRequestData("Berlin", timestamp, T2020_01_14_HERE_BERLIN, T2020_01_14_TOMTOM_BERLIN);
        addRequestData("Frankfurt am Main", timestamp, T2020_01_14_HERE_FRANKFURT, T2020_01_14_TOMTOM_FRANKFURT);

    }
}
