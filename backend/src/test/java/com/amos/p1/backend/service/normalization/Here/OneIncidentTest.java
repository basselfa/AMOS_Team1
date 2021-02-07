package com.amos.p1.backend.service.normalization.Here;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.normalization.HereNormalization;
import com.amos.p1.backend.service.normalization.JsonToIncident;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OneIncidentTest {
    private final Incident incident;

    private static final Logger log = LoggerFactory.getLogger(OneIncidentTest.class);

    public OneIncidentTest() {

        String json = Helper.getFileResourceAsString("normalization/HereData/OneIncident.json");

        JsonToIncident jsonNormalizer = new HereNormalization();
        incident = jsonNormalizer.normalizeOneIncident(json);
        log.info("" + incident);
    }

    @Test
    void testIncidentId() {
        assertEquals(1141077209175289128L, incident.getId());
    }

    @Test
    void testIncidentType() {
        assertEquals("ROADWORKS", incident.getType());
    }

    @Test
    void testIncidentSize() {
        assertEquals("0.35349", incident.getSize());
    }

    @Test
    void testIncidentDescription() {
        assertEquals("CONSTRUCTION & Geschlossen zwischen Am Sportplatz und Oberafferbacher Straße - Wegen Baustelle gesperrt.", incident.getDescription());
    }

    @Test
    void testIncidentCity() {
        assertEquals("Johannesberg", incident.getCity());
    }

    @Test
    void testIncidentCountry() {
        assertEquals(null, incident.getCountry());
    }

    @Test
    void testIncidentLengthInMeter() {
        assertNotEquals(0, incident.getLengthInMeter());        //todo
    }

    @Test
    void testIncidentStartLat() {
        assertEquals("50.02107", incident.getStartPositionLatitude());
    }

    @Test
    void testIncidentStartLng() {
        assertEquals("9.13304", incident.getStartPositionLongitude());
    }

    @Test
    void testIncidentStartStreet() {
        assertEquals("Aschaffenburger Straße - Am Sportplatz", incident.getStartPositionStreet());
    }

    @Test
    void testIncidentEndLat() {
        assertEquals("50.026", incident.getEndPositionLatitude());
    }

    @Test
    void testIncidentEndLng() {
        assertEquals("9.13453", incident.getEndPositionLongitude());
    }

    @Test
    void testIncidentEndStreet() {
        assertEquals("Aschaffenburger Straße - Oberafferbacher Straße", incident.getEndPositionStreet());
    }

    @Test
    void testIncidentVerified() {
        assertEquals(1, incident.getVerified());
    }

    @Test
    void testIncidentProvider() {
        assertEquals("0", incident.getProvider());
    }


    @Test
    void testIncidentTime() {
        assertEquals("2020-11-18T00:55:51", incident.getEntryTime().toString());
    }

    @Test
    void testIncidentEdges() {
        assertEquals("50.02107:9.13304,50.02151:9.13304,50.02151:9.13304,50.02221:9.13305,50.02221:9.13305,50.02257:9.13305,50.02295:9.13307,50.02317:9.13309,50.02317:9.13309,50.02346:9.13314,50.02381:9.13321,50.02415:9.13331,50.02442:9.13343,50.02463:9.13354,50.02483:9.13363,50.02483:9.13363,50.02495:9.13368,50.02521:9.13383,50.02532:9.13391,50.02554:9.13409,50.02554:9.13409,50.02569:9.13425,50.02577:9.13433,50.02587:9.13442,50.026:9.13453,", incident.getEdges());
    }

}
