package com.amos.p1.backend.service;

import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
public class ProviderIntervalRequest {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final ProviderNormalizer providerNormalizer = new ProviderNormalizer();

    // Will be runned on startup
    // 1000 ms * 60 * 60 = 1 hour
    @Scheduled(fixedRate = 3600000)
    public void providerCronJob() {
        System.out.println("The time is now " + dateFormat.format(new Date()));

        List<Request> requests = providerNormalizer.parseCurrentRequest();

        for (Request request : requests) {
            request.setRequestTime(LocalDateTime.now());

            System.out.println("Save here incidents. City: " + request.getCityName() +" Amount: " + request.getIncidents().size());
            MyRepo.insertRequest(request);
            System.out.println("Sucessfully saved everything");
        }

    }
}
