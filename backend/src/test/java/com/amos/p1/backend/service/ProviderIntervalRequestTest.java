package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizer;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerDummyBerlinSmall;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ProviderIntervalRequestTest {

    private final ProviderIntervalRequest providerIntervalRequest = new ProviderIntervalRequest();


    public ProviderIntervalRequestTest(){
        MyRepo.setUseTestDatabase(true);
    }

    @BeforeEach
    void setUp(){

        System.out.println("reintialising Database");
        MyRepo.dropAll();

        //Adding dummy data to database
        ProviderNormalizer providerNormalizer = new ProviderNormalizerDummyBerlinSmall();
        List<Request> requests = providerNormalizer.parseCurrentRequest();
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

    @Test
    void testExecutionOnInterval(){
        throw new IllegalStateException("Need to be implemented. Use awaitility library ");
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
