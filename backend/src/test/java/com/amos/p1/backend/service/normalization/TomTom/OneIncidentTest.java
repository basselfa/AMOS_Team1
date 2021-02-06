package com.amos.p1.backend.service.normalization.TomTom;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.normalization.JsonToIncident;
import com.amos.p1.backend.service.normalization.TomTomNormalization;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OneIncidentTest {

    private static final Logger log = LoggerFactory.getLogger(OneIncidentTest.class);

    private final Incident incident;

    public OneIncidentTest() {
        String json = Helper.getFileResourceAsString("normalization/TomTomData/OneIncident.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incident = jsonNormalizer.normalizeOneIncident(json);

        log.info("" + incident);
    }

    @Test
    void testProvider() {
        assertEquals("1", incident.getProvider());
    }

    // todo id in Incident is a Long but here it is a hex which is to large for a Long
    //@Test
    //void testId(){
    //    assertEquals(null, incident.getId()); //0c8ec1255d93f1498479cbe9eb2db037?
    //}

    @Test
    void testDescription() {
        assertEquals(incident.getDescription(), "carriageway reduced to one lane");
    }

    @Test
    void testType() {
        assertEquals("LANERESTRICTION", incident.getType());
    }

    @Test
    void testEntryTime() {
        assertEquals(LocalDateTime.parse("2020-11-28T06:00:00"), incident.getEntryTime());
    }

    @Test
    void testEndTime() {
        assertEquals(LocalDateTime.parse("2020-11-28T17:00:00"), incident.getEndTime());
    }

    @Test
    void testFrom() {
        assertEquals("Spandauer Damm - Fürstenbrunner Weg (Königin-Elisabeth-Straße/L1121)", incident.getStartPositionStreet());
    }

    @Test
    void testTo() {
        assertEquals("Knobelsdorffstraße (Königin-Elisabeth-Straße/L1121)", incident.getEndPositionStreet());
    }

    @Test
    void testStartPointLat() {
        assertEquals("52.51831", incident.getStartPositionLatitude());
    }

    @Test
    void testStartPointLong() {
        assertEquals("13.28007", incident.getStartPositionLongitude());
    }

    @Test
    void testEndPointLat() {
        assertEquals("52.51282", incident.getEndPositionLatitude());
    }

    @Test
    void testEndPointLong() {
        assertEquals("13.28139", incident.getEndPositionLongitude());
    }

    @Test
    void testEdges() {
        assertEquals("52.51784:13.28016,52.51771:13.28021,52.51765:13.28024,52.51729:13.28046,52.51712:13.28056,52.51702:13.2806,52.51691:13.28063,52.51624:13.28074,52.51554:13.28086,52.51514:13.28094,52.51499:13.28097,52.51458:13.28104,52.51385:13.28119,52.51371:13.28121,52.51319:13.28132,52.51305:13.28135,52.51295:13.28137", incident.getEdges());
    }
}
