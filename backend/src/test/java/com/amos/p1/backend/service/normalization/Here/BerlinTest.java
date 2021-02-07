package com.amos.p1.backend.service.normalization.Here;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.normalization.HereNormalization;
import com.amos.p1.backend.service.normalization.JsonToIncident;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BerlinTest {

    private final List<Incident> incidentList;

    public BerlinTest() {
        String json = Helper.getFileResourceAsString("normalization/HereData/Berlin.json");

        JsonToIncident jsonNormalizer = new HereNormalization();
        incidentList = jsonNormalizer.normalize(json);
    }

    @Test
    void testPrint() {
        String incidentsJson = Helper.getIncidentListMarshalling(incidentList);
        //log.info(incidentsJson);
    }
}
