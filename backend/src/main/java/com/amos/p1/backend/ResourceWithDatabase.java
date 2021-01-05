package com.amos.p1.backend;

import com.amos.p1.backend.data.CityBoundingBox;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.CityBoundingBoxesService;
import com.amos.p1.backend.service.IncidentAggregator;
import com.amos.p1.backend.service.IncidentAggregatorDirectlyFromProvider;
import com.amos.p1.backend.service.IncidentAggregatorFromDatabase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://0.0.0.0:8080")
@RequestMapping("withDatabase")
public class ResourceWithDatabase {

    private final IncidentAggregator incidentAggregator = new IncidentAggregatorFromDatabase();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/incidents",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<Incident>> getIncidentsByCity(@RequestParam("city") String city,
                                                             @RequestParam("timestamp") Optional<String> timestamp){

        if(timestamp.isPresent()){
            LocalDateTime localDateTime = parseTimeStamp(timestamp.get());

            return ResponseEntity.ok(incidentAggregator.getFromCityAndTimeStamp(city, localDateTime));
        }else{
            return ResponseEntity.ok(incidentAggregator.getFromCity(city));
        }
    }


    private LocalDateTime parseTimeStamp(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(timestamp, formatter);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/incidentsWithTypes",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<Incident>> getIncidentsByCityAndType(@RequestParam("city") String city, @RequestParam("types") String types){

        IncidentAggregator incidentAggregator = new IncidentAggregatorDirectlyFromProvider();

        return ResponseEntity.ok(incidentAggregator.getFromCityAndTypes(city, parseTypes(types)));
    }

    private List<String> parseTypes(String types) {
        return Arrays.asList(types.split(","));
    }



    @RequestMapping(
            method = RequestMethod.GET,
            value = "/timestamps",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<String>> getTimestampsByCity(@RequestParam("city") String city) {

        List<LocalDateTime> localDateTimes = incidentAggregator.getTimestampsFromCity(city);
        List<String> timestamps = parseLocalDateTimes(localDateTimes);

        return ResponseEntity.ok(timestamps);
    }

    private List<String> parseLocalDateTimes(List<LocalDateTime> localDateTimes) {
        //Strings need to have this pattern: yyyy-MM-dd HH:mm.
        throw new IllegalStateException("Not yet implemented yet. Sprint 7");
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cities",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<CityBoundingBox>> getAllCities() {

        CityBoundingBoxesService cityBoundingBoxesService = new CityBoundingBoxesService();
        List<CityBoundingBox> cities = cityBoundingBoxesService.getCityBoundingBoxes();

        return ResponseEntity.ok(cities);

    }

    private List<String> parseCityList(List<CityBoundingBox> cityBoundingBoxes) {
        //TODO: change to lambda
        List<String> cities = new ArrayList<>();
        for (CityBoundingBox cityBoundingBox : cityBoundingBoxes) {
            cities.add(cityBoundingBox.getCity());
        }

        return cities;
    }

}
