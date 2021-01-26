package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.provider.HereRequest;
import com.amos.p1.backend.provider.ProviderRequest;
import com.amos.p1.backend.provider.TomTomRequest;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesServiceImpl;
import org.junit.jupiter.api.Test;

public class ProviderLinksTest {

    private final TomTomRequest tomtomRequest = new TomTomRequest();
    private final HereRequest hereRequest = new HereRequest();
    private final CityBoundingBoxesService cityBoundingBoxesService = new CityBoundingBoxesServiceImpl();


    @Test
    void test() {
        for (CityBoundingBox cityBoundingBox : cityBoundingBoxesService.getCityBoundingBoxes()) {
            System.out.println(cityBoundingBox.getCity());
            System.out.println("Tomtom: " + getUrl(cityBoundingBox, tomtomRequest));
            System.out.println("Here: " + getUrl(cityBoundingBox, hereRequest));
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
