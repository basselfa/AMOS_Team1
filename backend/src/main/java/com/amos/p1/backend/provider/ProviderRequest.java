package com.amos.p1.backend.provider;

public interface ProviderRequest {
    String request(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude);
}
