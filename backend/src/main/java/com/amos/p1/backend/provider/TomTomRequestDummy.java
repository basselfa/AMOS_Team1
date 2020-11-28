package com.amos.p1.backend.provider;

import com.amos.p1.backend.Helper;

public class TomTomRequestDummy implements ProviderRequest {

    @Override
    public String request(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude) {
        return Helper.getFileResourceAsString("provider-dummy-data/TomTomBerlin.json");

    }
}
