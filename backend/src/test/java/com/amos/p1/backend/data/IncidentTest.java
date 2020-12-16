package com.amos.p1.backend.data;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IncidentTest {

    @Test
    void testEdgesStringWith0Locations(){
        Incident incident = new Incident();

        Locations locations = new Locations();

        incident.setEdgesAsLocations(locations);

        assertThat(incident.getEdges(), equalTo(""));
    }

    @Test
    void testEdgesStringWith1Locations(){
        Incident incident = new Incident();

        Locations locations = new Locations();
        locations.addLocation(new Location("12.34", "56.78"));

        incident.setEdgesAsLocations(locations);

        assertThat(incident.getEdges(), equalTo("12.34:56.78"));
    }

    @Test
    void testEdgesStringWith2Locations(){
        Incident incident = new Incident();

        Locations locations = new Locations();
        locations.addLocation(new Location("12.34", "56.78"));
        locations.addLocation(new Location("22.34", "22.78"));

        incident.setEdgesAsLocations(locations);

        assertThat(incident.getEdges(), equalTo("12.34:56.78,22.34:22.78"));
    }
}
