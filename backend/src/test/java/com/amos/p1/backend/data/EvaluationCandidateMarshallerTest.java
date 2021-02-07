package com.amos.p1.backend.data;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.normalization.HereNormalization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@JsonTest
class EvaluationCandidateMarshallerTest {

    private static final Logger log = LoggerFactory.getLogger(EvaluationCandidateMarshallerTest.class);

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSerialize() throws JsonProcessingException {
        EvaluationCandidate evaluationCandidate = getEvaluationDummy1();
        String json = objectMapper.writeValueAsString(evaluationCandidate);

        log.info(Helper.getPrettyJson(json));
    }

    @Test
    void testDeserialize() throws JsonProcessingException {
        String json = Helper.getFileResourceAsString("EvaluationCandidate.json");
        objectMapper.readValue(json, EvaluationCandidate.class);
    }

    @Test
    void testSerializeList() throws JsonProcessingException {
        EvaluationCandidate evaluationCandidate1 = getEvaluationDummy1();
        EvaluationCandidate evaluationCandidate2 = getEvaluationDummy2();

        List<EvaluationCandidate> evaluationCandidates = new ArrayList<>();
        evaluationCandidates.add(evaluationCandidate1);
        evaluationCandidates.add(evaluationCandidate2);

        String json = objectMapper.writeValueAsString(evaluationCandidates);

        log.info(Helper.getPrettyJsonList(json));
    }

    @Test
    void testDeserializeList() throws JsonProcessingException {
        String json = Helper.getFileResourceAsString("EvaluationCandidates.json");
        objectMapper.readValue(json, EvaluationCandidate[].class);
    }


    private EvaluationCandidate getEvaluationDummy1(){
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

        EvaluationCandidate evaluationCandidate = new EvaluationCandidate();
        evaluationCandidate.setScore(5);
        evaluationCandidate.setTomTomIncident(incidents.get(0));
        evaluationCandidate.setHereIncident(incidents.get(1));

        return evaluationCandidate;
    }

    private EvaluationCandidate getEvaluationDummy2(){
        List<Incident> incidents = new ArrayList<>();
        incidents.add(
                new Incident("333","baustelle","major","Traffic jam in Bergmannstraße",
                        "Berlin", "Germany", "45.5", "67.4",
                        "Bergmannstraße",  "46.5", "69.5",
                        "Bergmannstraße",  1, "tomtom",
                        LocalDateTime.of( 2020, 5, 1, 12, 30, 0),
                        LocalDateTime.of( 2020, 5, 1, 12, 30, 0),
                        "670000:690000,681234:691234",6.0,new Long(1)));
        incidents.add(
                new Incident("444","baustelle","major","Traffic jam in Bergmannstraße",
                        "Berlin", "Germany", "45.5", "67.4",
                        "Bergmannstraße",  "46.5", "69.5",
                        "Bergmannstraße",  1, "here",
                        LocalDateTime.of( 2020, 5, 1, 12, 30, 0),
                        LocalDateTime.of( 2020, 5, 1, 12, 30, 0),
                        "670000:690000,681234:691234",6.0,new Long(1)));

        EvaluationCandidate evaluationCandidate = new EvaluationCandidate();
        evaluationCandidate.setScore(3);
        evaluationCandidate.setTomTomIncident(incidents.get(0));
        evaluationCandidate.setHereIncident(incidents.get(1));

        return evaluationCandidate;
    }
}