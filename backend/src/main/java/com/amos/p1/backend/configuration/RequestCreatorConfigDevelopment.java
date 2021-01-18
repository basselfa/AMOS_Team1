package com.amos.p1.backend.configuration;

import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class RequestCreatorConfigDevelopment implements RequestCreatorConfig{

    public RequestCreatorConfigDevelopment(){
        System.out.println("Using development request creator");
    }

    @Override
    public RequestCreator getRequestCreator() {
        return new RequestCreatorDummyBerlinSmall();
    }
}
