package com.amos.p1.backend.service.provider;

import com.amos.p1.backend.database.MyRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.PrintWriter;

//https://traffic.ls.hereapi.com/traffic/6.3/incidents.json?bbox=52.5542,13.2823;52.4721,13.5422&apiKey=V2uw_kp8e-C7ee0b9V6RJCMcPlkMzvCtl19CeJg2OAg
public class HereRequest implements ProviderRequest {

    private static final Logger log = LoggerFactory.getLogger(HereRequest.class);


    private final static String BASE_URL = "https://traffic.ls.hereapi.com/traffic/6.3/incidents.json";
    private final static String API_KEY = "V2uw_kp8e-C7ee0b9V6RJCMcPlkMzvCtl19CeJg2OAg";

    @Override
    public String request(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude) {

        RestTemplate restTemplate = new RestTemplate();
        String url = getUrl(minLatitude, minLongitude, maxLatitude, maxLongitude);

        log.info("Here Request url:" + url);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        if(response.getStatusCode() == HttpStatus.OK){
            return response.getBody();
        }else{
            throw new IllegalStateException("Cant send request to Here. Request: " + response);
        }

    }

    public String getUrl(String minLatitude, String minLongitude, String maxLatitude, String maxLongitude){

        String bbox = new StringBuilder()
                .append(minLatitude)
                .append(",")
                .append(minLongitude)
                .append(";")
                .append(maxLatitude)
                .append(",")
                .append(maxLongitude)
                .toString();

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(BASE_URL)
                .queryParam("bbox", bbox)
                .queryParam("apiKey", API_KEY)
                .build();

        return uri.encode().toString();
    }
}
