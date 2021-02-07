package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.database.RequestTest;
import com.amos.p1.backend.provider.HereRequest;
import com.amos.p1.backend.provider.ProviderRequest;
import com.amos.p1.backend.provider.TomTomRequest;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderLinksTest {

    private static final Logger log = LoggerFactory.getLogger(ProviderLinksTest.class);

    private final TomTomRequest tomtomRequest = new TomTomRequest();
    private final HereRequest hereRequest = new HereRequest();
    private final CityBoundingBoxesService cityBoundingBoxesService = new CityBoundingBoxesServiceImpl();


    @Test
    void test() {
        for (CityBoundingBox cityBoundingBox : cityBoundingBoxesService.getCityBoundingBoxes()) {
            log.info(cityBoundingBox.getCity());
            log.info("Tomtom: " + getUrl(cityBoundingBox, tomtomRequest));
            log.info("Here: " + getUrl(cityBoundingBox, hereRequest));
        }
    }


    private String getUrl(CityBoundingBox cityBoundingBox, ProviderRequest request) {
        return request.getUrl(
                cityBoundingBox.getMinCorner().getLatitude(),
                cityBoundingBox.getMinCorner().getLongitude(),
                cityBoundingBox.getMaxCorner().getLatitude(),
                cityBoundingBox.getMaxCorner().getLongitude());
    }
}
