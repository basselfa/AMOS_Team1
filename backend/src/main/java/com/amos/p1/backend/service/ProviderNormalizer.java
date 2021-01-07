package com.amos.p1.backend.service;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import com.amos.p1.backend.provider.HereRequestDummy;
import com.amos.p1.backend.provider.ProviderRequest;
import com.amos.p1.backend.provider.TomTomRequestDummy;

import java.util.ArrayList;
import java.util.List;

public class ProviderNormalizer {

    private final ProviderRequest tomtomRequest = new TomTomRequestDummy();
    private final ProviderRequest hereRequest = new HereRequestDummy();
//    private final ProviderRequest tomtomRequest = new TomTomRequest();
//    private final ProviderRequest hereRequest = new HereRequest();

    private final CityBoundingBoxesService cityBoundingBoxesService = new CityBoundingBoxesService();

    public List<Request> parseCurrentRequest(){
        List<Request> requests = new ArrayList<>();

        for (CityBoundingBox cityBoundingBox : cityBoundingBoxesService.getCityBoundingBoxes()) {
            List<Incident> incidents = new ArrayList<>();

            System.out.println("Request data from city: " + cityBoundingBox.getCity());

            System.out.println("Get data from here.com");
            List<Incident> hereIncidents = getHereIncidents(cityBoundingBox);
            System.out.println("Get data from tomtom");
            List<Incident> tomTomIncidents = getTomTomIncidents(cityBoundingBox);

            incidents.addAll(hereIncidents);
            incidents.addAll(tomTomIncidents);

            Request request = new Request();
            request.setCityName(cityBoundingBox.getCity());
            request.setIncidents(incidents);
            requests.add(request);
        }

        return requests;
    }

    public List<Incident> getRecentTomTomIncidentsFromCity(String city){
        CityBoundingBox boundBoxFromCity = cityBoundingBoxesService.getBoundBoxFromCity(city);

        return getTomTomIncidents(boundBoxFromCity);
    }

    private List<Incident> getTomTomIncidents(CityBoundingBox cityBoundingBox){
        String jsonTomTom = tomtomRequest.request(
                cityBoundingBox.getMinCorner().getLatitude(),
                cityBoundingBox.getMinCorner().getLongitude(),
                cityBoundingBox.getMaxCorner().getLatitude(),
                cityBoundingBox.getMaxCorner().getLongitude());

        JsonToIncident normalizationTomTom = new TomTomNormalization();
        return normalizationTomTom.normalize(jsonTomTom);
    }

    private List<Incident> getHereIncidents(CityBoundingBox cityBoundingBox){
        String jsonHere = hereRequest.request(
                cityBoundingBox.getMinCorner().getLatitude(),
                cityBoundingBox.getMinCorner().getLongitude(),
                cityBoundingBox.getMaxCorner().getLatitude(),
                cityBoundingBox.getMaxCorner().getLongitude());

        JsonToIncident normalizationHere = new HereNormalization();
        return normalizationHere.normalize(jsonHere);
    }
}
