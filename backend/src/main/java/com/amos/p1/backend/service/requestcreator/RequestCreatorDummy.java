package com.amos.p1.backend.service.requestcreator;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.normalization.HereNormalization;
import com.amos.p1.backend.service.normalization.JsonToIncident;
import com.amos.p1.backend.service.normalization.TomTomNormalization;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.evaluation.Evaluation;

import java.time.LocalDateTime;
import java.util.*;

public abstract class RequestCreatorDummy implements RequestCreator {

    protected enum Provider {
        HERE,TOMTOM
    }

    protected static class RequestData{
        private final String cityName;
        private final LocalDateTime timestamp;
        private final String hereJsonPath;
        private final String tomtomJsonPath;
        private Request request;

        private RequestData(String cityName, LocalDateTime timestamp, String hereJsonPath, String tomtomJsonPath) {
            this.cityName = cityName;
            this.timestamp = timestamp;
            this.hereJsonPath = hereJsonPath;
            this.tomtomJsonPath = tomtomJsonPath;
        }

        protected static RequestData of(String cityName, LocalDateTime localDateTime, String hereJsonPath, String tomtomJsonPath){
            return new RequestData(cityName, localDateTime, hereJsonPath, tomtomJsonPath);
        }
        
        protected String getCityName() {
            return cityName;
        }

        protected LocalDateTime getTimestamp() {
            return timestamp;
        }

        protected String getHereJsonPath() {
            return hereJsonPath;
        }

        protected String getTomtomJsonPath() {
            return tomtomJsonPath;
        }

        protected Request getRequest() {
            return request;
        }

        protected void setRequest(Request request) {
            this.request = request;
        }
    }

    protected final static String T2020_01_01_HERE_BERLIN_SMALL = "provider-dummy-data/2020-01-01_00-00_small/HereBerlin.json";
    protected final static String T2020_01_01_TOMTOM_BERLIN_SMALL = "provider-dummy-data/2020-01-01_00-00_small/TomTomBerlin.json";


    protected static final String T2020_01_14_HERE_BERLIN = "provider-dummy-data/2020-01-14_10-00/here-berlin.json";
    protected static final String T2020_01_14_TOMTOM_BERLIN = "provider-dummy-data/2020-01-14_10-00/tomtom-berlin.json";

    protected static final String T2020_01_14_HERE_FRANKFURT = "provider-dummy-data/2020-01-14_10-00/here-frankfurt_am_main.json";
    protected static final String T2020_01_14_TOMTOM_FRANKFURT = "provider-dummy-data/2020-01-14_10-00/tomtom-frankfurt_am_main.json";

    protected static final String T2020_01_14_HERE_HAMBURG = "provider-dummy-data/2020-01-14_10-00/here-hamburg.json";
    protected static final String T2020_01_14_TOMTOM_HAMBURG = "provider-dummy-data/2020-01-14_10-00/tomtom_hamburg.json";

    protected static final String T2020_01_14_HERE_MUENCHEN = "provider-dummy-data/2020-01-14_10-00/here-muenchen.json";
    protected static final String T2020_01_14_TOMTOM_MUENCHEN = "provider-dummy-data/2020-01-14_10-00/tomtom-muenchen.json";

    protected static final String T2020_01_14_HERE_NUERNBERG = "provider-dummy-data/2020-01-14_10-00/here-nuernberg.json";
    protected static final String T2020_01_14_TOMTOM_NUERNBERG = "provider-dummy-data/2020-01-14_10-00/tomtom-nuernberg.json";


    protected static final String T2020_01_15_HERE_BERLIN = "provider-dummy-data/2020-01-15_00-00/here-berlin.json";
    protected static final String T2020_01_15_TOMTOM_BERLIN = "provider-dummy-data/2020-01-15_00-00/tomtom-berlin.json";

    protected static final String T2020_01_15_HERE_MUENCHEN = "provider-dummy-data/2020-01-15_00-00/here-muenchen.json";
    protected static final String T2020_01_15_TOMTOM_MUENCHEN = "provider-dummy-data/2020-01-15_00-00/tomtom-muenchen.json";


    protected final List<RequestData> requestData = new ArrayList<>();

    protected void addRequestData(String cityName, LocalDateTime localDateTime, String hereJsonPath, String tomtomJsonPath) {
        requestData.add(RequestData.of(cityName, localDateTime, hereJsonPath, tomtomJsonPath));
    }

    @Override
    public List<Request> buildRequests() {

        List<Request> requests = new ArrayList<>();

        requestData.forEach(requestDatum -> {

            Request request = buildRequest(
                    requestDatum.getCityName(),
                    requestDatum.getHereJsonPath(),
                    requestDatum.getTomtomJsonPath(),
                    requestDatum.getTimestamp()
            );

            requestDatum.setRequest(request);

            requests.add(request);
        });

        return requests;
    }

    @Override
    public void setTimeStamp(LocalDateTime timestamp) {

    }

    @Override
    public void setCityBoundingBoxes(CityBoundingBoxesService cityBoundingBoxesService){

    }


    protected Request buildRequest(String city, String hereJsonPath, String tomtomJsonPath, LocalDateTime timestamp) {
        JsonToIncident normalizationHere = new HereNormalization();
        List<Incident> normalizedHere = normalizationHere.normalize(Helper.getFileResourceAsString(hereJsonPath));

        JsonToIncident normalizationTomTom = new TomTomNormalization();
        List<Incident> normalizedTomTom = normalizationTomTom.normalize(Helper.getFileResourceAsString(tomtomJsonPath));

        List<Incident> incidents = new ArrayList<>();
        incidents.addAll(normalizedHere);
        incidents.addAll(normalizedTomTom);

        Request request = new Request();
        request.setCityName(city);
        request.setIncidents(incidents);
        request.setRequestTime(timestamp);

        List<EvaluationCandidate> evaluationCandidate = Evaluation.getEvaluationCandidates(request);
        request.setEvaluatedCandidates(evaluationCandidate);

        return request;
    }

    public Request getRequest(String cityName, LocalDateTime timestamp){
        Optional<RequestData> requestData = 
                this.requestData.stream().filter(requestDatum ->
                    requestDatum.getCityName().equals(cityName) &&
                    requestDatum.getTimestamp().equals(timestamp)
                ).findFirst();
        
        if(requestData.isPresent()){
            return requestData.get().getRequest();
        }else{
            throw new IllegalStateException("Cant find request with cityname: " + cityName + " and timestamp: " + timestamp );
        }
    }
}
