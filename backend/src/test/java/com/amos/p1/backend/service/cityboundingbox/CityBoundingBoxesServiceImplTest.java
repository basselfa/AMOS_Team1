package com.amos.p1.backend.service.cityboundingbox;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.database.MyRepo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CityBoundingBoxesServiceImplTest {

    @Test
    void test(){
        CityInformation cityInformation1 = new CityInformation();
        cityInformation1.setCityName("Berlin");
        cityInformation1.setCentreLatitude("50.0000");
        cityInformation1.setCentreLongitude("60.0000");
        cityInformation1.setSearchRadiusInMeter(10000);

        CityInformation cityInformation2 = new CityInformation();
        cityInformation2.setCityName("Munich");
        cityInformation2.setCentreLatitude("10.0000");
        cityInformation2.setCentreLongitude("20.0000");
        cityInformation2.setSearchRadiusInMeter(5000);

        MyRepo.insertCityInformation(cityInformation1);
        MyRepo.insertCityInformation(cityInformation2);

        CityBoundingBoxesService cityBoundingBoxesService = new CityBoundingBoxesServiceImpl();
        List<CityBoundingBox> cityBoundingBoxes = cityBoundingBoxesService.getCityBoundingBoxes();

        assertThat(cityBoundingBoxes, hasSize(2));
        assertThat(cityBoundingBoxes.get(0).getCity(), equalTo("Berlin"));
        assertThat(cityBoundingBoxes.get(0).getMinCorner().getLatitudeAsFloat(), not(equalTo(0)));
        assertThat(cityBoundingBoxes.get(0).getMinCorner().getLongitudeAsFloat(), not(equalTo(0)));
        assertThat(cityBoundingBoxes.get(1).getCity(), equalTo("Munich"));
        assertThat(cityBoundingBoxes.get(1).getMinCorner().getLatitudeAsFloat(), not(equalTo(0)));
        assertThat(cityBoundingBoxes.get(1).getMinCorner().getLongitudeAsFloat(), not(equalTo(0)));
    }

}