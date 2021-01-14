package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.data.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProviderNormalizerDummyBerlinSmall extends ProviderNormalizerDummy {

    private final static LocalDateTime TIME_STAMP = LocalDateTime.of(2020, 1,1,0,0,0);

    public ProviderNormalizerDummyBerlinSmall(){
        cities.put("Berlin", getProviderData(T2020_01_01_HERE_BERLIN_SMALL, T2020_01_01_TOMTOM_BERLIN_SMALL));
    }

    @Override
    public List<Request> parseCurrentRequest() {

        List<Request> requests = new ArrayList<>();

        cities.forEach((cityName, providerMap) -> {

            Request request = getRequest(
                    cityName,
                    providerMap.get(Provider.HERE),
                    providerMap.get(Provider.TOMTOM),
                    TIME_STAMP
            );

            requests.add(request);
        });

        return requests;
    }
}
