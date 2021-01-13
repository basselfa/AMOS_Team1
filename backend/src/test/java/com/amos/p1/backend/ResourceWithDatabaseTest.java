package com.amos.p1.backend;


import com.amos.p1.backend.data.*;
import com.amos.p1.backend.database.DummyIncident;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.ProviderIntervalRequest;
import com.amos.p1.backend.service.ProviderNormalizer;
import com.amos.p1.backend.service.aggregator.Aggregator;
import com.amos.p1.backend.service.aggregator.AggregatorFromDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(properties = "app.scheduling.enable=false")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceWithDatabaseTest {

    @LocalServerPort
    private int port;
    private String base;
    private final static LocalDateTime LOCAL_DATE_TIME_DUMMY = LocalDateTime.of(2020, 10, 30, 16, 30);


    @BeforeAll
    public static void init() {

        System.out.println("setting Database properties");
        MyRepo.setUseTestDatabase(true);
    }


    @BeforeEach
    void setUp() {
        this.base = "http://localhost:" + port + "/withDatabase";

        System.out.println("reintialising Database");
        MyRepo.dropAll();

        //Adding dummy data to database
        ProviderIntervalRequest providerIntervalRequest = new ProviderIntervalRequest(true);
        providerIntervalRequest.setProviderNormalizer(new ProviderNormalizer(true));
        providerIntervalRequest.providerCronJob();
    }

    @Test
    void testIncidentsByCityAndTimestamp(){
        List<Incident> incidents =
            given()
                .param("city", "Berlin")
                .param("timestamp", "2020-01-01 00:00")
            .when()
                .get(base + "/incidents")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", Incident.class);

        System.out.println(incidents);
        assertThat(incidents, hasSize(greaterThan(0)));
    }

    @Test
    void testIncidentsByCityAndTimestampAndType(){
        List<Incident> incidents =
            given()
                .param("city", "Berlin")
                .param("timestamp", "2020-01-01 00:00")
                .param("types", "CONSTRUCTION")
            .when()
                .get(base + "/incidents")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", Incident.class);


        assertThat(incidents, hasSize(greaterThan(0)));
    }

    @Test
    void testIncidentByCityAndTimestampNotInDb(){

        List<Incident> incidents =
            given()
                .param("city", "Berlin")
                .param("timestamp", "2020-01-01 00:00")
            .when()
                .get(base + "/incidents")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", Incident.class);

        assertThat(incidents, hasSize(0));
    }

    @Test
    void testIncidentByCityNotInDb(){

        List<Incident> incidents =
            given()
                .param("city", "NOT MY CITY")
                .param("timestamp", "2020-01-01 00:00")
            .when()
                .get(base + "/incidents")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", Incident.class);


        assertThat(incidents, hasSize(0));
    }

    @Test
    void testTimeStampByCity() {
        List<String> timeStamps = given()
                .param("city", "Berlin")
            .when()
                .get(base + "/timestamps")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", String.class);

        assertThat(timeStamps, hasItem("2020-01-01 00:00"));
        assertThat(timeStamps, hasSize(1));
    }

    @Test
    void testGetAllCitiesAndCheckBoundingBoxes() throws JSONException {
        String s = given()
                .param("city", "Berlin")
                .when()
                .get(base + "/cities")
                .then().extract().asString();

        JSONArray arr = new JSONArray(s);

        for (int i = 0; i < arr.length(); i++) {  //for all cities
            JSONObject cityObj = arr.getJSONObject(i);

            double latMin = cityObj.getJSONObject("minCorner").getDouble("latitude");
            double longMin = cityObj.getJSONObject("minCorner").getDouble("longitude");
            double latMax = cityObj.getJSONObject("maxCorner").getDouble("latitude");
            double longMax = cityObj.getJSONObject("maxCorner").getDouble("longitude");

          /*  System.out.println("new");
            System.out.println(Math.abs(cityObj.getJSONObject("centerPoint").getDouble("longitude")));
            System.out.println((longMin + (longMax - longMin) / 2));*/


            assert (Math.abs(cityObj.getJSONObject("centerPoint").getDouble("latitude") - (latMin + (latMax - latMin) / 2)) < 0.1);
            assert (Math.abs(cityObj.getJSONObject("centerPoint").getDouble("longitude") - (longMin + (longMax - longMin) / 2)) < 0.1);



        }

    }
    @Test
    void testGgetComparisonEvaluationOverTime()  {
        Request request = createDummyRequest();
        MyRepo.insertRequest(request);

        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs =given()
                .param("city", "Berlin")
                .when()
                .get(base + "/comparisonEvaluationOverTime")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", ComparisonEvaluationDTO.class);

        System.out.println(comparisonEvaluationDTOs);
        assertThat(comparisonEvaluationDTOs, hasSize(greaterThan(0)));


    }


    @Test
    void testComparison(){
        Request request = createDummyRequest();
        MyRepo.insertRequest(request);


        List<EvaluationCandidate> evaluationCandidatesFromRest = given()
                .param("city", "Berlin")
                .param("timestamp", "2020-05-01 12:30")
                .when()
                .get(base + "/comparison")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", EvaluationCandidate.class);

        System.out.println(evaluationCandidatesFromRest);


        assertThat(evaluationCandidatesFromRest, hasSize(greaterThan(0)));
    }
    private Request getDummyRequestWithOneDummyIncident() {
        Incident incident = DummyIncident.createIncident();
        List<Incident> incidents = new ArrayList<>();
        incidents.add(incident);

        return getDummyRequestWithIncidents(incidents);
    }
    private Request getDummyRequestWithIncidents(List<Incident> incidents) {
        Request request = new Request();
        request.setRequestTime(LOCAL_DATE_TIME_DUMMY);
        request.setIncidents(incidents);

        return  request;
    }
    Request createDummyRequest(){
        List<Incident> incidents = new ArrayList<Incident>();
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

        Request request = new Request();
        request.setRequestTime(LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0));
        request.setCityName("Berlin");
        request.setIncidents(incidents);

        List<EvaluationCandidate> evaluationCandidates = new ArrayList<EvaluationCandidate>();
        EvaluationCandidate evaluationCandidate = new EvaluationCandidate ();
        evaluationCandidate.setTomTomIncident(incidents.get(0));
        evaluationCandidate.setHereIncident(incidents.get(1));
        evaluationCandidates.add(evaluationCandidate);
        request.setEvaluatedCandidates(evaluationCandidates);

        return request;
    }

}
