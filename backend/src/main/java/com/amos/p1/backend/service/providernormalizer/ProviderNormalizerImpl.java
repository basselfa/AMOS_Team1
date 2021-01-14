package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.data.*;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import com.amos.p1.backend.provider.*;
import com.amos.p1.backend.service.CityBoundBoxesService;
import com.amos.p1.backend.service.CityBoundingBoxesServiceImpl;
import com.amos.p1.backend.service.evaluation.Evaluation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProviderNormalizerImpl implements ProviderNormalizer{

    private final ProviderRequest tomtomRequest = new TomTomRequest();
    private final ProviderRequest hereRequest = new HereRequest();
    
    private final CityBoundBoxesService cityBoundingBoxesService = new CityBoundingBoxesServiceImpl();

    private final LocalDateTime timestamp;

    public ProviderNormalizerImpl(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }

    public List<Request> parseCurrentRequest(){
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

            System.out.println("Evaluate Data");
            Evaluation evaluation = new Evaluation();
            List<EvaluationCandidate> evaluationCandidates = evaluation.calculateCandidates(request);
            System.out.println("Amount of evaluation before manifold drop: " + evaluationCandidates.size());
            evaluationCandidates = evaluation.dropManifolds(evaluationCandidates);
            System.out.println("Amount of evaluation after manifold drop: " + evaluationCandidates.size());
            request.setEvaluatedCandidates(evaluationCandidates);

            requests.add(request);
        }

        return requests;
    }

    public List<Incident> getRecentTomTomIncidentsFromCity(String city){
        CityBoundingBox boundBoxFromCity = cityBoundingBoxesService.getBoundBoxFromCity(city);

        return getTomTomIncidents(boundBoxFromCity);
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
