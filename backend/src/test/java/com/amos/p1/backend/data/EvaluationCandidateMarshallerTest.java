package com.amos.p1.backend.data;

import com.amos.p1.backend.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class EvaluationCandidateMarshallerTest {


    @Test
    void testSerialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        EvaluationCandidate evaluationCandidate = getEvaluationDummy();
        String json = objectMapper.writeValueAsString(evaluationCandidate);

        System.out.println(Helper.getPrettyJson(json));
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String json = Helper.getFileResourceAsString("EvaluationCandidate.json");
        objectMapper.readValue(json, EvaluationCandidate.class);
    }

    @Test
    void testDeserializeList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String json = Helper.getFileResourceAsString("EvaluationCandidates.json");
        objectMapper.readValue(json, EvaluationCandidate[].class);
    }


    private EvaluationCandidate getEvaluationDummy(){
        List<Incident> incidents = new ArrayList<>();
        incidents.add(
                new Incident("222","baustelle","major","Traffic jam in Bergmannstraße",
                        "Berlin", "Germany", "45.5", "67.4",
                        "Bergmannstraße",  "46.5", "69.5",
                        "Bergmannstraße",  1, "tomtom",
                        LocalDateTime.of( 2020, 5, 1, 12, 30, 0),
                        LocalDateTime.of( 2020, 5, 1, 12, 30, 0),
                        "670000:690000,681234:691234",6.0,new Long(1)));
        incidents.add(
                new Incident("222","baustelle","major","Traffic jam in Bergmannstraße",
                        "Berlin", "Germany", "45.5", "67.4",
                        "Bergmannstraße",  "46.5", "69.5",
                        "Bergmannstraße",  1, "here",
                        LocalDateTime.of( 2020, 5, 1, 12, 30, 0),
                        LocalDateTime.of( 2020, 5, 1, 12, 30, 0),
                        "670000:690000,681234:691234",6.0,new Long(1)));

        EvaluationCandidate evaluationCandidate = new EvaluationCandidate ();
        evaluationCandidate.setTomTomIncident(incidents.get(0));
        evaluationCandidate.setHereIncident(incidents.get(1));

        return evaluationCandidate;
    }
}