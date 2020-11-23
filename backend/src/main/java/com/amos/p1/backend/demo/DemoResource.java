package com.amos.p1.backend.demo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("demo")
public class DemoResource {

    private final Incidents incidents;
    private final Comparisons comparisons;

    public DemoResource(){
        Incident incident0Here = getIncident0Here();
        Incident incident0TomTom = getIncident0TomTOm();
        Incident incident1TomTom = getIncident1TomTom();

        this.incidents = getIncidents(incident0Here, incident0TomTom, incident1TomTom);;
        this.comparisons = getComparisons(incident0Here, incident0TomTom);
    }

    private Comparisons getComparisons(Incident incident0Here, Incident incident0TomTom) {
        Comparison comparison0 = new Comparison(incident0Here.getShape(), incident0TomTom, incident0Here);
        Comparisons comparisons = new Comparisons();
        comparisons.addComparison(comparison0);
        return comparisons;
    }

    private Incidents getIncidents(Incident incident0Here, Incident incident0TomTom, Incident incident1TomTom) {
        Incidents incidents = new Incidents();
        incidents.addIncidents(incident0Here);
        incidents.addIncidents(incident0TomTom);
        incidents.addIncidents(incident1TomTom);
        return incidents;
    }

    private Incident getIncident1TomTom() {
        Location location100 = new Location("52.514591", "13.322257");
        Location location101 = new Location("52.516276", "13.316517");
        Incident incident1TomTom = new Incident(2, "Demonstration");
        incident1TomTom.addShape(location100);
        incident1TomTom.addShape(location101);
        return incident1TomTom;
    }

    private Incident getIncident0TomTOm() {
        Location location010 = new Location("52.508075", "13.328351");
        Location location011 = new Location("52.512913", "13.326806");

        Incident incident0TomTom = new Incident(2, "Closed");
        incident0TomTom.addShape(location010);
        incident0TomTom.addShape(location011);

        return incident0TomTom;
    }

    private Incident getIncident0Here() {
        Location location000 = new Location("52.509041", "13.326355");
        Location location001 = new Location("52.511908", "13.322278");
        Location location002 = new Location("52.512626", "13.323158");
        Location location003 = new Location("52.513089", "13.330550");

        Incident incident0Here = new Incident(1, "Closed due construction");
        incident0Here.addShape(location000);
        incident0Here.addShape(location001);
        incident0Here.addShape(location002);
        incident0Here.addShape(location003);

        return incident0Here;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/incidents",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<Incidents> getIncidentsByCity(@RequestParam("city") String city){
        return ResponseEntity.ok(incidents);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/comparisons",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<Comparisons> getComparionsByCity(@RequestParam("city") String city){
        return ResponseEntity.ok(comparisons);
    }
}
