package com.amos.p1.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProviderIntervalRequestTest {

    private final ProviderIntervalRequest providerIntervalRequest = new ProviderIntervalRequest();

    @BeforeEach
    void setUp(){
        clearDatabase();
    }

    private void clearDatabase() {
        //TODO
    }

    @Test
    void testSavingToDatabase(){
        providerIntervalRequest.providerCronJob();

        //TODO check database
    }

}
