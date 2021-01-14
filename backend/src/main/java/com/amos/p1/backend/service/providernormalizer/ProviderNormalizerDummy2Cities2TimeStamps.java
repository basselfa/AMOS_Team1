package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.data.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProviderNormalizerDummy2Cities2TimeStamps extends ProviderNormalizerDummy{

    @Override
    public List<Request> parseCurrentRequest() {

        LocalDateTime date2020_01_14 = LocalDateTime.of(2020, 1, 14, 10, 0, 0);
        LocalDateTime date2020_01_15 = LocalDateTime.of(2020, 1, 15, 0, 0, 0);

        List<Request> requests = new ArrayList<>();

        Request requestBerlin2020_01_14 = getRequest(
                "Berlin",
                T2020_01_14_HERE_BERLIN,
                T2020_01_14_TOMTOM_BERLIN,
                date2020_01_14
        );

        Request requestMuenchen2020_01_14 = getRequest(
                "Berlin",
                T2020_01_14_HERE_MUENCHEN,
                T2020_01_14_TOMTOM_MUENCHEN,
                date2020_01_14
        );

        Request requestBerlin2020_01_15 = getRequest(
                "München",
                T2020_01_15_HERE_BERLIN,
                T2020_01_15_TOMTOM_BERLIN,
                date2020_01_15
        );

        Request requestMuenchen2020_01_15 = getRequest(
                "München",
                T2020_01_15_HERE_MUENCHEN,
                T2020_01_15_TOMTOM_MUENCHEN,
                date2020_01_15
        );

        requests.add(requestBerlin2020_01_14);
        requests.add(requestMuenchen2020_01_14);
        requests.add(requestBerlin2020_01_15);
        requests.add(requestMuenchen2020_01_15);

        return requests;
    }
}
