package com.amos.p1.backend.service.cityboundingbox;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.database.MyRepo;

import java.util.ArrayList;
import java.util.List;

public class CityBoundingBoxesServiceImpl implements CityBoundingBoxesService {

    private final List<CityBoundingBox> cityBoundingBoxes = new ArrayList<>();

    public CityBoundingBoxesServiceImpl(){
        List<CityInformation> cityInformationlist = MyRepo.getAllCityInformation();

        for (CityInformation cityInformation : cityInformationlist) {
            CityBoundingBox cityBoundingBox = new CityBoundingBox(cityInformation);
            cityBoundingBoxes.add(cityBoundingBox);
        }
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
