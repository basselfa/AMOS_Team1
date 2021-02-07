package com.amos.p1.backend.service.normalization.Here;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.normalization.HereNormalization;
import com.amos.p1.backend.service.normalization.JsonToIncident;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TruncatedJsonTest {
    private final List<Incident> incidentList;

    public TruncatedJsonTest() {
        String json = Helper.getFileResourceAsString("normalization/HereData/Truncated.json");

        JsonToIncident jsonNormalizer = new HereNormalization();
        incidentList = jsonNormalizer.normalize(json);
    }

    @Test
    void testIncidentAmount() {
        assertEquals(1, incidentList.size());

    }

}
