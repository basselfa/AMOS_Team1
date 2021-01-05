package com.amos.p1.backend;


import com.amos.p1.backend.database.MyRepo;
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
    void testIncidents(){
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
    void testTimeStampByCityNotInDatabase(){
        throw new IllegalStateException("Not yet implemented yet. Sprint 7");
    }

    @Test
    void testGetAllCities(){
        throw new IllegalStateException("Not yet implemented yet. Sprint 7");
    }
}
