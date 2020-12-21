package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ProviderIntervalRequestTest {

    private final ProviderIntervalRequest providerIntervalRequest = new ProviderIntervalRequest();

    @BeforeAll
    public static void init() {

        System.out.println("setting Database properties");
        MyRepo.setUseTestDatabase(true);
    }

    @BeforeEach
    void setUp(){

        System.out.println("reintialising Database");
        MyRepo.dropAll();
    }


    @Test
    void testOneMoreRequestAfterCronjob(){
        long amountOfRequestsBeforeCronJob = getAmountOfRequests();

        providerIntervalRequest.providerCronJob();

        long amountOfRequestAfterCronJob = getAmountOfRequests();

        assertThat(amountOfRequestAfterCronJob - amountOfRequestsBeforeCronJob, equalTo(1));
    }

    @Test
    void testMoreIncidentsAfterCronjob(){
        long amountOfIncidentsBeforeCronjob = getAmountOfIncidents();

        providerIntervalRequest.providerCronJob();

        long amountOfIncidentsAfterCronJob = getAmountOfIncidents();

        assertThat(amountOfIncidentsAfterCronJob - amountOfIncidentsBeforeCronjob, greaterThan(0L));
    }

    @Test
    void testGettingBerlinTomTomIncidents(){
        List<Incident> berlinIncidents = providerIntervalRequest.getRecentTomTomIncidentsFromCity("Berlin");

        assertThat(berlinIncidents, hasSize(greaterThan(0)));
    }

    @Test
    void testExecutionOnInterval(){
        //TODO: change interval to 5 seconds so we can check after 5 seconds if the cronjob was executed
        fail("Need to be implemented");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
