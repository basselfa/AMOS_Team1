package com.amos.p1.backend;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


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
    void testGetIncidentsSizeFromBerlin(){
        given()
            .param("city", "berlin")
        .when()
            .get(base + "/incidents")
        .then()
            .body("incidents.size()", is(3));
    }

    @Test
    void testGetAllData(){
        given()
        .when()
            .get(base + "/historization")
        .then()
            .body("incidents.size()", is(2));
    }

    @Test
    void testComparison(){
        throw new IllegalStateException("Needs to be implemented");
    }
}
