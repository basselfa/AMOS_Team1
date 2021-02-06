package com.amos.p1.backend;


import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesSameTimeStamp;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestPropertySource(properties = "app.scheduling.enable=false")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceWithDatabase5CitiesTest {

    private static final Logger log = LoggerFactory.getLogger(ResourceWithDatabase5CitiesTest.class);

    @LocalServerPort
    private int port;
    private String base;

    Request berlinRequest;


    public ResourceWithDatabase5CitiesTest() {

        MyRepo.setUseTestDatabase(true);
        MyRepo.dropAll();

        //Adding dummy data to database
        RequestCreatorDummy requestCreator = new RequestCreatorDummy5CitiesSameTimeStamp();

        List<Request> requests = requestCreator.buildRequests();
        log.info("Request sucessfully builded");
        for (Request request : requests) {
            MyRepo.insertRequest(request);
            log.info("Request saved for: " + request.getCityName() + " " + request.getRequestTime());
        }
        log.info("Saved everything");

        berlinRequest = requestCreator.getRequest("Berlin", LocalDateTime.of(2020, 1, 14, 10, 0, 0));
        assertThat(berlinRequest, not(nullValue()));
    }

    @BeforeEach
    void setup(){
        this.base = "http://localhost:" + port + "/withDatabase";
    }

    @Test
    void testIncidentsByCityAndTimestamp(){
        List<Incident> incidents =
            given()
                .param("city", "Berlin")
                .param("timestamp", "2020-01-14 10:00")
            .when()
                .get(base + "/incidents")
            .then()
                .extract()
                .body()
                .jsonPath()
                .getList(".", Incident.class);

        log.info("" + incidents);
        assertThat(incidents, hasSize(49 + 58)); // Other number. Need to be evaluated
    }
}
