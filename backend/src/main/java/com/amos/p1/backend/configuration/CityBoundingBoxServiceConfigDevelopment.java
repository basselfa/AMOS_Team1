package com.amos.p1.backend.configuration;

import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesServiceDummy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class CityBoundingBoxServiceConfigDevelopment implements CityBoundingBoxServiceConfig{

    @Override
    public CityBoundingBoxesService getCityBoundBoxesService() {
        return new CityBoundingBoxesServiceDummy();
    }
}
