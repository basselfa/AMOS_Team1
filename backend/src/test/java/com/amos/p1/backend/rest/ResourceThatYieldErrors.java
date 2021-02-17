package com.amos.p1.backend.rest;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesWithError;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesWithError2;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesWithError3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestPropertySource(properties = "app.scheduling.enable=false")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("blank")
public class ResourceThatYieldErrors {

    private static final Logger log = LoggerFactory.getLogger(ResourceThatYieldErrors.class);

    @LocalServerPort
    private int port;
    private String base;

    @BeforeEach
    void setUp() {
        this.base = "http://localhost:" + port + "/withDatabase";
    }

    @BeforeEach
    void prepareDatabase() {
        MyRepo.dropAll();

        //2021-02-10 16:00
        saveRequest();
    }

    private void saveRequest() {
        RequestCreatorDummy requestCreator = new RequestCreatorDummy5CitiesWithError();

        List<Request> requests = requestCreator.buildRequests();
        for (Request request : requests) {
            MyRepo.insertRequest(request);
        }
    }

    /*
     * Vorbedingung:
     * - Speichere einmal request in die db
     *
     * Aufrufe:
     * - rufe incidents
     * - rufe comparison auf
     * - rufe incidents
     * - rufe comparison auf
     * - speichere noch ein request
     *
     * Expecting:
     * - incidents sind noch da
     * - comparison over time funktioniert noch
     * - comparison funktioniert
     */
    @Test
    void testAfterRequestSaveAndComparisonAndIncidentCall(){

        // First get
        assertThat(getIncidentsFromBerlin("2021-02-10 16:00"), hasSize(788));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 16:00"), hasSize(97));


        // Second get
        assertThat(getIncidentsFromBerlin("2021-02-10 16:00"), hasSize(788));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 16:00"), hasSize(97));
    }

    private List<EvaluationCandidate> getEvaluationCandidatesFromBerlin(String timestamp) {
        List<EvaluationCandidate> evaluationCandidates =
            given()
                .param("city", "Berlin")
                .param("timestamp", timestamp)
            .when()
                .get(base + "/comparison")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", EvaluationCandidate.class);

        return evaluationCandidates;
    }

    private List<Incident> getIncidentsFromBerlin(String timestamp) {
        List<Incident> incidents =
            given()
                .param("city", "Berlin")
                .param("timestamp", timestamp)
            .when()
                .get(base + "/incidents")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", Incident.class);

        return incidents;
    }

    private List<Incident> getIncidentsFromBerlinOnlyHere(String timestamp) {
        List<Incident> incidents =
            given()
                .param("city", "Berlin")
                .param("timestamp", timestamp)
                .param("provider", "0")
            .when()
                .get(base + "/incidents")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", Incident.class);

        return incidents;
    }

    private List<ComparisonEvaluationDTO> getEvaluationOverTimeFromBerlin(){
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

        return comparisonEvaluationDTOs;
    }

    private List<String> getTimestampsFromBerlin(){
        List<String> timeStamps = given()
                .param("city", "Berlin")
            .when()
                .get(base + "/timestamps")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", String.class);

        return timeStamps;
    }


