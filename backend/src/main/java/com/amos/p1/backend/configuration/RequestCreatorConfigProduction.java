package com.amos.p1.backend.configuration;

import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesSameTimeStamp;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import com.amos.p1.backend.service.requestcreator.RequestCreatorImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class RequestCreatorConfigProduction implements RequestCreatorConfig{

    public RequestCreatorConfigProduction(){
        System.out.println("Using production request creator");
    }

    @Override
    public RequestCreator getRequestCreator() {
        return new RequestCreatorImpl();
    }
}
