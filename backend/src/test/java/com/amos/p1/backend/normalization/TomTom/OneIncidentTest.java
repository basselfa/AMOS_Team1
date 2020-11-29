package com.amos.p1.backend.normalization.TomTom;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OneIncidentTest {

    private final Incident incident;

    public OneIncidentTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/OneIncident.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incident = jsonNormalizer.normalizeOneIncident(json);

        System.out.println(incident);
    }

    @Test
    void testProvider(){
        assertEquals(0, incident.getProvider());
    }

    @Test
    void testId(){
        assertEquals(null, incident.getId()); //0c8ec1255d93f1498479cbe9eb2db037?
    }

    @Test
    void testDescription() {
        assertEquals( incident.getDescription(), "roadworks");
    }

    @Test
    void testEntryTime() {
        assertEquals(LocalDateTime.parse("2020-11-28T06:00:00"), incident.getEntryTime());
    }

    @Test
    void testEndTime() {
        assertEquals(LocalDateTime.parse("2020-11-28T17:00:00"), incident.getEntryTime());
    }

    @Test
    void testFrom(){
        assertEquals("Spandauer Damm - Fürstenbrunner Weg (Königin-Elisabeth-Straße/L1121)", incident.getStartPositionStreet());
    }

    @Test
    void testTo(){
        assertEquals("Knobelsdorffstraße (Königin-Elisabeth-Straße", incident.getStartPositionStreet());
    }

    @Test
    void testShape(){
        fail();
        //mnp_Imw`pA|AQXIJEfAk@`@SRGTEdCUjCWnAO\EpAMpC]ZCfBUZERCXC
    }
}
