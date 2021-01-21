package com.amos.p1.backend.service;

import com.amos.p1.backend.configuration.RequestCreatorConfig;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
public class ProviderIntervalRequest {

    private final RequestCreatorConfig requestCreatorConfig;

    @Autowired
    public ProviderIntervalRequest(RequestCreatorConfig requestCreatorConfig){

        this.requestCreatorConfig = requestCreatorConfig;
    }

    /**
     *  Will be runned on startup
     *  1000 ms * 60 * 60 = 1 hour
     */
    @Scheduled(fixedRate = 3600000)
    public void providerCronJob() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowNoSeconds = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());

        System.out.println("The time is now " + nowNoSeconds);

        RequestCreator requestCreator = requestCreatorConfig.getRequestCreator();
        requestCreator.setTimeStamp(nowNoSeconds);
        List<Request> requests = requestCreator.buildRequests();

        for (Request request : requests) {
            System.out.println("Save incidents into db. City: " + request.getCityName() +" Amount: " + request.getIncidents().size());
            MyRepo.insertRequest(request);
            System.out.println("Sucessfully saved");
        }

        System.out.println("Sucessfully saved everything");
    }
}
