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
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AggregatorFromDatabaseTest {

    private static final Logger log = LoggerFactory.getLogger(AggregatorFromDatabaseTest.class);

    Aggregator aggregator = new AggregatorFromDatabase();
    Request berlinRequest;

    public AggregatorFromDatabaseTest() {
        MyRepo.setUseTestDatabase(true);
        MyRepo.dropAll();

        //Adding dummy data to database
        RequestCreatorDummy requestCreator = new RequestCreatorDummyBerlinSmall();

        List<Request> requests = requestCreator.buildRequests();
        for (Request request : requests) {
            MyRepo.insertRequest(request);
        }

        berlinRequest = requestCreator.getRequest("Berlin", LocalDateTime.of(2020, 1, 1, 0, 0, 0));
    }

    @Test
    void testGetIncidentsFromCity() {
        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());
        log.info("" + berlinRequest.getIncidents());
        assertThat(incidentList, hasSize(greaterThan(0)));                               // List not empty
        assertThat(incidentList, hasSize(equalTo(berlinRequest.getIncidents().size())));       // as long as input List TODO: Kang -> stimmt das so?


        for (int i = 0; i < incidentList.size(); i++) {
            assertThat(incidentList.get(i), equalTo(berlinRequest.getIncidents().get(i)));
        }

//        assertThat(incidentList.get(0), equalTo(berlinRequest.getIncidents().get(0)));
//        deepCompareIncidentLists(berlinRequest.getIncidents(), incidentList);
    }

    @Test
    void testGetIncidentsFromCityAndWithType() {
        // test for set of types
        List<String> types = new ArrayList<>();
        types.add(Incident.IncidentTypes.CONGESTION.toString());
        types.add(Incident.IncidentTypes.ROADCLOSURE.toString());

        List<Incident> resultIncidentList = aggregator.getIncidents("Berlin", Optional.ofNullable(berlinRequest.getRequestTime()), Optional.of(types));
        List<Incident> sourceIncidentList = berlinRequest.getIncidents().stream().filter(i -> types.contains(i.getType())).collect(Collectors.toList());

        assertThat(resultIncidentList, hasSize(equalTo(sourceIncidentList.size())));   // correct amount ?

        resultIncidentList.forEach(
                incident ->
                        assertThat(types, contains(incident.getType()))     // only incidnets with correct type
        );

        sourceIncidentList.forEach(
                incident -> {
                    assertThat(resultIncidentList, contains(incident));     // no duplicates or corrupted data -> all fine!
                }
        );

        // test all types individually
        Arrays.stream(Incident.IncidentTypes.values()).sequential().map(s -> s.toString()).forEach(
                type ->
                {
                    List<String> types2 = new ArrayList<String>();
                    types2.add(type);
                    List<Incident> resultIncidentList2 = aggregator.getIncidents("Berlin", Optional.ofNullable(berlinRequest.getRequestTime()), Optional.of(types2));
                    List<Incident> sourceIncidentList2 = berlinRequest.getIncidents().stream().filter(i -> type.equals(i.getType())).collect(Collectors.toList());

                    log.info(type);
                    assertThat(resultIncidentList2, hasSize(equalTo(sourceIncidentList2.size())));   // correct amount ?

                    resultIncidentList2.forEach(
                            incident ->
                                    assertThat(type, equalTo(incident.getType()))     // only incidnets with correct type
                    );


                    for (int i = 0; i < resultIncidentList2.size(); i++) {
                        assertThat(resultIncidentList2.get(i), equalTo(sourceIncidentList2.get(i)));        // test darf fehlschlagen, wenn reihenfoleg vertauscht sit
                    }

                }
        );


    }

    /*@Test
    void testGetIncidentsFromCityAndWithTypeListEmpty() {
        List<String> types = new ArrayList<>();

        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.empty(), Optional.of(types));

        assertThat(incidentList, hasSize(greaterThan(0)));
    }*/


    @Test
    void testGetIncidentsFromCityAndTimeStamp() {
        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.of(berlinRequest.getRequestTime()), Optional.empty());

        for (int i = 0; i < incidentList.size(); i++) {
            assertThat(incidentList.get(i), equalTo(berlinRequest.getIncidents().get(i)));
        }
