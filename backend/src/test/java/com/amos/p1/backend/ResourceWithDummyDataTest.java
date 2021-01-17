package com.amos.p1.backend;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceWithDummyDataTest {

    @LocalServerPort
    private int port;
    private String base;

    @BeforeEach
    void setUp() {
        this.base = "http://localhost:" + port + "/demo";
    }

    /**
     * Status Code: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
     */
    @Test
    void testGetStatus200ByValidCitySearch(){
        given()
            .param("city", "berlin")
        .when()
            .get(base + "/incidents")
        .then()
            .statusCode(200);
    }

    @Test
    void testContentTypeCitySearch(){
        given()
            .param("city", "berlin")
        .when()
            .get(base + "/incidents")
        .then()
            .contentType(ContentType.JSON);
    }

    /**
     * Groovy Path: http://docs.groovy-lang.org/latest/html/documentation/#_gpath
     */
    @Test
    void testGetIncidentsHasElementsInList(){
        given()
            .param("city", "berlin")
        .when()
            .get(base + "/incidents")
        .then()
            .body("incidents.list.size()", greaterThan(0));
    }

    @Test
    void testComparisonListAmount(){
        List<EvaluationCandidate> evaluationCandidateList = given()
            .param("city", "berlin")
            .param("timestamp", "2021-01-10T16:08:43.780+00:00")
        .when()
            .get(base + "/comparison")
        .then()
            .extract()
            .body()
            .jsonPath()
            .getList(".", EvaluationCandidate.class);

        assertThat(evaluationCandidateList.size(), equalTo(2));
    }

    @Test
    void testComparisonNotExisting(){
        List<EvaluationCandidate> evaluationCandidateList = given()
            .param("city", "shanghai")
            .param("timestamp", "2021-01-09T16:08:43.780+00:00")
        .when()
            .get(base + "/comparison")
        .then()
            .extract()
            .body()
            .jsonPath()
            .getList(".", EvaluationCandidate.class);

        assertThat(evaluationCandidateList.size(), equalTo(0));
    }

    @Test
    void testComparisonEvaluationOverTimeListAmount(){
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOList = given()
            .param("city", "berlin")
        .when()
            .get(base + "/comparisonEvaluationOverTime")
        .then()
            .extract()
            .body()
            .jsonPath()
            .getList(".", ComparisonEvaluationDTO.class);

        assertThat(comparisonEvaluationDTOList.size(), equalTo(6));
    }

    @Test
    void testComparisonEvaluationOverTimeFirstDTOSameIncidents(){
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOList = given()
            .param("city", "berlin")
        .when()
            .get(base + "/comparisonEvaluationOverTime")
        .then()
            .extract()
            .body()
            .jsonPath()
            .getList(".", ComparisonEvaluationDTO.class);

        assertThat(comparisonEvaluationDTOList.get(0).getSameIncidentAmount(), equalTo(20));
    }


    @Test
    void testTimeStampsWithUnMarshalling(){
        List<String> timestamps = given()
                .param("city", "Berlin") // parameter in the url
            .when()
                .get(base + "/timestamps") // Url that you want to test
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", String.class); //Extract the root json element to a list of String

        assertThat(timestamps.get(0), equalTo("2020-12-19 12:00"));
        assertThat(timestamps.get(1), equalTo("2020-12-19 13:00"));
    }

    @Test
    void testDateEndpoint(){
        Date myDate = given()
                .param("city", "Berlin") // parameter in the url
            .when()
                .get(base + "/someDateEndpoint") // Url that you want to test
            .then()
                .extract()
                .as(Date.class); //Extrac as this object

        assertThat(myDate.getTime(), equalTo(123L));
    }
}
