package com.amos.p1.backend.configuration;

import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class CityBoundingBoxServiceConfigProduction implements CityBoundingBoxServiceConfig{

    @Override
    public CityBoundingBoxesService getCityBoundBoxesService() {
        return new CityBoundingBoxesServiceImpl();
    }
}
