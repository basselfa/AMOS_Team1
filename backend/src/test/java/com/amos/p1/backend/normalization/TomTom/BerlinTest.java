package com.amos.p1.backend.normalization.TomTom;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BerlinTest {

    private List<Incident> incidentList;


    public BerlinTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/Berlin.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incidentList = jsonNormalizer.normalize(json);
    }

    @Test
    void testIncidentAmount(){
        String incidentsJson = Helper.getIncidentListMarshalling(incidentList);
        System.out.println(incidentsJson);
    }

    @Test
    void testIncidentTypes(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/Berlin.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incidentList = jsonNormalizer.normalize(json);

        assertEquals("LANERESTRICTION", incidentList.get(0).getType());
    }


}
