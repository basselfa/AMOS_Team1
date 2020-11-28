package com.amos.p1.backend.normalization;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TomTomTruncatedJsonTest {

    private final List<Incident> incidentList;

    public TomTomTruncatedJsonTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/TomTomTruncated.json");

        JsonToIncident jsonNormalizer = new HereNormalization();
        incidentList = jsonNormalizer.normalize(json);
    }

    @Test
    void testIncidentAmount(){
        assertEquals(incidentList.size(), 1);

    }

}
