package com.amos.p1.backend.normalization.TomTom;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OneIncidentTest {

    private final Incident incident;

    public OneIncidentTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/OneIncident.json");

        JsonToIncident jsonNormalizer = new HereNormalization();
        incident = jsonNormalizer.normalizeOneIncident(json);
    }

    @Test
    void testProvider(){
        assertEquals(incident.getProvider(), 0);
    }

    @Test
    void testId(){
        assertEquals(incident.getId(), null); //0c8ec1255d93f1498479cbe9eb2db037?
    }

    @Test
    void testDescription() {
        assertEquals(incident.getDescription(), "roadworks");
    }

    @Test
    void testEntryTime() {
        assertEquals(incident.getEntryTime(), LocalDateTime.parse("2020-11-28T06:00:00"));
    }

    @Test
    void testEndTime() {
        assertEquals(incident.getEntryTime(), LocalDateTime.parse("2020-11-28T17:00:00"));
    }

    @Test
    void testFrom(){
        assertEquals(incident.getStartPositionStreet(), "Spandauer Damm - Fürstenbrunner Weg (Königin-Elisabeth-Straße/L1121)");
    }

    @Test
    void testTo(){
        assertEquals(incident.getStartPositionStreet(), "Knobelsdorffstraße (Königin-Elisabeth-Straße");
    }

    @Test
    void testShape(){
        fail();
        //mnp_Imw`pA|AQXIJEfAk@`@SRGTEdCUjCWnAO\EpAMpC]ZCfBUZERCXC
    }
}
