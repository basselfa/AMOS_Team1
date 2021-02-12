package com.amos.p1.backend.service;

import com.amos.p1.backend.configuration.CityBoundingBoxServiceConfig;
import com.amos.p1.backend.configuration.RequestCreatorConfig;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.evaluation.Evaluation;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
public class ProviderIntervalRequest {

    private static final Logger log = LoggerFactory.getLogger(ProviderIntervalRequest.class);

    private final RequestCreatorConfig requestCreatorConfig;
    private final CityBoundingBoxServiceConfig cityBoundingBoxServiceConfig;

    @Autowired
    public ProviderIntervalRequest(RequestCreatorConfig requestCreatorConfig, CityBoundingBoxServiceConfig cityBoundingBoxServiceConfig){

        this.requestCreatorConfig = requestCreatorConfig;
        this.cityBoundingBoxServiceConfig = cityBoundingBoxServiceConfig;
    }

    /**
     *  Will be runned on startup
     *  1000 ms * 60 * 60 = 1 hour = 3600000 seconds
     */
    @Scheduled(fixedRate = 3200000)
    public void providerCronJob() {

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime nowNoSeconds = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), now.getMinute());

        log.info("The time is now " + nowNoSeconds);

        RequestCreator requestCreator = requestCreatorConfig.getRequestCreator();
        requestCreator.setTimeStamp(nowNoSeconds);
        requestCreator.setCityBoundingBoxes(cityBoundingBoxServiceConfig.getCityBoundBoxesService());

        List<Request> requests = requestCreator.buildRequests();

        for (Request request : requests) {
            log.info("Save incidents into db. City: " + request.getCityName() +" Amount: " + request.getIncidents().size());
            MyRepo.insertRequest(request);
            log.info("Sucessfully saved");
        }

        log.info("Sucessfully saved everything");
    }
}
