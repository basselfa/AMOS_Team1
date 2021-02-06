package com.amos.p1.backend.database;

import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.data.RequestMarshallingTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DatabaseTest {

    private static final Logger log = LoggerFactory.getLogger(DatabaseTest.class);

    @BeforeAll
    public static void init() {

        log.info("setting Database properties");
    //    MyRepo.setUseTestDatabase(true);
    }

    @BeforeEach
    void setUp(){

        log.info("reintialising Database");
        MyRepo.dropAll();
    }


    @Test
    void testRequestDatabaseConnection() {
        MyRepo.setUseTestDatabase(true);
        List<Incident> incidents = new ArrayList<Incident>();
        incidents.add(
                new Incident("222", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "tomtom",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));
        incidents.add(
                new Incident("333", "baustelle", "major",
                        "Traffic jam in Bergmannstraße",
                        "Berlin", "Germany",
                        "45.5", "67.4",
                        "Bergmannstraße",
                        "46.5", "69.5",
                        "Bergmannstraße",
                        1, "here",
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        LocalDateTime.of(
                                2020, 5, 1,
                                12, 30, 0),
                        "670000:690000,681234:691234", 6.0, new Long(1)));

        Request request = new Request();
        request.setRequestTime(LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0));
        request.setIncidents(incidents);

        MyRepo.insertRequest(request);


        log.info("" + MyRepo.getRequest(LocalDateTime.of(
                2020, 5, 1,
                12, 30, 0)));
    }

    @Test
    void testStoreCityInforamtion() {


        CityInformation information = new CityInformation();

        information.setCityName("Hamburg");

        information.setCentreLatitude("145.345346");
        information.setCentreLongitude("4.02344");
        information.setSearchRadiusInMeter(234);

        MyRepo.insertCityInformation(information);

        assertThat(MyRepo.getAllCityInformation().get(0), equalTo(information));
    }

    @Test
    void testDeleteCityInforamtion() {


        CityInformation information = new CityInformation();

        information.setCityName("Hamburg");

        information.setCentreLatitude("145.345346");
        information.setCentreLongitude("4.02344");
        information.setSearchRadiusInMeter(234);

        MyRepo.insertCityInformation(information);

        assertThat(MyRepo.getAllCityInformation().get(0), equalTo(information));

        MyRepo.deleteCityInformation(information.getId());

        assertThat(MyRepo.getAllCityInformation().size(),equalTo(0));
    }

}
