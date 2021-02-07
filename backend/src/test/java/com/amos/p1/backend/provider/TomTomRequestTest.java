package com.amos.p1.backend.provider;

import com.amos.p1.backend.normalization.ProviderLinksTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TomTomRequestTest {

    private static final Logger log = LoggerFactory.getLogger(TomTomRequestTest.class);

    ProviderRequest providerRequest;
    ProviderRequest providerRequestDummy;

    public TomTomRequestTest(){
        providerRequest = new TomTomRequest();
        providerRequestDummy = new TomTomRequestDummy();
    }

    @Test
    void testRequestStringNotNull(){
        fail(); // Fail because we dont want to overuse the API

        String json = providerRequest.request("52.5542", "13.2823", "52.4721",  "13.5422");
        assertThat(json, notNullValue());
    }

    @Test
    void testRequestDummyStringNotNull(){
        String json = providerRequestDummy.request("52.5542", "13.2823", "52.4721",  "13.5422");

        assertThat(json, notNullValue());
        log.info(json);
    }
}
