package com.amos.p1.backend.configuration;

import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesWithError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class RequestCreatorConfigProviderDataThatWillGiveUseError implements RequestCreatorConfig{

    private static final Logger log = LoggerFactory.getLogger(RequestCreatorConfigProviderDataThatWillGiveUseError.class);

    public RequestCreatorConfigProviderDataThatWillGiveUseError(){
        log.info("Using development request creator");
    }

    @Override
    public RequestCreator getRequestCreator() {
        return new RequestCreatorDummy5CitiesWithError();
    }
}
