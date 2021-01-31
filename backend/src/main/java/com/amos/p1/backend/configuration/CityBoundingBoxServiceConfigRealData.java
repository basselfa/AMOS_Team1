package com.amos.p1.backend.configuration;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.data.Location;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesServiceDummy;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public class CityBoundingBoxServiceConfigRealData implements CityBoundingBoxServiceConfig{

    public CityBoundingBoxServiceConfigRealData(){
        System.out.println("Using CityBoundingBox Service Config for real data");
        saveDummyCityInformationIntoDb();
    }

    @Override
    public CityBoundingBoxesService getCityBoundBoxesService() {


        return new CityBoundingBoxesServiceImpl();
    }

    private void saveDummyCityInformationIntoDb() {

        CityInformation cityInformation = new CityInformation();
        cityInformation.setCityName("Berlin");
        cityInformation.setCentreLatitude("52.515531");
        cityInformation.setCentreLongitude("13.381941");
        cityInformation.setSearchRadiusInMeter(1000);

        MyRepo.insertCityInformation(cityInformation);

    }
}
