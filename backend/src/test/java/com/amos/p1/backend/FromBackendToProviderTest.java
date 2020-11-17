package com.amos.p1.backend;

import com.amos.p1.backend.from_backend_to_provider.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * https://www.baeldung.com/rest-template
 */
public class FromBackendToProviderTest {

    @Test
    void testToString(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://dummy.restapiexample.com/api/v1/employee/1";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        System.out.println(response.getBody());
    }

    @Test
    void testToObject(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://dummy.restapiexample.com/api/v1/employee/1";
        ResponseEntity<Employee> response = restTemplate.getForEntity(url, Employee.class);

        Employee employee = response.getBody();

        System.out.println(employee);
    }
}
