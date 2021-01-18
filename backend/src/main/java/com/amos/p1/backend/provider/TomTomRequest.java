package com.amos.p1.backend.provider;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class TomTomRequest implements ProviderRequest {

    private final static String STYLE = "s3";
    private final static String ZOOM_LEVEL = "10";
    private final static String TRAFFIC_MODEL_ID = "-1";
    private final static String PROJECTION = "EPSG4326";
    private final static String API_KEY = "3iFupnTxt0RrNyHStTaLlzpSefQyydI5";

    @Override
    public String request(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude) {

        RestTemplate restTemplate = new RestTemplate();
        String url = getUrl(minLatitude, minLongitude, maxLatitude, maxLongitude);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }else{
            throw new IllegalStateException("Cant send request to TomTom. Request: " + response);
        }
    }

    public String getUrl(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude){
        //https://api.tomtom.com/traffic/services/4/incidentDetails/s3/52.5542%2C13.2823%2C52.4721%2C13.5422/10/-1/json?projection=EPSG4326&geometries=original&expandCluster=true&key=*****
        ///json?projection=EPSG4326&geometries=original&expandCluster=true&key=*****

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl("https://api.tomtom.com/traffic/services/4/incidentDetails")
                    .path("/" + STYLE)
                    .path("/" + minLatitude + "," + minLongitude + "," + maxLatitude + "," + maxLongitude)
                    .path("/" + ZOOM_LEVEL)
                    .path("/" + TRAFFIC_MODEL_ID)
                    .path("/json")
                        .queryParam("projection", PROJECTION)
                        .queryParam("geometries", "original")
                        .queryParam("expandCluster", "true")
                        .queryParam("key", API_KEY)
                .build();

        return uri.encode().toString();
    }
}
