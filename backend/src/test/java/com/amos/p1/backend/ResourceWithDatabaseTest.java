package com.amos.p1.backend;


import com.amos.p1.backend.data.*;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(properties = "app.scheduling.enable=false")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceWithDatabaseTest {

    private static final Logger log = LoggerFactory.getLogger(ResourceWithDatabaseTest.class);

    @LocalServerPort
    private int port;
    private String base;

    public ResourceWithDatabaseTest() {

        MyRepo.setUseTestDatabase(true);
        MyRepo.dropAll();

        //Adding dummy data to database
        RequestCreator requestCreator = new RequestCreatorDummyBerlinSmall();
        List<Request> requests = requestCreator.buildRequests();
        for (Request request : requests) {
            MyRepo.insertRequest(request);
        }
    }

    @BeforeEach
    void setup(){
        this.base = "http://localhost:" + port + "/withDatabase";
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

        log.info("" + incidents);
        log.info("" + incidents.get(0));
        assertThat(incidents, hasSize(49 + 58)); // 49 Here incidents + 58 tomtom incicents
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


        assertThat(incidents, hasSize(41));
    }

    @Test
    void testIncidentByCityAndTimestampNotInDb(){

        List<Incident> incidents =
            given()
                .param("city", "Berlin")
                .param("timestamp", "2020-02-01 00:00")
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

          /*  log.info("new");
            log.info(Math.abs(cityObj.getJSONObject("centerPoint").getDouble("longitude")));
            log.info((longMin + (longMax - longMin) / 2));*/

            assert (Math.abs(cityObj.getJSONObject("centerPoint").getDouble("latitude") - (latMin + (latMax - latMin) / 2)) < 0.1);
            assert (Math.abs(cityObj.getJSONObject("centerPoint").getDouble("longitude") - (longMin + (longMax - longMin) / 2)) < 0.1);
        }
    }

    @Test
    void testComparisonEvaluationOverTime(){
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs =
                given()
                    .param("city", "Berlin")
                .when()
                    .get(base + "/comparisonEvaluationOverTime")
                .then()
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", ComparisonEvaluationDTO.class);

        assertThat(comparisonEvaluationDTOs, hasSize(1));
        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(8));
        assertThat(comparisonEvaluationDTOs.get(0).getHereIncidentsAmount(), equalTo(49));
        assertThat(comparisonEvaluationDTOs.get(0).getTomTomIncidentsAmount(), equalTo(58));
    }

    @Test
    void testComparison(){
        List<EvaluationCandidate> evaluationCandidates =
                given()
                    .param("city", "Berlin")
                    .param("timestamp", "2020-01-01 00:00")
                .when()
                    .get(base + "/comparison")
                .then()
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", EvaluationCandidate.class);

        assertThat(evaluationCandidates, hasSize(8));

        assertThat(evaluationCandidates.get(0).getHereIncident(), is(notNullValue()));
        assertThat(evaluationCandidates.get(0).getTomTomIncident(), is(notNullValue()));
        assertThat(evaluationCandidates.get(0).getScore(), is(not(0)));
        assertThat(evaluationCandidates.get(0).getConfidenceDescription(), not(equalTo("")));
    }



}
