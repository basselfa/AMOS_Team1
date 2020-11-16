package com.amos.p1.backend;

import com.amos.p1.backend.database.Comparison;
import com.amos.p1.backend.database.ComparisonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class DatabaseTest {

    @Autowired
    private ComparisonRepository repo;

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
