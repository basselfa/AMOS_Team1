package com.amos.p1.backend.service.provider;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HereRequestTest {

    ProviderRequest providerRequest;
    ProviderRequest providerRequestDummy;

    public HereRequestTest(){
        providerRequest = new HereRequest();
        providerRequestDummy = new HereRequestDummy();
    }

    @Test
    void testRequestStringNotNull(){
//        fail(); // Fail because we dont want to overuse the API
        String json = providerRequest.request("52.5542", "13.2823", "52.4721",  "13.5422");

        assertThat(json, notNullValue());
    }

    @Test
    void testRequestDummyStringNotNull(){
        String json = providerRequestDummy.request("52.5542", "13.2823", "52.4721",  "13.5422");

        assertThat(json, notNullValue());
    }


}
