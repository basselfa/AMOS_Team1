package com.amos.p1.backend.normalization.TomTom;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OneIncidentNoPolyLineTest {

    private final Incident incident;

    public OneIncidentNoPolyLineTest(){
        String json = Helper.getFileResourceAsString("normalization/TomTomData/OneIncidentNoPolyLine.json");

        JsonToIncident jsonNormalizer = new TomTomNormalization();
        incident = jsonNormalizer.normalizeOneIncident(json);
    }

    @Test
    void testShape(){
        fail();
        //mnp_Imw`pA|AQXIJEfAk@`@SRGTEdCUjCWnAO\EpAMpC]ZCfBUZERCXC
    }
}
