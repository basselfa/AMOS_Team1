package com.amos.p1.backend.service;

import com.amos.p1.backend.configuration.*;
import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.data.Location;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class ProviderIntervalRequestRealTest {

    private final ProviderIntervalRequest providerIntervalRequest =
            new ProviderIntervalRequest(
                    new RequestCreatorConfigProduction(),
                    new CityBoundingBoxServiceConfigRealData()
            );


    public ProviderIntervalRequestRealTest(){
        MyRepo.setUseTestDatabase(true);
    }

    @BeforeEach
    void setUp(){

        System.out.println("reintialising Database");
        MyRepo.dropAll();

        CityInformation cityInformation1 = new CityInformation();
        cityInformation1.setCityName("Berlin");
        cityInformation1.setCentreLatitude("52.516875");
        cityInformation1.setCentreLongitude("13.378852");
        cityInformation1.setSearchRadiusInMeter(1000);

        CityInformation cityInformation2 = new CityInformation();
        cityInformation2.setCityName("Munich");
        cityInformation2.setCentreLatitude("48.140499");
        cityInformation2.setCentreLongitude("11.577914");
        cityInformation2.setSearchRadiusInMeter(1000);

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
