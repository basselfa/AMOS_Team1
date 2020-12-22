package com.amos.p1.backend;

import com.amos.p1.backend.service.CityBoundingBoxesService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Restcontroller only for static demo purposes. Only use dummy strings for returning to client
 */
@RestController
@CrossOrigin(origins = "http://0.0.0.0:8080")
@RequestMapping("demo")
public class ResourceWithDummyData {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/incidents",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<String> getIncidentsByCity(@RequestParam("city") String city,
                                                     @RequestParam("timestamp") String timestamp){

        String incidents = Helper.getFileResourceAsString("rest-endpoint-dummy/berlin_incidents.json");

        return ResponseEntity.ok(incidents);

    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/timestamps",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<String>> getTimestampsByCity(@RequestParam("city") String city){

        List<String> timestamps = new ArrayList<>();
        timestamps.add("2020-12-19 12:00");
        timestamps.add("2020-12-19 13:00");

        return ResponseEntity.ok(timestamps);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/cities",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<String>> getAllCities(){

        List<String> cities = new ArrayList<>();
        cities.add("Berlin");
        cities.add("Frankfurt");
        cities.add("Malchin");

        return ResponseEntity.ok(cities);
    }
}