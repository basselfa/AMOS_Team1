package com.amos.p1.backend.normalization.Here;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OneIncidentTest {
    private final List<Incident> incidentList = new LinkedList<>();

    public OneIncidentTest(){

        String json = Helper.getFileResourceAsString("normalization/HereData/OneIncident.json");

        JsonToIncident jsonNormalizer = new HereNormalization();
        incidentList.add(jsonNormalizer.normalizeOneIncident(json));
    }

    @Test
    void testIncidentAmount(){
        assertEquals(1, incidentList.size());

    }

}
