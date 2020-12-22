package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Comparison;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.ComparisonRepository;
import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class DatabaseTest {

    @Autowired
    private ComparisonRepository repo;



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

    @Test
    void test(){
        repo.save(new Comparison("Traffic jam1", "Crash1"));
        repo.save(new Comparison("Traffic jam2", "Crash2"));

        repo.findAll().forEach(result -> System.out.println(result));

        Optional<Comparison> byId = repo.findById(1L);
        Comparison comparison1 = byId.get();
        System.out.println(comparison1.getSource1());

    }

    @Test
    void testFindBySource1(){
        repo.save(new Comparison("Traffic jam1", "Crash1"));
        repo.save(new Comparison("Traffic jam2", "Crash2"));

        List<Comparison> traffic_jam1 = repo.findBySource1("Traffic jam1");
        traffic_jam1.forEach(System.out::println);
    }

}
