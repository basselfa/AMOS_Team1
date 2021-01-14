package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.data.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProviderNormalizerDummy5CitiesSameTimeStamp extends ProviderNormalizerDummy{

    private final static LocalDateTime TIME_STAMP = LocalDateTime.of(2020, 1, 14, 10, 0, 0);
    
    public ProviderNormalizerDummy5CitiesSameTimeStamp(){
        
        cities.put("Berlin", getProviderData(T2020_01_14_HERE_BERLIN, T2020_01_14_TOMTOM_BERLIN));
        cities.put("Frankfurt am Main", getProviderData(T2020_01_14_HERE_FRANKFURT, T2020_01_14_TOMTOM_FRANKFURT));
        cities.put("Hamburg", getProviderData(T2020_01_14_HERE_HAMBURG, T2020_01_14_TOMTOM_HAMBURG));
        cities.put("München", getProviderData(T2020_01_14_HERE_MUENCHEN, T2020_01_14_TOMTOM_MUENCHEN));
        cities.put("Nürnberg", getProviderData(T2020_01_14_HERE_NUERNBERG, T2020_01_14_TOMTOM_NUERNBERG));
        
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
