package com.amos.p1.backend.rest;

import com.amos.p1.backend.database.MyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = "app.scheduling.enable=false")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class RefreshResourceTest {

    private static final Logger log = LoggerFactory.getLogger(RefreshResourceTest.class);

    @LocalServerPort
    private int port;
    private String base;

    @BeforeEach
    void setUp() throws InterruptedException {
        this.base = "http://localhost:" + port + "/withDatabase";
        Thread.sleep(1000);// need to wait until everything is saved. I know its a code smell! For the next person: dont use active profiles that add new data in the constructor
        MyRepo.dropAll();
    }

    @Test
    void testRefresh() {

        List<String> timeStamps = given()
                .param("city", "Berlin")
            .when()
                .get(base + "/timestamps")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", String.class);
        assertThat(timeStamps, hasSize(0));


        given()
        .when()
            .get(base + "/refresh")
        .then();


        List<String> timeStampsAfterRefresh = given()
                .param("city", "Berlin")
            .when()
                .get(base + "/timestamps")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", String.class);

        assertThat(timeStampsAfterRefresh, hasSize(1));
    }
}