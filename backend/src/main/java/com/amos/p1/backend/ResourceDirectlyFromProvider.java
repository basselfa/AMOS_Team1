package com.amos.p1.backend;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.aggregator.Aggregator;
import com.amos.p1.backend.service.aggregator.AggregatorDirectlyFromProvider;
import com.amos.p1.backend.service.aggregator.AggregatorFromDatabase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://0.0.0.0:8080")
@RequestMapping("directlyFromProvider")
public class ResourceDirectlyFromProvider {

    private final Aggregator aggregator = new AggregatorDirectlyFromProvider();


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/incidents",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<Incident>> getIncidentsByCity(@RequestParam("city") String city,
                                                             @RequestParam("timestamp") Optional<String> timestamp,
                                                             @RequestParam("types") Optional<String> types){

        Optional<LocalDateTime> timestampParsed = Optional.empty();
        Optional<List<String>> typesParsed = Optional.empty();

        if(timestamp.isPresent()){
            timestampParsed = Optional.of(parseTimeStamp(timestamp.get()));
        }

        if(types.isPresent()){
            typesParsed = Optional.of(parseTypes(types.get()));
        }

        List<Incident> incidents = aggregator.getIncidents(city, timestampParsed, typesParsed);
        return ResponseEntity.ok(incidents);
    }

    private LocalDateTime parseTimeStamp(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(timestamp, formatter);
    }

    private List<String> parseTypes(String types) {
        return Arrays.asList(types.split(","));
    }
}
