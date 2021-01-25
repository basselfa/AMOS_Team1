package com.amos.p1.backend.service.cityboundingbox;

import com.amos.p1.backend.data.CityBoundingBox;

import java.util.List;

public interface CityBoundingBoxesService {

    List<CityBoundingBox> getCityBoundingBoxes();
    CityBoundingBox getBoundBoxFromCity(String city);
}
