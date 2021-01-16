package com.amos.p1.backend.provider;

import com.amos.p1.backend.Helper;

public class TomTomRequestDummy implements ProviderRequest {

    @Override
    public String request(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude) {
        return Helper.getFileResourceAsString("provider-dummy-data/2020-01-01_00-00_small/TomTomBerlin.json");

    }

    @Override
    public String getUrl(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude) {
        return null;
    }
}
