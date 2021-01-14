package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.data.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProviderNormalizerDummy2CitiesSameTimeStamp extends ProviderNormalizerDummy{

    private final static LocalDateTime TIME_STAMP = LocalDateTime.of(2020, 1, 14, 10, 0, 0);

    public ProviderNormalizerDummy2CitiesSameTimeStamp(){

        cities.put("Berlin", getProviderData(T2020_01_14_HERE_BERLIN, T2020_01_14_TOMTOM_BERLIN));
        cities.put("Frankfurt am Main", getProviderData(T2020_01_14_HERE_FRANKFURT, T2020_01_14_TOMTOM_FRANKFURT));

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
