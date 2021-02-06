package com.amos.p1.backend.configuration;

import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesSameTimeStamp;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import com.amos.p1.backend.service.requestcreator.RequestCreatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class RequestCreatorConfigProduction implements RequestCreatorConfig{

    private static final Logger log = LoggerFactory.getLogger(RequestCreatorConfigProduction.class);

    public RequestCreatorConfigProduction(){
        log.info("Using production request creator");
    }

    @Override
    public RequestCreator getRequestCreator() {
        return new RequestCreatorImpl();
    }
}
