package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.ProviderIntervalRequest;
import com.amos.p1.backend.service.ProviderNormalizer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AggregatorFromDatabaseTest {

    Aggregator aggregator = new AggregatorFromDatabase();;
    @BeforeAll
    public static void init() {

        System.out.println("setting Database properties");
        MyRepo.setUseTestDatabase(true);
    }

    @BeforeEach
    void setUp(){
        System.out.println("reintialising Database");
        MyRepo.dropAll();

        //Adding dummy data to database
        ProviderIntervalRequest providerIntervalRequest = new ProviderIntervalRequest();
        providerIntervalRequest.setProviderNormalizer(new ProviderNormalizer(true));
        providerIntervalRequest.providerCronJob();
    }
    @Test
    void testGetIncidentsFromCity(){
        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());

        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testGetIncidentsFromCityAndWithType(){
        List<String> types = new ArrayList<>();
        types.add("1");
        types.add("10");

        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.empty(), Optional.of(types));

        assertThat(incidentList, hasSize(greaterThan(0)));

        incidentList.forEach(incident -> {
            assertThat(incident.getType(),
                    anyOf(equalTo("1"),
                            equalTo("10"))
            );
        });
    }

    @Test
    void testGetIncidentsFromCityAndWithTypeListEmpty(){
        List<String> types = new ArrayList<>();

        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.empty(), Optional.of(types));

        assertThat(incidentList, hasSize(greaterThan(0)));
    }


    @Test
    void testGetIncidentsFromCityAndTimeStamp(){

        List<Incident> incidents = new ArrayList<Incident>();
        LocalDateTime timestamp = LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0);

        incidents.add(
                new Incident("222","baustelle","major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "dummy",
                        timestamp,
                        timestamp,
                        "670000:690000,681234:691234",6.0,new Long(1)));
        MyRepo.insertIncident(incidents);

        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.of(timestamp), Optional.empty());

        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testGetIncidentsFromCityAndTimeStampNotInDatabase(){
        LocalDateTime timestamp = LocalDateTime.of(
                0, 1, 1,
                12, 30, 0);
        List<Incident> incidentList = aggregator.getIncidents("Berlin", Optional.of(timestamp), Optional.empty());

        assertThat(incidentList, is(empty()));
    }

    @Test
    void testGetTimestampsFromCity(){
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
        MyRepo.insertIncident(incidents);
        List<LocalDateTime> timestampList = aggregator.getTimestampsFromCity("Berlin");

        assertThat(timestampList, hasSize(greaterThan(0)));
    }

    @Test
    void testGetTimestampsFromCityNotInDatabase(){
       // List<LocalDateTime> timestampList = incidentAggregator.getTimestampsFromCity("DreamLand");

       // assertThat(timestampList, is(empty()));
    }

    @Test
    void testGetAllIncidents(){
        List<Incident> incidentList = aggregator.getAllData();

        assertThat(incidentList, hasSize(greaterThan(0)));
    }

    @Test
    void testMarshallingOneIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());

        String json = objectMapper.writeValueAsString(berlinIncidents.get(0));
        assertThat(json, notNullValue());

    }

    @Test
    void testMarshallingAllIncidentFromCity() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Incident> berlinIncidents = aggregator.getIncidents("Berlin", Optional.empty(), Optional.empty());

        String json = objectMapper.writeValueAsString(berlinIncidents);
        assertThat(json, notNullValue());

    }
}
