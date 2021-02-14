package com.amos.p1.backend.service;

import com.amos.p1.backend.configuration.CityBoundingBoxServiceConfigDevelopment;
import com.amos.p1.backend.configuration.RequestCreatorConfigDevelopment;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ProviderIntervalRequestDummyTest {

    private static final Logger log = LoggerFactory.getLogger(ProviderIntervalRequestDummyTest.class);

    private ProviderIntervalRequest providerIntervalRequest;

    public ProviderIntervalRequestDummyTest(){
        MyRepo.setUseTestDatabase(true);
    }

    @BeforeEach
    void setUp(){
        providerIntervalRequest = new ProviderIntervalRequest(
                new RequestCreatorConfigDevelopment(),
                new CityBoundingBoxServiceConfigDevelopment()
        );

        log.info("reintialising Database");
        MyRepo.dropAll();

        //Adding dummy data to database
        RequestCreator requestCreator = new RequestCreatorDummyBerlinSmall();
        List<Request> requests = requestCreator.buildRequests();
        for (Request request : requests) {
            MyRepo.insertRequest(request);
        }
    }

    @Test
    void testOneMoreRequestAfterCronjob(){
        long amountOfRequestsBeforeCronJob = getAmountOfRequests();

        providerIntervalRequest.providerCronJob();

        long amountOfRequestAfterCronJob = getAmountOfRequests();

        assertThat(amountOfRequestAfterCronJob - amountOfRequestsBeforeCronJob, equalTo(1L));
    }

    @Test
    void testMoreIncidentsAfterCronjob(){
        long amountOfIncidentsBeforeCronjob = getAmountOfIncidents();

        providerIntervalRequest.providerCronJob();

        long amountOfIncidentsAfterCronJob = getAmountOfIncidents();

        assertThat(amountOfIncidentsAfterCronJob - amountOfIncidentsBeforeCronjob, greaterThan(0L));
    }

    private Long getAmountOfRequests() {
        return MyRepo.getEntityManager()
                .createQuery("SELECT COUNT(r) FROM Request r", Long.class)
                .getSingleResult();
    }

    private Long getAmountOfIncidents() {
        return MyRepo.getEntityManager()
                .createQuery("SELECT COUNT(r) FROM Incident r", Long.class)
                .getSingleResult();
    }
}
