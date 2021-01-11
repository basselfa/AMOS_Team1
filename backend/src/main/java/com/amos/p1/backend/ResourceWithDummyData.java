package com.amos.p1.backend;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<String> getIncidents(@RequestParam("city") String city,
                                                     @RequestParam("timestamp") Optional<String> timestamp,
                                                     @RequestParam("types") Optional<String> types){

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
        cities.add("Hamburg");
        cities.add("Munich");

        return ResponseEntity.ok(cities);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            value = "/comparison",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<EvaluationCandidate>> getComparison(@RequestParam("city") String city,
                                                                   @RequestParam("timestamp") String timestamp) {
        List<EvaluationCandidate> listEvalCans = new ArrayList<>();

        Incident incidentTomTom = new Incident("222","baustelle","major",
                "Traffic jam in Bergmannstraße",
                "Berlin", "Germany",
                "45.5", "67.4",
                "Bergmannstraße",
                "46.5", "69.5",
                "Bergmannstraße",
                1, "dummy",
                LocalDateTime.of(
                        2020, 5, 1,
                        12, 30, 0),
                LocalDateTime.of(
                        2020, 5, 1,
                        12, 30, 0),
                "670000:690000,681234:691234",6.0,new Long(1));

        Incident incidentHere = new Incident("123","baustelle","major",
                "Traffic jam in Bergmannstraße",
                "Munich", "Germany",
                "45.5", "67.4",
                "Bergmannstraße",
                "46.5", "69.5",
                "Bergmannstraße",
                1, "dummy",
                LocalDateTime.of(
                        2020, 5, 1,
                        12, 30, 0),
                LocalDateTime.of(
                        2020, 5, 1,
                        12, 30, 0),
                "670000:690000,681234:691234",6.0,new Long(1));

        EvaluationCandidate evaluationCandidate = new EvaluationCandidate(incidentTomTom, incidentTomTom);
        EvaluationCandidate evaluationCandidate2 = new EvaluationCandidate(incidentTomTom, incidentTomTom);
        listEvalCans.add(evaluationCandidate);
        listEvalCans.add(evaluationCandidate2);

        return ResponseEntity.ok(listEvalCans);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/comparisonEvaluationOverTime",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<List<ComparisonEvaluationDTO>> getComparisonEvaluationOverTime(@RequestParam("city") String city) {

        List<ComparisonEvaluationDTO> listCEDTO = new ArrayList<>();

        ComparisonEvaluationDTO comparisonEvaluationDTO = new ComparisonEvaluationDTO();
        comparisonEvaluationDTO.setDate(new Date());
        comparisonEvaluationDTO.setHereIncidentsAmount(40);
        comparisonEvaluationDTO.setTomTomIncidentsAmount(55);
        comparisonEvaluationDTO.setSameIncidentAmount(20);
        listCEDTO.add(comparisonEvaluationDTO);


        ComparisonEvaluationDTO comparisonEvaluationDTO2 = new ComparisonEvaluationDTO();
        comparisonEvaluationDTO2.setDate(new Date());
        comparisonEvaluationDTO2.setHereIncidentsAmount(50);
        comparisonEvaluationDTO2.setTomTomIncidentsAmount(73);
        comparisonEvaluationDTO2.setSameIncidentAmount(18);
        listCEDTO.add(comparisonEvaluationDTO2);

        ComparisonEvaluationDTO comparisonEvaluationDTO3 = new ComparisonEvaluationDTO();
        comparisonEvaluationDTO3.setDate(new Date());
        comparisonEvaluationDTO3.setHereIncidentsAmount(77);
        comparisonEvaluationDTO3.setTomTomIncidentsAmount(43);
        comparisonEvaluationDTO3.setSameIncidentAmount(35);
        listCEDTO.add(comparisonEvaluationDTO3);


        return ResponseEntity.ok(listCEDTO);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/someDateEndpoint",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<Date> getSomeDateEndpoint(@RequestParam("city") String cityName) {

        return ResponseEntity.ok(new Date(123));
    }

}