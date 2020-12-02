package com.amos.p1.backend;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.IncidentAggregator;
import com.amos.p1.backend.service.IncidentAggregatorDummy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class Resource {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/incidents",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<Incident>> getIncidentsByCity(@RequestParam("city") String city){

        IncidentAggregator incidentAggregator = new IncidentAggregatorDummy();

        return ResponseEntity.ok(incidentAggregator.getFromCity(city));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/historization",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    // to be refined with entryTime - getIncidentsByEntryTime()
    @ResponseBody
    public ResponseEntity<List<Incident>> getAllIncidents(){

        IncidentAggregator incidentAggregator = new IncidentAggregatorDummy();

        return ResponseEntity.ok(incidentAggregator.getAllData());
    }

}
