package com.amos.p1.backend.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dont need to be implemented. Spring Data JPA create a implementation on runtime
 *
 * https://spring.io/guides/gs/accessing-data-jpa/
 * https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
 */
@Repository
public interface ComparisonRepository extends CrudRepository<Comparison, Long> {

    List<Comparison> findBySource1(String source1);

}
