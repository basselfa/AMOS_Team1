package com.amos.p1.backend.service;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Location;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import com.amos.p1.backend.provider.HereRequestDummy;
import com.amos.p1.backend.provider.ProviderRequest;
import com.amos.p1.backend.provider.TomTomRequestDummy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProviderIntervalRequest {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final ProviderRequest tomtomRequest = new TomTomRequestDummy();
    private final ProviderRequest hereRequest = new HereRequestDummy();

    // 1000 ms * 60 * 60 = 1 hour
    @Scheduled(fixedRate = 3600000)
    public void providerCronJob() {

        for (CityBoundingBox cityBoundingBox : getCityBoundingBoxes()) {

            List<Incident> hereIncidents = getHereIncidents(cityBoundingBox);
            List<Incident> tomTomIncidents = getTomTomIncidents(cityBoundingBox);

            saveToDatabase(hereIncidents);
            saveToDatabase(tomTomIncidents);
        }
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

    private List<CityBoundingBox> getCityBoundingBoxes(){
        CityBoundingBox berlin = new CityBoundingBox();
        berlin.setCity("Berlin");
        berlin.setMinCorner(new Location("52.5542", "13.2823"));
        berlin.setMaxCorner(new Location("52.4721", "13.5422"));

        List<CityBoundingBox> cityBoundingBoxes = new ArrayList<>();
        cityBoundingBoxes.add(berlin);

        return cityBoundingBoxes;
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

    private void saveToDatabase(List<Incident> incidents) {
        //TODO
    }

}
