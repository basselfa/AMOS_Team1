package com.amos.p1.backend;


import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizer;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerDummy2Cities2TimeStamps;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerDummy2CitiesSameTimeStamp;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerDummyBerlinSmall;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestPropertySource(properties = "app.scheduling.enable=false")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourceWithDatabaseGenericTest {

    @LocalServerPort
    private int port;
    private String base;

    public ResourceWithDatabaseGenericTest() {

        MyRepo.setUseTestDatabase(true);
        MyRepo.dropAll();

        //Adding dummy data to database
        ProviderNormalizer providerNormalizer = new ProviderNormalizerDummy2Cities2TimeStamps();
        List<Request> requests = providerNormalizer.parseCurrentRequest();
        for (Request request : requests) {
            MyRepo.insertRequest(request);
        }
    }

    @BeforeEach
    void setup(){
        this.base = "http://localhost:" + port + "/withDatabase";
    }

    @Test
    void testComparisonEvaluationOverTime(){
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs =
                given()
                    .param("city", "Berlin")
                .when()
                    .get(base + "/comparisonEvaluationOverTime")
                .then()
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", ComparisonEvaluationDTO.class);

        Date date1 = new Date(2020, Calendar.JANUARY, 14);
        Date date2 = new Date(2020, Calendar.JANUARY, 15);

        assertThat(comparisonEvaluationDTOs, hasSize(2));

        Optional<ComparisonEvaluationDTO> date1ComparisonEvaluationDTO = comparisonEvaluationDTOs
                .stream()
                .filter(comparisonEvaluationDTO -> comparisonEvaluationDTO.getDate().equals(date1))
                .findAny();

        Optional<ComparisonEvaluationDTO> date2ComparisonEvaluationDTO = comparisonEvaluationDTOs
                .stream()
                .filter(comparisonEvaluationDTO -> comparisonEvaluationDTO.getDate().equals(date2))
                .findAny();


        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(9));
        assertThat(comparisonEvaluationDTOs.get(0).getHereIncidentsAmount(), equalTo(49));
        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(58));
    }


    @Test
    void testComparison(){
        List<EvaluationCandidate> evaluationCandidates =
                given()
                    .param("city", "Berlin")
                    .param("timestamp", "2020-01-01 00:00")
                .when()
                    .get(base + "/comparison")
                .then()
                    .extract()
                    .body()
                    .jsonPath()
                    .getList(".", EvaluationCandidate.class);

        assertThat(evaluationCandidates, hasSize(9));

        assertThat(evaluationCandidates.get(0).getHereIncident(), is(notNullValue()));
        assertThat(evaluationCandidates.get(0).getTomTomIncident(), is(notNullValue()));
        assertThat(evaluationCandidates.get(0).getScore(), is(not(0)));
        assertThat(evaluationCandidates.get(0).getConfidenceDescription(), not(equalTo("")));
    }

}
