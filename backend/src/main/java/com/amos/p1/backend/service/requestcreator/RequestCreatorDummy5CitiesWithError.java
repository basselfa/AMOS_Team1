package com.amos.p1.backend.service.requestcreator;

import java.time.LocalDateTime;

public class RequestCreatorDummy5CitiesWithError extends RequestCreatorDummy {

    protected static final String T2020_01_14_HERE_BERLIN = "provider-dummy-data/2021-02-09_19-00_error/here-berlin.json";
    protected static final String T2020_01_14_TOMTOM_BERLIN = "provider-dummy-data/2021-02-09_19-00_error/tomtom-berlin.json";

    protected static final String T2020_01_14_HERE_FRANKFURT = "provider-dummy-data/2021-02-09_19-00_error/here-frankfurt.json";
    protected static final String T2020_01_14_TOMTOM_FRANKFURT = "provider-dummy-data/2021-02-09_19-00_error/tomtom-frankfurt.json";

    protected static final String T2020_01_14_HERE_HAMBURG = "provider-dummy-data/2021-02-09_19-00_error/here-hamburg.json";
    protected static final String T2020_01_14_TOMTOM_HAMBURG = "provider-dummy-data/2021-02-09_19-00_error/tomtom-hamburg.json";

    protected static final String T2020_01_14_HERE_MUENCHEN = "provider-dummy-data/2021-02-09_19-00_error/here-muenchen.json";
    protected static final String T2020_01_14_TOMTOM_MUENCHEN = "provider-dummy-data/2021-02-09_19-00_error/tomtom-muenchen.json";

    protected static final String T2020_01_14_HERE_NUERNBERG = "provider-dummy-data/2021-02-09_19-00_error/here-nuernberg.json";
    protected static final String T2020_01_14_TOMTOM_NUERNBERG = "provider-dummy-data/2021-02-09_19-00_error/tomtom-nuernberg.json";


    public RequestCreatorDummy5CitiesWithError(){

        LocalDateTime timestamp = LocalDateTime.of(2020, 1, 14, 10, 0, 0);

        addRequestData("Berlin", timestamp, T2020_01_14_HERE_BERLIN, T2020_01_14_TOMTOM_BERLIN);
        addRequestData("Frankfurt am Main", timestamp, T2020_01_14_HERE_FRANKFURT, T2020_01_14_TOMTOM_FRANKFURT);
        addRequestData("Hamburg", timestamp, T2020_01_14_HERE_HAMBURG, T2020_01_14_TOMTOM_HAMBURG);
        addRequestData("München", timestamp, T2020_01_14_HERE_MUENCHEN, T2020_01_14_TOMTOM_MUENCHEN);
        addRequestData("Nürnberg", timestamp, T2020_01_14_HERE_NUERNBERG, T2020_01_14_TOMTOM_NUERNBERG);

    }
}
