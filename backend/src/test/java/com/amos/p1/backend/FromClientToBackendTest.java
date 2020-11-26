package com.amos.p1.backend;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FromClientToBackendTest {

    @LocalServerPort
    private int port;
    private String base;

    @BeforeEach
    void setUp() {
        this.base = "http://localhost:" + port;
    }

    /**
     * Status Code: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
     */
    @Test
    void testGetStatus200ByValidCitySearch(){
        given()
            .param("city", "berlin")
        .when()
            .get(base + "/search")
        .then()
            .statusCode(200);
    }

    @Test
    void testContentTypeCitySearch(){
        given()
            .param("city", "berlin")
        .when()
            .get(base + "/search")
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
            .get(base + "/search")
        .then()
            .body("incidents.size()", is(2));
    }

    @Test
    void testGetSpecificIncidentFromBerlinWithGroovyPath(){
        given()
            .param("city", "berlin")
        .when()
            .get(base + "/search")
        .then()
            .body("incidents[0].locationX", is(10))
            .body("incidents[0].locationY", is(20))
            .body("incidents[0].type", is("Traffic jam"));
    }
}
