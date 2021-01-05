package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class RequestTest {

    private final static LocalDateTime LOCAL_DATE_TIME_DUMMY = LocalDateTime.of(2020, 10, 30, 16, 30);
    @BeforeAll
    public static void init() {

        System.out.println("setting Database properties");
        MyRepo.setUseTestDatabase(true);
    }

    @BeforeEach
    void setUp(){

        System.out.println("reintialising Database");
        MyRepo.dropAll();
    }



    @Test
    void testInsertRequestNoIncident() {
        Request request = getDummyRequestWithIncidents(null);
        MyRepo.insertRequest(request);

        Request requestFromDatabase = MyRepo.getRequest(LOCAL_DATE_TIME_DUMMY);
        assertThat(requestFromDatabase, is(notNullValue()));
    }

    @Test
    void testInsertRequestOneIncident(){
        Request request = getDummyRequestWithOneDummyIncident();
        MyRepo.insertRequest(request);

        Request requestFromDatabase = MyRepo.getRequest(LOCAL_DATE_TIME_DUMMY);
        assertThat(requestFromDatabase, is(notNullValue()));
    }

    @Test
    void testInsertRequest100Incidents(){

        List<Incident> incidents = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Incident incident = DummyIncident.createIncident();
            incident.setCity("City " + i);
            incidents.add(incident);
        }
        Request request = getDummyRequestWithIncidents(incidents);
        MyRepo.insertRequest(request);

        Request requestFromDatabase = MyRepo.getRequest(LOCAL_DATE_TIME_DUMMY);
        assertThat(requestFromDatabase, is(notNullValue()));
    }

    @Test
    void testNoIncidentInDatabaseAfterDeletingInRequestObject(){
        Request request = getDummyRequestWithOneDummyIncident();
        MyRepo.insertRequest(request);
        Incident incident = request.getIncidents().get(0);
        long firstIncidentId = incident.getId();

        Request requestFromDatabase = MyRepo.getRequest(LOCAL_DATE_TIME_DUMMY);
        requestFromDatabase.getIncidents().clear();
        requestFromDatabase.getIncidents().add(DummyIncident.createIncident());
        MyRepo.insertRequest(requestFromDatabase);

        List<Incident> incidentsFromDatabase = DatabaseTestHelper.getIncidentListById(firstIncidentId);
        assertThat(incidentsFromDatabase, hasSize(0));
    }

    @Test
    void TestDeleteRequest(){
        Request request = getDummyRequestWithOneDummyIncident();
        MyRepo.insertRequest(request);
        Incident incident = request.getIncidents().get(0);
        long dbId = incident.getId();

        //TODO delete request
        fail();
    }

    private Request getDummyRequestWithIncidents(List<Incident> incidents) {
        Request request = new Request();
        request.setRequestTime(LOCAL_DATE_TIME_DUMMY);
        request.setIncidents(incidents);

        return  request;
    }

    private Request getDummyRequestWithOneDummyIncident() {
        Incident incident = DummyIncident.createIncident();
        List<Incident> incidents = new ArrayList<>();
        incidents.add(incident);

        return getDummyRequestWithIncidents(incidents);
    }

}