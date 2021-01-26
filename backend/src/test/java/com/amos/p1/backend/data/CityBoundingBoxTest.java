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

    @Test
    void testCentrePoint(){
        CityBoundingBox berlin = new CityBoundingBox(
                "Berlin",
                new Location("52.39192425798079", "13.129307898046857"),
                new Location("52.644689636137734", "13.520944272525774")
        );

        double latitude = berlin.getCenterPoint().getLatitudeAsFloat();
        double longitude = berlin.getCenterPoint().getLongitudeAsFloat();
        assertThat(latitude, closeTo(52.518306947, 0.00001));
        assertThat(longitude, closeTo(13.325126085, 0.00001));
    }

}