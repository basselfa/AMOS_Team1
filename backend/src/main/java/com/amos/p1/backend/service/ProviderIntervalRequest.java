package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizer;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerDummyBerlinSmall;
import com.amos.p1.backend.service.providernormalizer.ProviderNormalizerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Component
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
public class ProviderIntervalRequest {

    /**
     *  Will be runned on startup
     *  1000 ms * 60 * 60 = 1 hour
     */
    @Scheduled(fixedRate = 3600000)
    public void providerCronJob() {

        LocalDateTime now = LocalDateTime.now();
        System.out.println("The time is now " + now);

        ProviderNormalizer providerNormalizer = new ProviderNormalizerDummyBerlinSmall();
        List<Request> requests = providerNormalizer.parseCurrentRequest();

        for (Request request : requests) {
            System.out.println("Save incidents into db. City: " + request.getCityName() +" Amount: " + request.getIncidents().size());
            MyRepo.insertRequest(request);
            System.out.println("Sucessfully saved");
        }

        System.out.println("Sucessfully saved everything");
    }
}
