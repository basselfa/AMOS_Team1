package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.DummyIncident;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.ProviderIntervalRequest;
import com.amos.p1.backend.service.ProviderNormalizer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AggregatorFromDatabaseTest {

    Aggregator aggregator = new AggregatorFromDatabase();
    private final static LocalDateTime LOCAL_DATE_TIME_DUMMY = LocalDateTime.of(2020, 10, 30, 16, 30);

    @BeforeAll
    public static void init() {

        System.out.println("setting Database properties");
        MyRepo.setUseTestDatabase(true);
    }

    @BeforeEach
    void setUp() {
        System.out.println("reintialising Database");
        MyRepo.dropAll();

        //Adding dummy data to database
        ProviderIntervalRequest providerIntervalRequest = new ProviderIntervalRequest(true);
        providerIntervalRequest.setProviderNormalizer(new ProviderNormalizer(true));
        providerIntervalRequest.providerCronJob();
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

/*    @Test
    void testGetEvaluationCandiate() {
        Request request = createDummyRequest();

        MyRepo.insertRequest(request);

        List<EvaluationCandidate> evaluationCandidates = aggregator.getEvaluationCandidate("Berlin", LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0));
        System.out.println(evaluationCandidates);
        assertThat(evaluationCandidates, hasSize(greaterThan(0)));

    }*/


    Request berlinRequest;

    public AggregatorFromDatabaseTest() {
        ProviderNormalizer providerNormalizer = new ProviderNormalizer(true);
        List<Request> requests = providerNormalizer.parseCurrentRequest();

        requests
                .stream()
                .filter(request -> request.getCityName().equals("Berlin"))
                .findFirst()
                .ifPresent(request -> this.berlinRequest = request);

        berlinRequest.setRequestTime(LocalDateTime.of(
                2021, 1, 9,
                11, 21, 25));
    }


    @Test
    void testGetEvaluationCandiate() {
        MyRepo.insertRequest(berlinRequest);

        List<EvaluationCandidate> evaluationCandidates = aggregator.getEvaluationCandidate("Berlin", LocalDateTime.of(
                2021, 1, 9,
                11, 21, 25));
        System.out.println(evaluationCandidates);


        assertThat(evaluationCandidates, hasSize(equalTo(berlinRequest.getEvaluationCandidate().size())));

        berlinRequest.getEvaluationCandidate().forEach(
                candidate -> {assert(evaluationCandidates.contains(candidate));}
        );

    }

/*    @Test
    void testGgetComparisonEvaluationOverTime() {
        Request request = createDummyRequest();

        MyRepo.insertRequest(request);


        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs = aggregator.getComparisonEvaluationOverTime("Berlin");

        System.out.println(comparisonEvaluationDTOs);
        assertThat(comparisonEvaluationDTOs, hasSize(greaterThan(0)));


    }*/

    @Test
    void testGetComparisonEvaluationOverTime() {
        MyRepo.insertRequest(berlinRequest);

        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs = aggregator.getComparisonEvaluationOverTime("Berlin");

        System.out.println(comparisonEvaluationDTOs);

        assertThat(comparisonEvaluationDTOs, hasSize(1));
        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(9));
        assertThat(comparisonEvaluationDTOs.get(0).getHereIncidentsAmount(), equalTo(49));
        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(58));

    }


    Request createDummyRequest() {
        List<Incident> incidents = new ArrayList<Incident>();
        incidents.add(
                new Incident("222", "baustelle", "major", "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany", "45.5", "67.4",
                        "Bergmannstraße", "46.5", "69.5",
                        "Bergmannstraße", 1, "tomtom",
                        LocalDateTime.of(2020, 5, 1, 12, 30, 0),
                        LocalDateTime.of(2020, 5, 1, 12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));
        incidents.add(
                new Incident("222", "baustelle", "major", "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany", "45.5", "67.4",
                        "Bergmannstraße", "46.5", "69.5",
                        "Bergmannstraße", 1, "here",
                        LocalDateTime.of(2020, 5, 1, 12, 30, 0),
                        LocalDateTime.of(2020, 5, 1, 12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));

        Request request = new Request();
        request.setRequestTime(LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0));
        request.setCityName("Berlin");
        request.setIncidents(incidents);

        List<EvaluationCandidate> evaluationCandidates = new ArrayList<EvaluationCandidate>();
        EvaluationCandidate evaluationCandidate = new EvaluationCandidate();
        evaluationCandidate.setTomTomIncident(incidents.get(0));
        evaluationCandidate.setHereIncident(incidents.get(1));
        evaluationCandidates.add(evaluationCandidate);
        request.setEvaluatedCandidates(evaluationCandidates);

        return request;
    }

}
