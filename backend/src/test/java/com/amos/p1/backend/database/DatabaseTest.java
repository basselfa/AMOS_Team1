package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class DatabaseTest {


    @Test
    void testRequestDatabaseConnection() {
MyRepo.setUseTestDatabase(true);
        List<Incident> incidents = new ArrayList<Incident>();
        incidents.add(
                new Incident("222","baustelle","major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "dummy",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234",6.0,new Long(1)));
        incidents.add(
                new Incident("333","baustelle","major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "dummy",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234",6.0,new Long(1)));

        Request request = new Request();
        request.setRequestTime(LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0));
        request.setIncidents(incidents);

        MyRepo.insertRequest(request);


        System.out.println(MyRepo.getRequest(LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0)));
    }



}
