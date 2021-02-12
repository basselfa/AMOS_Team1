package com.amos.p1.backend.service.normalization.TomTom;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.normalization.JsonToIncident;
import com.amos.p1.backend.service.normalization.TomTomNormalization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OneIncidentNoPolyLineStaticDataTest {

    private final Incident incident;

    public OneIncidentNoPolyLineStaticDataTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/OneIncidentNoPolyLine.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incident = jsonNormalizer.normalizeOneIncident(json);
    }

    // Shape and Start/Endpoint should be null because without PolyLine we can't extract any

    @Test
    void testShape(){
        assertEquals(incident.getEdges(), null);
    }

    @Test
    void testStartPoint(){
        assertEquals(incident.getStartPositionLatitude(), null);
        assertEquals(incident.getStartPositionLongitude(), null);
    }

    @Test
    void testEndPoint(){
        assertEquals(incident.getEndPositionLatitude(), null);
        assertEquals(incident.getEndPositionLongitude(), null);
    }

/*    @Test
    void testShape(){
        assertEquals(incident.getEdges(), null);
    }*/
}
