package com.amos.p1.backend;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;

import static io.restassured.RestAssured.given;
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
    void testComparison(){
        given()
            .param("city", "berlin")
            .param("timestamp", "2021-01-10T16:08:43.780+00:00")
        .when()
            .get(base + "/comparison")
        .then()
            .body("comparison.size()", equalTo(2));
    }

    @Test
    void testComparisonEvaluationOverTime(){
        Date date = new Date();
        System.out.println(date);
        given()
            .param("city", "berlin")
        .when()
            .get(base + "/comparisonEvaluationOverTime")
        .then()
            .body("compEval.size()", equalTo(3));

    }
}
