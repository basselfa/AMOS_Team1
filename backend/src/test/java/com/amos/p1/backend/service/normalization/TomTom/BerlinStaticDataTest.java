package com.amos.p1.backend.service.normalization.TomTom;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.normalization.JsonToIncident;
import com.amos.p1.backend.service.normalization.TomTomNormalization;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BerlinStaticDataTest {

    private static final Logger log = LoggerFactory.getLogger(BerlinStaticDataTest.class);

    private List<Incident> incidentList;

    public BerlinStaticDataTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/Berlin.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incidentList = jsonNormalizer.normalize(json);
    }

    @Test
    void testIncidentAmount(){
        assertEquals(289, incidentList.size());
    }

    @Test
    void testIncidentTypesMapping(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/Berlin.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incidentList = jsonNormalizer.normalize(json);

        assertEquals("LANERESTRICTION", incidentList.get(0).getType());
    }

    @Test
    void testIncidentStartPositionStreet() {
        assertEquals("Spandauer Damm - Fürstenbrunner Weg (Königin-Elisabeth-Straße/L1121)", incidentList.get(0).getStartPositionStreet());
    }

    @Test
    void testStartPointLat() {
        assertEquals("52.51831", incidentList.get(0).getStartPositionLatitude());
    }

    @Test
    void testStartPointLong() {
        assertEquals("13.28007", incidentList.get(0).getStartPositionLongitude());
    }

    @Test
    void testEndPointLat() {
        assertEquals("52.51282", incidentList.get(0).getEndPositionLatitude());
    }

    @Test
    void testEndPointLong() {
        assertEquals("13.28139", incidentList.get(0).getEndPositionLongitude());
    }

}
