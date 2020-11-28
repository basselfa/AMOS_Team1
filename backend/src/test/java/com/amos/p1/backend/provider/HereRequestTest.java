package com.amos.p1.backend.provider;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HereRequestTest {

    ProviderRequest providerRequest;
    ProviderRequest providerRequestDummy;

    public HereRequestTest(){
        providerRequest = new HereRequest();
        providerRequestDummy = new HereRequestDummy();
    }

    @Test
    void testRequestStringNotNull(){
        String json = providerRequest.request("52.5542", "13.2823", "52.4721",  "13.5422");

        assertNotEquals(json, null);
    }

    @Test
    void testRequestDummyStringNotNull(){
        String json = providerRequestDummy.request("52.5542", "13.2823", "52.4721",  "13.5422");

        assertNotEquals(json, null);
    }


}