    /*
     * Vorbedingung:
     * - Speichere einmal request in die db (2021-02-10 16:00) berlin, muenchen ...
     *
     * Aufrufe:
     * - rufe incidents auf timestamp 2021-02-10 16:00 mit berlin
     * - rufe comparison/evaluationCandidate auf timestamp 2021-02-10 16:00 mit berlin
     * - speichere noch einen request (request2) mit timestamp (2021-02-10 17:00)
     *
     * Expecting:
     * - incidents sind noch da sind von (2021-02-10 16:00) in berlin mit gleicher anzahl wie vorher
     * - comparison/evaluationCandidate funktioniert auch noch (2021-02-10 16:00) in berlin mit gleicher anzahl
     *
     * - comparison over time funktioniert noch
     */
    @Test
    void testAfterRequestSaveAndComparisonAndOtherRequestSaveAndIncidentCall(){

        assertThat(getTimestampsFromBerlin(), hasSize(1));
        assertThat(getIncidentsFromBerlin("2021-02-10 16:00"), hasSize(788));
        assertThat(getIncidentsFromBerlinOnlyHere("2021-02-10 16:00").size(), greaterThan(0));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 16:00"), hasSize(97));
        checkComparisonEvaluationCandidateFromBerlin1Timestamp();


        // save another request (2021-02-10 17:00)
        RequestCreatorDummy requestCreator = new RequestCreatorDummy5CitiesWithError2();
        List<Request> requests = requestCreator.buildRequests();
        for (Request request : requests) {
            MyRepo.insertRequest(request);
        }

        assertThat(getTimestampsFromBerlin(), hasSize(2));
        assertThat(getIncidentsFromBerlin("2021-02-10 16:00"), hasSize(788));
        assertThat(getIncidentsFromBerlinOnlyHere("2021-02-10 16:00").size(), greaterThan(0));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 16:00"), hasSize(97));
        checkComparisonEvaluationCandidateFromBerlin2Timestamps();

        assertThat(getTimestampsFromBerlin(), hasSize(2));
        assertThat(getIncidentsFromBerlin("2021-02-10 17:00"), hasSize(788));
        assertThat(getIncidentsFromBerlinOnlyHere("2021-02-10 17:00").size(), greaterThan(0));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 17:00"), hasSize(97));
        checkComparisonEvaluationCandidateFromBerlin2Timestamps();

        assertThat(getTimestampsFromBerlin(), hasSize(2));
        assertThat(getIncidentsFromBerlin("2021-02-10 16:00"), hasSize(788));
        assertThat(getIncidentsFromBerlinOnlyHere("2021-02-10 16:00").size(), greaterThan(0));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 16:00"), hasSize(97));
        checkComparisonEvaluationCandidateFromBerlin2Timestamps();


        // save another request (2021-02-10 18:00)
        RequestCreatorDummy requestCreatorT18_00 = new RequestCreatorDummy5CitiesWithError3();
        List<Request> requestsT18_00 = requestCreatorT18_00.buildRequests();
        for (Request request : requestsT18_00) {
            MyRepo.insertRequest(request);
        }

        assertThat(getTimestampsFromBerlin(), hasSize(3));
        assertThat(getIncidentsFromBerlin("2021-02-10 16:00"), hasSize(788));
        assertThat(getIncidentsFromBerlinOnlyHere("2021-02-10 16:00").size(), greaterThan(0));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 16:00"), hasSize(97));
        checkComparisonEvaluationCandidateFromBerlin3Timestamps();

        assertThat(getTimestampsFromBerlin(), hasSize(3));
        assertThat(getIncidentsFromBerlin("2021-02-10 17:00"), hasSize(788));
        assertThat(getIncidentsFromBerlinOnlyHere("2021-02-10 17:00").size(), greaterThan(0));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 17:00"), hasSize(97));
        checkComparisonEvaluationCandidateFromBerlin3Timestamps();

        assertThat(getTimestampsFromBerlin(), hasSize(3));
        assertThat(getIncidentsFromBerlin("2021-02-10 18:00"), hasSize(788));
        assertThat(getIncidentsFromBerlinOnlyHere("2021-02-10 18:00").size(), greaterThan(0));
        assertThat(getEvaluationCandidatesFromBerlin("2021-02-10 18:00"), hasSize(97));
        checkComparisonEvaluationCandidateFromBerlin3Timestamps();

    }

    private void checkComparisonEvaluationCandidateFromBerlin1Timestamp() {
        List<ComparisonEvaluationDTO> evaluationOverTimeFromBerlin = getEvaluationOverTimeFromBerlin();
        assertThat(evaluationOverTimeFromBerlin.get(0).getHereIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(0).getTomTomIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(0).getSameIncidentAmount(), greaterThan(0));
    }

    private void checkComparisonEvaluationCandidateFromBerlin2Timestamps() {
        List<ComparisonEvaluationDTO> evaluationOverTimeFromBerlin = getEvaluationOverTimeFromBerlin();
        assertThat(evaluationOverTimeFromBerlin.get(0).getHereIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(0).getTomTomIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(0).getSameIncidentAmount(), greaterThan(0));

        assertThat(evaluationOverTimeFromBerlin.get(1).getHereIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(1).getTomTomIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(1).getSameIncidentAmount(), greaterThan(0));
    }

    private void checkComparisonEvaluationCandidateFromBerlin3Timestamps() {
        List<ComparisonEvaluationDTO> evaluationOverTimeFromBerlin = getEvaluationOverTimeFromBerlin();
        assertThat(evaluationOverTimeFromBerlin.get(0).getHereIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(0).getTomTomIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(0).getSameIncidentAmount(), greaterThan(0));

        assertThat(evaluationOverTimeFromBerlin.get(1).getHereIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(1).getTomTomIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(1).getSameIncidentAmount(), greaterThan(0));

        assertThat(evaluationOverTimeFromBerlin.get(2).getHereIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(2).getTomTomIncidentsAmount(), greaterThan(0));
        assertThat(evaluationOverTimeFromBerlin.get(2).getSameIncidentAmount(), greaterThan(0));
    }

}
