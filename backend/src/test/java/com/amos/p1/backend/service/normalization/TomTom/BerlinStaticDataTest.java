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
        String incidentsJson = Helper.getIncidentListMarshalling(incidentList);
        log.info(incidentsJson);
    }

    @Test
    void testIncidentTypesMapping(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/Berlin.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incidentList = jsonNormalizer.normalize(json);

        assertEquals("LANERESTRICTION", incidentList.get(0).getType());
    }


}
