package com.amos.p1.backend.service.normalization.TomTom;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.normalization.JsonToIncident;
import com.amos.p1.backend.service.normalization.TomTomNormalization;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BerlinTruncatedStaticDataTest {

    private final List<Incident> incidentList;

    public BerlinTruncatedStaticDataTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/BerlinTruncated.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incidentList = jsonNormalizer.normalize(json);
    }

    @Test
    void testIncidentAmount(){
        assertEquals(4, incidentList.size());
    }

    @Test
    void testFirstIncidentType(){
        assertEquals("LANERESTRICTION", incidentList.get(0).getType());
    }

    @Test
    void testFirstIncidentEdgesNotNull(){
        assertEquals(17, incidentList.get(0).getEdgesAsLocations().getLocationsList().size());
    }

    @Test
    void testFirstIncidentLength(){
        assertEquals(618, incidentList.get(0).getLengthInMeter());
    }

}
