package com.amos.p1.backend;

import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.data.CityInformationIncomingDTO;
import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.database.MyRepo;
import io.restassured.http.ContentType;
import org.junit.Before;
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

        @BeforeEach
        void setUp() {
            this.base = "http://localhost:" + port + "/withDatabase";
        }

        @BeforeEach
        void prepareDatabase() {
            MyRepo.dropAll();

            CityInformation cityInformation1 = new CityInformation();
            cityInformation1.setCityName("Berlin");
            cityInformation1.setCentreLongitude("52.50877");
            cityInformation1.setCentreLatitude("13.36916");
            cityInformation1.setSearchRadiusInMeter(5000);

            CityInformation cityInformation2 = new CityInformation();
            cityInformation2.setCityName("Berlin");
            cityInformation2.setCentreLongitude("52.50859");
            cityInformation2.setCentreLatitude("13.36971");
            cityInformation2.setSearchRadiusInMeter(5000);

            MyRepo.insertCityInformation(cityInformation1);
            MyRepo.insertCityInformation(cityInformation2);
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
            .when()
                .get(base + "/cityinformation") // Url that you want to test
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", CityInformation.class); //Extract the root json element to a list of String

            assertThat(allCityInformation.get(0).getCityName(), equalTo("Berlin"));
            assertThat(allCityInformation.get(1).getCentreLatitude(), equalTo("13.36971"));
        }

        @Test
        void testDeleteCityInformation(){
            List<CityInformation> allCityInformation = given()
            .when()
                .get(base + "/cityinformation") // Url that you want to test
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", CityInformation.class); //Extract the root json element to a list of String


            Long cityInformationId = allCityInformation.get(1).getId();

            given()
                .param("id", cityInformationId)
            .when()
                .delete(base + "/cityinformation") // Url that you want to test
            .then()
                .statusCode(200);

            allCityInformation = given()
                .when()
                    .get(base + "/cityinformation") // Url that you want to test
                .then()
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", CityInformation.class); //Extract the root json element to a list of String

            assertThat(allCityInformation.size(), equalTo(1));
        }

    @Test
    void testAddCityInformation(){

        CityInformationIncomingDTO cityInformationIncomingDTO = new CityInformationIncomingDTO();
        cityInformationIncomingDTO.setCityName("dortmund");
        cityInformationIncomingDTO.setCentreLatitude("52.50877");
        cityInformationIncomingDTO.setCentreLongitude("13.50877");
        cityInformationIncomingDTO.setSearchRadiusInMeter(63);

        given()
            .body(cityInformationIncomingDTO)
            .contentType(ContentType.JSON)
        .when()
            .post(base + "/cityinformation") // Url that you want to test
        .then()
            .statusCode(200);

        List<CityInformation> allCityInformation = given()
            .when()
                .get(base + "/cityinformation") // Url that you want to test
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", CityInformation.class); //Extract the root json element to a list of String

        assertThat(allCityInformation.size(), equalTo(3));
    }

    @Test
    void testAddCityInformationWithUnmarshalling(){

        CityInformationIncomingDTO cityInformationIncomingDTO = new CityInformationIncomingDTO();
        cityInformationIncomingDTO.setCityName("Berlin");
        cityInformationIncomingDTO.setCentreLatitude("10.111");
        cityInformationIncomingDTO.setCentreLongitude("12.111");
        cityInformationIncomingDTO.setSearchRadiusInMeter(2222);

        given()
            .body(cityInformationIncomingDTO)
            .contentType(ContentType.JSON)
        .when()
            .post(base + "/cityinformation") // Url that you want to test
        .then()
            .statusCode(200);

        List<CityInformation> allCityInformation = given()
            .when()
                .get(base + "/cityinformation") // Url that you want to test
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", CityInformation.class); //Extract the root json element to a list of String

        assertThat(allCityInformation.get(2).getCityName(), equalTo("Berlin"));
        assertThat(allCityInformation.get(2).getCentreLatitude(), equalTo("10.111"));
        assertThat(allCityInformation.get(2).getCentreLongitude(), equalTo("12.111"));
        assertThat(allCityInformation.get(2).getSearchRadiusInMeter(), equalTo(2222));
    }

}