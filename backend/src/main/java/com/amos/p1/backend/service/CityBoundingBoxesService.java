package com.amos.p1.backend.service;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.Location;

import java.util.ArrayList;
import java.util.List;

public class CityBoundingBoxesService {

    private final List<CityBoundingBox> cityBoundingBoxes = new ArrayList<>();

    public CityBoundingBoxesService() {
        initBoundingBoxes();
    }

    private void initBoundingBoxes() {
        CityBoundingBox berlin = new CityBoundingBox();

        cityBoundingBoxes.add(new CityBoundingBox(
                "Berlin",
                new Location("52.61873", "13.21846"),
                new Location("52.37093", "13.69252")
        ));

        cityBoundingBoxes.add(new CityBoundingBox(
                "München",
                new Location("48.20022", "11.39352"),
                new Location("48.07948", "11.68191")
        ));

        cityBoundingBoxes.add(new CityBoundingBox(
                "Frankfurt am Main",
                new Location("50.16668", "8.59003"),
                new Location("50.11291", "8.77895")
        ));

        cityBoundingBoxes.add(new CityBoundingBox(
                "Hamburg",
                new Location("53.62411", "9.76440"),
                new Location("53.44331", "10.31371")
        ));

        cityBoundingBoxes.add(new CityBoundingBox(
                "Nürnberg",
                new Location("49.53933", "10.99159"),
                new Location("49.38693", "11.18934")
        ));

    }

    public List<CityBoundingBox> getCityBoundingBoxes() {
        return cityBoundingBoxes;
    }

    public CityBoundingBox getBoundBoxFromCity(String city) {
        for (CityBoundingBox cityBoundingBoxTemp : cityBoundingBoxes) {
            if (city.equals(cityBoundingBoxTemp.getCity())) {
                return cityBoundingBoxTemp;
            }
        }

        throw new IllegalStateException("Couldn't find you boundingbox with city: " + city);
    }

}
