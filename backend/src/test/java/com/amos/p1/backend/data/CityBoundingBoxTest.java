package com.amos.p1.backend.data;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class CityBoundingBoxTest {

    @Test
    void testCityInformationToCityBoundingBox(){

        CityInformation cityInformation = new CityInformation();
        cityInformation.setCityName("Berlin");
        cityInformation.setCentreLatitude("50.0000");
        cityInformation.setCentreLongitude("60.0000");
        cityInformation.setSearchRadiusInMeter(10000);

        CityBoundingBox cityBoundingBox = new CityBoundingBox(cityInformation);

        assertThat(cityBoundingBox.getCity(), equalTo("Berlin"));
        final double error = 0.0015; // Error of 200 meter
        assertThat(cityBoundingBox.getMinCorner().getLatitudeAsFloat(), closeTo(49.9101, error));
        assertThat(cityBoundingBox.getMinCorner().getLongitudeAsFloat(), closeTo( 59.8605, error));
        assertThat(cityBoundingBox.getMaxCorner().getLatitudeAsFloat(), closeTo(50.0899, error));
        assertThat(cityBoundingBox.getMaxCorner().getLongitudeAsFloat(), closeTo(60.1395, error));
    }

}