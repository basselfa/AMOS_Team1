package com.amos.p1.backend.normalization;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TomTomOneIncidentTest {

    private final Incident incident;

    public TomTomOneIncidentTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/TomTomOneIncident.json");

        JsonToIncident jsonNormalizer = new HereNormalization();
        incident = jsonNormalizer.normalizeOneIncident(json);
    }

    @Test
    void testProvider(){
        assertEquals(incident.getProvider(), 0);
    }

    @Test
    void testId(){
        assertEquals(incident.getId(), null); //fd536bb9a79e399af6ebb65061b713f0?
    }

    @Test
    void testDescription() {
        assertEquals(incident.getDescription(), "roadworks");
    }

    @Test
    void testEntryTime() {
        assertEquals(incident.getEntryTime(), LocalDateTime.parse("2020-11-27T20:04:00"));
    }

    @Test
    void testFrom(){
        assertEquals(incident.getStartPositionStreet(), "A6 / Poortdreef");
    }

    @Test
    void testTo(){
        assertEquals(incident.getStartPositionStreet(), "Trailerhelling Gooimeerdijk-West");
    }

    @Test
    void testShape(){
        //52.33321,5.17245
        //52.33372,5.17504
        //52.33445,5.17875
    }
}
