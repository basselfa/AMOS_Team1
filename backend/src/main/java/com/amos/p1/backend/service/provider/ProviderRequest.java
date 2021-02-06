package com.amos.p1.backend.service.provider;

public interface ProviderRequest {
    String request(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude);

    String getUrl(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude);
}