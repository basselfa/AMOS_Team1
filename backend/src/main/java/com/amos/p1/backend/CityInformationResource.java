package com.amos.p1.backend;

import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.data.CityInformationIncomingDTO;
import com.amos.p1.backend.database.MyRepo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("withDatabase")
public class CityInformationResource {

    @RequestMapping(
            method = RequestMethod.POST,
            value ="/cityinformation",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addCityInformation(CityInformationIncomingDTO cityInformationIncomingDTO){
        CityInformation cityInformation = new CityInformation();

        cityInformation.setCityName(cityInformationIncomingDTO.getCityName());
        cityInformation.setCentreLatitude(cityInformationIncomingDTO.getCentreLatitude());
        cityInformation.setCentreLongitude(cityInformationIncomingDTO.getCentreLongitude());
        cityInformation.setSearchRadiusInMeter(cityInformationIncomingDTO.getSearchRadiusInMeter());

        MyRepo.insertCityInformation(cityInformation);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cityinformation",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<CityInformation>> getAllCityInformation(){
        List<CityInformation> allCityInformation = MyRepo.getAllCityInformation();
        return ResponseEntity.ok(allCityInformation);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/cityinformation",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteCityInformation(@RequestParam("id") long id){
        MyRepo.deleteCityInformation(id);
        return ResponseEntity.ok(null);
    }
}
