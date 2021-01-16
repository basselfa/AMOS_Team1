package com.amos.p1.backend.service.requestcreator;

import java.time.LocalDateTime;

public class RequestCreatorDummy5CitiesSameTimeStamp extends RequestCreatorDummy {

    public RequestCreatorDummy5CitiesSameTimeStamp(){

        LocalDateTime timestamp = LocalDateTime.of(2020, 1, 14, 10, 0, 0);

        addRequestData("Berlin", timestamp, T2020_01_14_HERE_BERLIN, T2020_01_14_TOMTOM_BERLIN);
        addRequestData("Frankfurt am Main", timestamp, T2020_01_14_HERE_FRANKFURT, T2020_01_14_TOMTOM_FRANKFURT);
        addRequestData("Hamburg", timestamp, T2020_01_14_HERE_HAMBURG, T2020_01_14_TOMTOM_HAMBURG);
        addRequestData("München", timestamp, T2020_01_14_HERE_MUENCHEN, T2020_01_14_TOMTOM_MUENCHEN);
        addRequestData("Nürnberg", timestamp, T2020_01_14_HERE_NUERNBERG, T2020_01_14_TOMTOM_NUERNBERG);

    }
}
