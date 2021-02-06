package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.normalization.TomTom.BerlinTest;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesSameTimeStamp;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AggregatorFromDatabase5CitiesTest {

    private static final Logger log = LoggerFactory.getLogger(AggregatorFromDatabase5CitiesTest.class);

    private final Aggregator aggregator = new AggregatorFromDatabase();
    private final Request berlinRequest;
    private final LocalDateTime berlinTimeStamp = LocalDateTime.of(2020, 1, 14, 10, 0, 0);


    public AggregatorFromDatabase5CitiesTest() {
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

        berlinRequest = requestCreator.getRequest("Berlin", berlinTimeStamp);
        assertThat(berlinRequest, not(nullValue()));
    }

    @Test
    void testGetIncidentsFromCity() {
        Instant start = Instant.now();
        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.of(berlinTimeStamp), Optional.empty());
        Instant end = Instant.now();

        Duration timeElapsed = Duration.between(start, end);
        log.info("Time taken: "+ timeElapsed.toMillis() +" miliseconds");

        assertThat(incidentList, hasSize(greaterThan(0)));                               // List not empty
        assertThat(incidentList, hasSize(equalTo(berlinRequest.getIncidents().size())));       // as long as input List TODO: Kang -> stimmt das so?

        for (int i = 0; i < incidentList.size(); i++) {
            assertThat(incidentList.get(i), equalTo(berlinRequest.getIncidents().get(i)));
        }
    }
}
