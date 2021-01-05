package com.amos.p1.backend;


import com.amos.p1.backend.data.Location;
import com.amos.p1.backend.database.MyRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceWithDatabaseTest {

    @LocalServerPort
    private int port;
    private String base;

    @BeforeAll
    public static void init() {

        System.out.println("setting Database properties");
        MyRepo.setUseTestDatabase(true);
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
    void testIncidentByCityAndInvalidTimestamp() {
        //For example wrong format. Valid: "yyyy-MM-dd HH:mm". Invalid: ""yyyy-dd-MM HH:mm""
        throw new IllegalStateException("Not yet implemented yet. Sprint 7");
    }

    @Test
    void testTimeStampByCityNotInDatabase() {
        throw new IllegalStateException("Not yet implemented yet. Sprint 7");
    }

    @Test
    void testGetAllCities() throws JSONException {
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

}
