package com.amos.p1.backend;


import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Location;
import com.amos.p1.backend.database.MyRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceWithDatabaseTest {

    @LocalServerPort
    private int port;
    private String base;

    @BeforeAll
    public static void init() {

        System.out.println("setting Database properties");
        MyRepo.setUseTestDatabase(false);
    }


    @BeforeEach
    void setUp() {
        System.out.println("reintialising Database");
        MyRepo.dropAll();
        this.base = "http://localhost:" + port + "/withDatabase";
    }

    /**
     * Status Code: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
     */
    @Test
    void testIncidents() {
        String s = given()
                .param("city", "Berlin")
                .when()
                .get(base + "/incidents")
                .then().extract().asString();


        assertThat(s, notNullValue());
        //System.out.println(Helper.getPrettyJsonList(s));
    }

    @Test
    void testIncidentsWithTypes(){
        String s = given()
                .param("city", "Berlin")
                .param("types", "1,10")
            .when()
                .get(base + "/incidentsWithTypes")
            .then()
                .extract()
                .asString();

        assertThat(s, notNullValue());
        System.out.println(Helper.getPrettyJsonList(s));
    }

    @Test
    void testIncidentsWithTypeListEmpty(){
        String s = given()
                .param("city", "Berlin")
                .param("types", "")
            .when()
                .get(base + "/incidentsWithTypes")
            .then()
                .extract()
                .asString();

        assertThat(s, notNullValue());
        System.out.println(Helper.getPrettyJsonList(s));
    }

    @Test
    void testIncidentByCityAndInvalidTimestamp(){
        //For example wrong format. Valid: "yyyy-MM-dd HH:mm". Invalid: ""yyyy-dd-MM HH:mm""
        throw new IllegalStateException("Not yet implemented yet. Sprint 7");
    }

    @Test
    void testTimeStampByCityNotInDatabase() {
        String s = given()
                .param("city", "Shanghai")
            .when()
                .get(base + "/timestamps")
            .then()
                .extract()
                .asString();

        assertEquals(s, "[]");
        System.out.println(Helper.getPrettyJsonList(s));    }

    @Test
    void testTimeStampByCity() {
        List<Incident> incidents = new ArrayList<Incident>();
        incidents.add(
                new Incident("222","baustelle","major",
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
                        "670000:690000,681234:691234",6.0,new Long(1)));
        MyRepo.insertIncident(incidents);

        String s = given()
                .param("city", "Berlin")
            .when()
                .get(base + "/timestamps")
            .then()
                .extract()
                .asString();

        assertEquals(s, "2020-05-01 12:30");
        System.out.println(Helper.getPrettyJsonList(s));
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
    void testComparison(){
        throw new IllegalStateException("Needs to be implemented");
    }
}
