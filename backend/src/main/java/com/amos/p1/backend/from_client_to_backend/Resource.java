package com.amos.p1.backend.from_client_to_backend;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class Resource {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<Report> getIncidentsByCity(@RequestParam("city") String city){

        Report report = new Report();

        // dummy data
        if(city.equals("berlin")){
            report.addIncident(10, 20, "Traffic jam");
            report.addIncident(20, 10, "Crash");

        }

        return ResponseEntity.ok(report);
    }

}
