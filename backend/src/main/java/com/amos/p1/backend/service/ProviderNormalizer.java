package com.amos.p1.backend.service;

import com.amos.p1.backend.data.*;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import com.amos.p1.backend.provider.*;
import com.amos.p1.backend.service.evaluation.Evaluation;

import java.util.ArrayList;
import java.util.List;

public class ProviderNormalizer {

    private final ProviderRequest tomtomRequest;
    private final ProviderRequest hereRequest;
    
    private final CityBoundBoxesService cityBoundingBoxesService;


    public ProviderNormalizer(boolean useDummy){

        if(useDummy){
            tomtomRequest = new TomTomRequestDummy();
            hereRequest = new HereRequestDummy();

            cityBoundingBoxesService = getCityBoundBoxServiceDummy();
        }else{
            tomtomRequest = new TomTomRequest();
            hereRequest = new HereRequest();

            cityBoundingBoxesService = new CityBoundingBoxesServiceImpl();
        }
    }

    private CityBoundBoxesService getCityBoundBoxServiceDummy() {

        // Data from 09.January 2020 12:50
        CityBoundingBox cityBoundingBox = new CityBoundingBox();
        cityBoundingBox.setCity("Berlin");
        cityBoundingBox.setMinCorner(new Location("52.509097", "13.381993"));
        cityBoundingBox.setMaxCorner(new Location("52.525915", "13.415296"));

        CityBoundBoxesService cityBoundingBoxesService;
        cityBoundingBoxesService = new CityBoundBoxesService(){
            @Override
            public List<CityBoundingBox> getCityBoundingBoxes() {
                List<CityBoundingBox> cityBoundingBoxes = new ArrayList<>();
                cityBoundingBoxes.add(cityBoundingBox);

                return cityBoundingBoxes;
            }
            @Override
            public CityBoundingBox getBoundBoxFromCity(String city) {
                return cityBoundingBox;
            }
        };

        return cityBoundingBoxesService;
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

            System.out.println("Evaluate Data");
            Evaluation evaluation = new Evaluation();
            List<EvaluationCandidate> evaluationCandidates = evaluation.calculateCandidates(request);
            System.out.println("Evaluate Data done. Amount of evaluation data: " + evaluationCandidates.size());
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