//        deepCompareIncidentLists(berlinRequest.getIncidents(), incidentList);
    }

    @Test
    void testGetIncidentsFromCityAndTimeStampNotInDatabase() {
        LocalDateTime timestamp = LocalDateTime.of(
                10, 1, 1,
                12, 30, 0);
        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.of(timestamp), Optional.empty());

        assertThat(incidentList, is(empty()));
    }

    @Test
    void testGetTimestampsFromCity() {
        List<LocalDateTime> timestampList = aggregator.getTimestampsFromCity("Berlin");

        assertThat(timestampList, contains(berlinRequest.getRequestTime()));
    }

    @Test
    void testGetTimestampsFromCityNotInDatabase() {
        List<LocalDateTime> timestampList = aggregator.getTimestampsFromCity("DreamLand");

        assertThat(timestampList, is(empty()));
    }

    @Test
        // TODO: Move to resource test
    void testMarshallingOneIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());

        String json = objectMapper.writeValueAsString(berlinIncidents.get(0));
        assertThat(json, notNullValue());

    }

    @Test
        // TODO: Move to resource test
    void testMarshallingAllIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());

        String json = objectMapper.writeValueAsString(berlinIncidents);
        assertThat(json, notNullValue());

    }

    @Test
    void testGetEvaluationCandiate() {
        List<EvaluationCandidate> evaluationCandidates = aggregator.getEvaluationCandidate("Berlin", berlinRequest.getRequestTime());

        assertThat(evaluationCandidates, hasSize(equalTo(berlinRequest.getEvaluationCandidate().size())));  //richtige Größe


        List<Long> evaluationCandidateIDs = evaluationCandidates.stream().map(c -> c.getId()).collect(Collectors.toList());
        berlinRequest.getEvaluationCandidate().forEach(
                sourceCandidate -> {
                    Long id = sourceCandidate.getId();
                    boolean idIsPresent = evaluationCandidateIDs.contains(id);
                    assertThat(idIsPresent, equalTo(true)); // candidate wih id is not present ?

                    // assertThat(evaluationCandidateIDs, contains(id));

                    EvaluationCandidate resultCandidate = evaluationCandidates.stream().filter(c -> c.getId() == id).findFirst().get();

                    assertThat(resultCandidate.getScore(), equalTo(sourceCandidate.getScore()));
                    assertThat(resultCandidate.getConfidenceDescription(), equalTo(sourceCandidate.getConfidenceDescription()));
                    assertThat(resultCandidate.getRequestId(), equalTo(sourceCandidate.getRequestId()));
                    assertThat(resultCandidate.getTomTomIncident(), equalTo(sourceCandidate.getTomTomIncident()));
                    assertThat(resultCandidate.getHereIncident(), equalTo(sourceCandidate.getHereIncident()));
                    assertThat(resultCandidate.isDropped(), equalTo(sourceCandidate.isDropped()));
                    assertThat(resultCandidate.getTomTomIncidentId(), equalTo(sourceCandidate.getTomTomIncidentId()));
                    assertThat(resultCandidate.getHereIncidentId(), equalTo(sourceCandidate.getHereIncidentId()));
                }
        );

    }

    @Test
    void testGetComparisonEvaluationOverTime() {
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs = aggregator.getComparisonEvaluationOverTime("Berlin");
        log.info("" + comparisonEvaluationDTOs);

        assertThat(comparisonEvaluationDTOs, hasSize(1));

        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(berlinRequest.getEvaluationCandidate().size()));
        assertThat(comparisonEvaluationDTOs.get(0).getHereIncidentsAmount(), equalTo((int) berlinRequest.getIncidents().stream().filter(i -> i.getProvider().equals("0")).count()));
        assertThat(comparisonEvaluationDTOs.get(0).getTomTomIncidentsAmount(), equalTo((int) berlinRequest.getIncidents().stream().filter(i -> i.getProvider().equals("1")).count()));
    }

    private void deepCompareIncidentLists(List<Incident> sourceList, List<Incident> resultList) {
        assertThat(resultList, hasSize(equalTo(sourceList.size())));
        sourceList.forEach(
                sourceIncident -> { //für jeden surce Incident ...
                    Optional<Incident> optResultIncident = resultList.stream().filter(i -> i.getId() == sourceIncident.getId()).findAny();
                    assertThat(optResultIncident.isPresent(), equalTo(true));   // ein Incident mit der ID fehlt in der resultList

                    Incident resultIncident = optResultIncident.get();

                    deepCompareInciden(sourceIncident, resultIncident);

                    assertThat(resultList, contains(sourceIncident));   // ist in resultList vorhanden
                }
        );
    }

    private void deepCompareInciden(Incident sourceIncident, Incident resultIncident) {
        assertThat(sourceIncident.getTrafficId(), equalTo(resultIncident.getTrafficId()));
        assertThat(sourceIncident.getType(), equalTo(resultIncident.getType()));
        assertThat(sourceIncident.getSize(), equalTo(resultIncident.getSize()));
        assertThat(sourceIncident.getDescription(), equalTo(resultIncident.getDescription()));
        assertThat(sourceIncident.getCity(), equalTo(resultIncident.getCity()));
        assertThat(sourceIncident.getCountry(), equalTo(resultIncident.getCountry()));
        assertThat(sourceIncident.getLengthInMeter(), equalTo(resultIncident.getLengthInMeter()));
        assertThat(sourceIncident.getStartPositionLatitude(), equalTo(resultIncident.getStartPositionLatitude()));
        assertThat(sourceIncident.getStartPositionLongitude(), equalTo(resultIncident.getStartPositionLongitude()));
        assertThat(sourceIncident.getStartPositionStreet(), equalTo(resultIncident.getStartPositionStreet()));
        assertThat(sourceIncident.getEndPositionLatitude(), equalTo(resultIncident.getEndPositionLatitude()));
        assertThat(sourceIncident.getEndPositionLongitude(), equalTo(resultIncident.getEndPositionLongitude()));
        assertThat(sourceIncident.getEndPositionStreet(), equalTo(resultIncident.getEndPositionStreet()));
        assertThat(sourceIncident.getVerified(), equalTo(resultIncident.getVerified()));
        assertThat(sourceIncident.getProvider(), equalTo(resultIncident.getProvider()));
        assertThat(sourceIncident.getEntryTime(), equalTo(resultIncident.getEntryTime()));
        assertThat(sourceIncident.getEndTime(), equalTo(resultIncident.getEndTime()));
        assertThat(sourceIncident.getEdges(), equalTo(resultIncident.getEdges()));
    }

}
