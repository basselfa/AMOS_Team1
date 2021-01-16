package com.amos.p1.backend.service.requestcreator;

import java.time.LocalDateTime;

public class RequestCreatorDummy2Cities2TimeStamps extends RequestCreatorDummy {

    public RequestCreatorDummy2Cities2TimeStamps(){

        LocalDateTime date2020_01_14 = LocalDateTime.of(2020, 1, 14, 10, 0, 0);
        LocalDateTime date2020_01_15 = LocalDateTime.of(2020, 1, 15, 0, 0, 0);

        addRequestData("Berlin", date2020_01_14, T2020_01_14_HERE_BERLIN, T2020_01_14_TOMTOM_BERLIN);
        addRequestData("Berlin", date2020_01_15, T2020_01_15_HERE_BERLIN, T2020_01_15_TOMTOM_BERLIN);

        addRequestData("München", date2020_01_14, T2020_01_14_HERE_MUENCHEN, T2020_01_14_TOMTOM_MUENCHEN);
        addRequestData("München", date2020_01_15, T2020_01_15_HERE_MUENCHEN, T2020_01_15_TOMTOM_MUENCHEN);

    }
}
