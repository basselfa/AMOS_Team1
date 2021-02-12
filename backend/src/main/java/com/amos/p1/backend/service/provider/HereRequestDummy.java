package com.amos.p1.backend.service.provider;

import com.amos.p1.backend.Helper;

/**
 * https://traffic.ls.hereapi.com/traffic/6.3/incidents.json?bbox=52.5542,13.2823;52.4721,13.5422&apiKey=V2uw_kp8e-C7ee0b9V6RJCMcPlkMzvCtl19CeJg2OAg
 */
public class HereRequestDummy implements ProviderRequest {
    @Override
    public String request(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude) {
        return Helper.getFileResourceAsString("provider-dummy-data/2020-01-01_00-00_small/HereBerlin.json");
    }

    @Override
    public String getUrl(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude) {
        return null;
    }
}
