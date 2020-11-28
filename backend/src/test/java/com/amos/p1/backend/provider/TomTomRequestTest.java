package com.amos.p1.backend.provider;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TomTomRequestTest {

    ProviderRequest providerRequest;
    ProviderRequest providerRequestDummy;

    public TomTomRequestTest(){
        providerRequest = new TomTomRequest();
        providerRequestDummy = new TomTomRequestDummy();

    }

    @Test
    void testRequestStringNotNull(){
        fail(); //Test working but dont want to exceed api

        String json = providerRequest.request("52.5542", "13.2823", "52.4721",  "13.5422");
        assertNotEquals(json, null);
        System.out.println(json);
    }

    @Test
    void testRequestDummyStringNotNull(){
        String json = providerRequestDummy.request("52.5542", "13.2823", "52.4721",  "13.5422");

        assertNotEquals(json, null);
        System.out.println(json);
    }
}
