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

@Component
@Profile("default")
public class CityBoundingBoxServiceConfigDevelopment implements CityBoundingBoxServiceConfig{

    public CityBoundingBoxServiceConfigDevelopment(){
        System.out.println("Using CityBoundingBox Service Config for development");
        saveDummyCityInformationIntoDb();
    }

    @Override
    public CityBoundingBoxesService getCityBoundBoxesService() {
        return new CityBoundingBoxesServiceImpl();
    }

    private void saveDummyCityInformationIntoDb() {
        CityBoundingBoxesService cityBoundingBoxesServiceDummy = new CityBoundingBoxesServiceDummy();
        for (CityBoundingBox cityBoundingBox : cityBoundingBoxesServiceDummy.getCityBoundingBoxes()) {

            String city = cityBoundingBox.getCity();
            Location centrePoint = cityBoundingBox.getCenterPoint();

            CityInformation cityInformation = new CityInformation();
            cityInformation.setCityName(city);
            cityInformation.setCentreLatitude(centrePoint.getLatitude());
            cityInformation.setCentreLongitude(centrePoint.getLongitude());
            cityInformation.setSearchRadiusInMeter(13000);  // Use for every city the same radius

            MyRepo.insertCityInformation(cityInformation);
        }
    }
}
