package com.amos.p1.backend.service;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.Location;

import java.util.ArrayList;
import java.util.List;

public class CityBoundingBoxesService {

    private final List<CityBoundingBox> cityBoundingBoxes = new ArrayList<>();

    public CityBoundingBoxesService(){
        initBoundingBoxes();
    }

    private void initBoundingBoxes(){
        CityBoundingBox berlin = new CityBoundingBox();
        berlin.setCity("Berlin");
        berlin.setMinCorner(new Location("52.5542", "13.2823"));
        berlin.setMaxCorner(new Location("52.4721", "13.5422"));

        cityBoundingBoxes.add(berlin);

    }

    public List<CityBoundingBox> getCityBoundingBoxes() {
        return cityBoundingBoxes;
    }

    public CityBoundingBox getBoundBoxFromCity(String city) {
        for (CityBoundingBox cityBoundingBoxTemp : cityBoundingBoxes) {
            if (city.equals(cityBoundingBoxTemp.getCity())){
                return cityBoundingBoxTemp;
            }
        }

        throw new IllegalStateException("Couldn't find you boundingbox with city: " + city);
    }

}
