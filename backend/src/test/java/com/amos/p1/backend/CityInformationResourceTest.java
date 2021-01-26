package com.amos.p1.backend;

import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;


@TestPropertySource(properties = "app.scheduling.enable=false")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityInformationResourceTest {
        @LocalServerPort
        private int port;
        private String base;

        //TODO Add Dummy Data
        @BeforeEach
        void setUp() {
            this.base = "http://localhost:" + port + "/withDatabase";
        }

        /**
         * Status Code: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
         */
        @Test
        void testGetStatus200ForGetAllCityInformation(){
            given()
                    .param("","")
            .when()
                .get(base + "/cityinformation")
            .then()
                .statusCode(200);
        }

    @Test
    void testGetAllCityInformation(){
        List<CityInformation> allCityInformation = given()
                .param("", "") // parameter in the url
                .when()
                .get(base + "/cityinformation") // Url that you want to test
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", CityInformation.class); //Extract the root json element to a list of String

        assertThat(allCityInformation.size(), equalTo(2));
    }

        @Test
        void testGetAllCityInformationWithUnMarshalling(){
            List<CityInformation> allCityInformation = given()
                .param("", "") // parameter in the url
            .when()
                .get(base + "/cityinformation") // Url that you want to test
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", CityInformation.class); //Extract the root json element to a list of String

            //TODO
            assertThat(allCityInformation.get(0), equalTo("2020-12-19 12:00"));
            assertThat(allCityInformation.get(1), equalTo("2020-12-19 13:00"));
        }

    }

