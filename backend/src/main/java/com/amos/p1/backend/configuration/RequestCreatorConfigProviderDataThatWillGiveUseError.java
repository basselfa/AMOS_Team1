package com.amos.p1.backend.configuration;

import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesWithError;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesWithError2;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy5CitiesWithError3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("default")
public class RequestCreatorConfigProviderDataThatWillGiveUseError implements RequestCreatorConfig{

    private static final Logger log = LoggerFactory.getLogger(RequestCreatorConfigProviderDataThatWillGiveUseError.class);

    public RequestCreatorConfigProviderDataThatWillGiveUseError(){
        log.info("Using development request creator");

        log.info("First request batch");
        RequestCreator requestCreator = new RequestCreatorDummy5CitiesWithError();
        List<Request> requests = requestCreator.buildRequests();
        for (Request request : requests) {
            log.info("Save incidents into db. City: " + request.getCityName() +" Amount: " + request.getIncidents().size());
            MyRepo.insertRequest(request);
            log.info("Sucessfully saved");
        }
        log.info("Saved everything for first batch");

        log.info("Second request batch");
        RequestCreator requestCreator2 = new RequestCreatorDummy5CitiesWithError2();
        List<Request> requests2 = requestCreator2.buildRequests();
        for (Request request : requests2) {
            log.info("Save incidents into db. City: " + request.getCityName() +" Amount: " + request.getIncidents().size());
            MyRepo.insertRequest(request);
            log.info("Sucessfully saved");
        }
        log.info("Saved everything for second batch");
    }

    @Override
    public RequestCreator getRequestCreator() {
        return new RequestCreatorDummy5CitiesWithError3();
    }
}
