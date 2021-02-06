package com.amos.p1.backend.service.requestcreator;

import com.amos.p1.backend.data.*;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import com.amos.p1.backend.provider.*;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.evaluation.Evaluation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestCreatorImpl implements RequestCreator {

    private final ProviderRequest tomtomRequest = new TomTomRequest();
    private final ProviderRequest hereRequest = new HereRequest();
    
    private CityBoundingBoxesService cityBoundingBoxesService;
    private LocalDateTime timestamp;

    public List<Request> buildRequests(){
        List<Request> requests = new ArrayList<>();

        for (CityBoundingBox cityBoundingBox : cityBoundingBoxesService.getCityBoundingBoxes()) {
            List<Incident> incidents = new ArrayList<>();

            System.out.println("Request data from city: " + cityBoundingBox.getCity());

            System.out.println("Get data from here.com");
            List<Incident> hereIncidents = getHereIncidents(cityBoundingBox);
            System.out.println("Amount of here data: " + hereIncidents.size());
            System.out.println("Get data from tomtom");
            List<Incident> tomTomIncidents = getTomTomIncidents(cityBoundingBox);
            System.out.println("Amount of tomtom data: " + tomTomIncidents.size());

            incidents.addAll(hereIncidents);
            incidents.addAll(tomTomIncidents);

            Request request = new Request();
            request.setCityName(cityBoundingBox.getCity());
            request.setIncidents(incidents);
            request.setRequestTime(timestamp);

            List<EvaluationCandidate> evaluationCandidate = Evaluation.getEvaluationCandidates(request);
            request.setEvaluatedCandidates(evaluationCandidate);

            requests.add(request);
        }

        return requests;
    }

    @Override
    public void setTimeStamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void setCityBoundingBoxes(CityBoundingBoxesService cityBoundingBoxesService) {
        this.cityBoundingBoxesService = cityBoundingBoxesService;
    }

    private List<Incident> getTomTomIncidents(CityBoundingBox cityBoundingBox){
        String json = getJson(cityBoundingBox, tomtomRequest);

        JsonToIncident normalizationTomTom = new TomTomNormalization();
        return normalizationTomTom.normalize(json);
    }

    private List<Incident> getHereIncidents(CityBoundingBox cityBoundingBox){
        String json = getJson(cityBoundingBox, hereRequest);

        JsonToIncident normalizationHere = new HereNormalization();
        return normalizationHere.normalize(json);
    }

    private String getJson(CityBoundingBox cityBoundingBox, ProviderRequest request) {
        return request.request(
                cityBoundingBox.getMinCorner().getLatitude(),
                cityBoundingBox.getMinCorner().getLongitude(),
                cityBoundingBox.getMaxCorner().getLatitude(),
                cityBoundingBox.getMaxCorner().getLongitude());
    }
}
