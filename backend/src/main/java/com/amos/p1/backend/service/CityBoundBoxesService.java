package com.amos.p1.backend.service;

import com.amos.p1.backend.data.CityBoundingBox;

import java.util.List;

public interface CityBoundBoxesService {

    List<CityBoundingBox> getCityBoundingBoxes();
    CityBoundingBox getBoundBoxFromCity(String city);
}
