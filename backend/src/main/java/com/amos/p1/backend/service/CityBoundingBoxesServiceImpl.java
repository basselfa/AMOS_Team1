package com.amos.p1.backend.service;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.Location;

import java.util.ArrayList;
import java.util.List;

public class CityBoundingBoxesServiceImpl implements CityBoundBoxesService{

    private final List<CityBoundingBox> cityBoundingBoxes = new ArrayList<>();

    public CityBoundingBoxesServiceImpl() {
        initBoundingBoxes();
    }

    private void initBoundingBoxes() {
        CityBoundingBox berlin = new CityBoundingBox();

        cityBoundingBoxes.add(new CityBoundingBox(
                "Berlin",
                new Location("52.39192425798079", "13.129307898046857"),
                new Location("52.644689636137734", "13.520944272525774")
        ));

        cityBoundingBoxes.add(new CityBoundingBox(
                "München",
                new Location("48.09448234888686", "11.474051382615752"),
                new Location("48.22756934915291", "11.627946833003753")
        ));

        cityBoundingBoxes.add(new CityBoundingBox(
                "Frankfurt am Main",
                new Location("50.02335", "8.52842"),
                new Location("50.21861", "8.72274")
        ));

        cityBoundingBoxes.add(new CityBoundingBox(
                "Hamburg",
                new Location("53.45722", "9.82070"),
                new Location("53.65871", "10.18462")
        ));

        cityBoundingBoxes.add(new CityBoundingBox(
                "Nürnberg",
                new Location("49.35247", "10.93617"),
                new Location("49.4985", "11.22506")
        ));

    }

    @Override
    public List<CityBoundingBox> getCityBoundingBoxes() {
        return cityBoundingBoxes;
    }

    @Override
    public CityBoundingBox getBoundBoxFromCity(String city) {
        for (CityBoundingBox cityBoundingBoxTemp : cityBoundingBoxes) {
            if (city.equals(cityBoundingBoxTemp.getCity())) {
                return cityBoundingBoxTemp;
            }
        }

        throw new IllegalStateException("Couldn't find you boundingbox with city: " + city);
    }

}
