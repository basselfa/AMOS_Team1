package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AggregatorFromDatabaseBerlinWith3Requests {

    Aggregator aggregator = new AggregatorFromDatabase();

    public AggregatorFromDatabaseBerlinWith3Requests() {
        MyRepo.setUseTestDatabase(true);
        MyRepo.dropAll();

        // TODO add incidents for 3 request and evaluations candidates

        List<Incident> incidents = new ArrayList<Incident>();
        incidents.add(
                new Incident("222", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "0",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));
        incidents.add(
                new Incident("333", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "1",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));


        List<Incident> incidents2 = new ArrayList<Incident>();
        incidents2.add(
                new Incident("222", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany2",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "0",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));
        incidents2.add(
                new Incident("333", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany2",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "1",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));

        List<Incident> incidents3 = new ArrayList<Incident>();
        incidents3.add(
                new Incident("222", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany3",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "0",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));
        incidents3.add(
                new Incident("333", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany3",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "1",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));

        EvaluationCandidate evaluationCandidateFromRequest1 = new EvaluationCandidate();
        List<EvaluationCandidate> evaluationCandidatesFromRequest1 = new ArrayList<>();
        evaluationCandidatesFromRequest1.add(evaluationCandidateFromRequest1);

        Request request1 = new Request();
        request1.setRequestTime(LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0));
        request1.setIncidents(incidents);
        request1.setCityName("Berlin");
        MyRepo.insertRequest(request1);


        Request request2 = new Request();
        request2.setRequestTime(LocalDateTime.of(
                2022, 5, 1,
                12, 30, 0));

        request2.setIncidents(incidents2);
        request2.setCityName("Berlin");
        MyRepo.insertRequest(request2);

        Request request3 = new Request();
        request3.setRequestTime(LocalDateTime.of(
                2023, 5, 1,
                12, 30, 0));
        request3.setIncidents(incidents3);
        request3.setCityName("Berlin");
        MyRepo.insertRequest(request3);



    }


    @Test
    void testGetComparisonEvaluationOverTime(){
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs = aggregator.getComparisonEvaluationOverTime("Berlin");
        System.out.println(comparisonEvaluationDTOs);
        assertThat(comparisonEvaluationDTOs, hasSize(3));

        // TODO change amount according to dummy data
        assertThat(comparisonEvaluationDTOs.get(0).getTomTomIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(0).getHereIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(0));

        assertThat(comparisonEvaluationDTOs.get(1).getTomTomIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(1).getHereIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(1).getSameIncidentAmount(), equalTo(0));

        assertThat(comparisonEvaluationDTOs.get(2).getTomTomIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(2).getHereIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(2).getSameIncidentAmount(), equalTo(0));
    }

}
