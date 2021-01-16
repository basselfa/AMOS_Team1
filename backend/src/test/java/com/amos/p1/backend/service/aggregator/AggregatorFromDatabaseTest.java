package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AggregatorFromDatabaseTest {

    Aggregator aggregator = new AggregatorFromDatabase();

    Request berlinRequest;

    public AggregatorFromDatabaseTest(){
        MyRepo.setUseTestDatabase(true);
        MyRepo.dropAll();

        //Adding dummy data to database
        RequestCreatorDummy requestCreator = new RequestCreatorDummyBerlinSmall();

        List<Request> requests = requestCreator.buildRequests();
        for (Request request : requests) {
            MyRepo.insertRequest(request);
        }

        berlinRequest = requestCreator.getRequest("Berlin", LocalDateTime.of(2020, 1,1,0,0,0));
    }

    @Test
    void testGetIncidentsFromCity() {
        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());

        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testGetIncidentsFromCityAndWithType() {
        List<String> types = new ArrayList<>();
        types.add("1");
        types.add("10");

        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.empty(), Optional.of(types));

        assertThat(incidentList, hasSize(greaterThan(0)));

        incidentList.forEach(incident -> {
            assertThat(incident.getType(),
                    anyOf(equalTo("1"),
                            equalTo("10"))
            );
        });
    }

    @Test
    void testGetIncidentsFromCityAndWithTypeListEmpty() {
        List<String> types = new ArrayList<>();

        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.empty(), Optional.of(types));

        assertThat(incidentList, hasSize(greaterThan(0)));
    }


    @Test
    void testGetIncidentsFromCityAndTimeStamp() {

        List<Incident> incidents = new ArrayList<Incident>();
        LocalDateTime timestamp = LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0);

        incidents.add(
                new Incident("222", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "dummy",
                        timestamp,
                        timestamp,
                        "670000:690000,681234:691234", 6.0, new Long(1)));
        MyRepo.insertIncident(incidents);

        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.of(timestamp), Optional.empty());

        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testGetIncidentsFromCityAndTimeStampNotInDatabase() {
        LocalDateTime timestamp = LocalDateTime.of(
                0, 1, 1,
                12, 30, 0);
        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.of(timestamp), Optional.empty());

        assertThat(incidentList, is(empty()));
    }

    @Test
    void testGetTimestampsFromCity() {
        List<Incident> incidents = new ArrayList<Incident>();
        incidents.add(
                new Incident("222", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "dummy",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));
        MyRepo.insertIncident(incidents);
        List<LocalDateTime> timestampList = aggregator.getTimestampsFromCity("Berlin");

        assertThat(timestampList, hasSize(greaterThan(0)));
    }

    @Test
    void testGetTimestampsFromCityNotInDatabase() {
        // List<LocalDateTime> timestampList = incidentAggregator.getTimestampsFromCity("DreamLand");

        // assertThat(timestampList, is(empty()));
    }

    @Test
    void testGetAllIncidents() {
        List<Incident> incidentList = aggregator.getAllData();

        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testMarshallingOneIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());

        String json = objectMapper.writeValueAsString(berlinIncidents.get(0));
        assertThat(json, notNullValue());

    }

    @Test
    void testMarshallingAllIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());

        String json = objectMapper.writeValueAsString(berlinIncidents);
        assertThat(json, notNullValue());

    }

    @Test
    void testGetEvaluationCandiate() {
        List<EvaluationCandidate> evaluationCandidates = aggregator.getEvaluationCandidate("Berlin", berlinRequest.getRequestTime());
        System.out.println(evaluationCandidates);


        assertThat(evaluationCandidates, hasSize(equalTo(berlinRequest.getEvaluationCandidate().size())));

        berlinRequest.getEvaluationCandidate().forEach(
                candidate -> {assert(evaluationCandidates.contains(candidate));}
        );

    }

    @Test
    void testGetComparisonEvaluationOverTime() {
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs = aggregator.getComparisonEvaluationOverTime("Berlin");

        System.out.println(comparisonEvaluationDTOs);

        assertThat(comparisonEvaluationDTOs, hasSize(1));
        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(9));
        assertThat(comparisonEvaluationDTOs.get(0).getHereIncidentsAmount(), equalTo(49));
        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(58));

    }
}
