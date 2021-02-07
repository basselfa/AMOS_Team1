package com.amos.p1.backend.service;

import com.amos.p1.backend.configuration.CityBoundingBoxServiceConfigDevelopment;
import com.amos.p1.backend.configuration.CityBoundingBoxServiceConfigProduction;
import com.amos.p1.backend.configuration.RequestCreatorConfigDevelopment;
import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.database.MyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProviderIntervalRequestCityInformationTest {


    @BeforeEach
    void setUp(){
        MyRepo.dropAll();
    }

    @Test
    void testCityBoundingBoxServiceConfigProductionAfterTwoIntervals(){

        final ProviderIntervalRequest providerIntervalRequest =
                new ProviderIntervalRequest(
                        new RequestCreatorConfigDevelopment(),
                        new CityBoundingBoxServiceConfigProduction()
                );

        providerIntervalRequest.providerCronJob();
        providerIntervalRequest.providerCronJob();

        List<CityInformation> cityInformation = MyRepo.getAllCityInformation();

        assertThat(cityInformation, hasSize(5));
    }

    @Test
    void testCityBoundingBoxServiceConfigDevelopmentAfterTwoIntervals(){
        final ProviderIntervalRequest providerIntervalRequest =
                new ProviderIntervalRequest(
                        new RequestCreatorConfigDevelopment(),
                        new CityBoundingBoxServiceConfigDevelopment()
                );

        providerIntervalRequest.providerCronJob();
        providerIntervalRequest.providerCronJob();

        List<CityInformation> cityInformation = MyRepo.getAllCityInformation();

        assertThat(cityInformation, hasSize(5));
    }
}
