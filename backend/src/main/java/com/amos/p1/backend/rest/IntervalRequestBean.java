package com.amos.p1.backend.rest;

import com.amos.p1.backend.configuration.CityBoundingBoxServiceConfig;
import com.amos.p1.backend.configuration.RequestCreatorConfig;
import com.amos.p1.backend.service.ProviderIntervalRequest;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IntervalRequestBean {

    @Autowired
    RequestCreatorConfig requestCreatorConfig;

    @Autowired
    CityBoundingBoxServiceConfig cityBoundingBoxServiceConfig;


    @Bean
    public ProviderIntervalRequest getProviderIntervalRequest(){
        return new ProviderIntervalRequest(requestCreatorConfig, cityBoundingBoxServiceConfig);
    }

}
