package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;
import org.aspectj.lang.annotation.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

public class IncidentTest {
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
    void testInsertEmptyIncidentGetNotNull(){
        Incident incident = new Incident();

        DatabaseTestHelper.insertIncident(incident);
        long dbId = incident.getId();

        Incident incidentFromDb = DatabaseTestHelper.getIncidentById(dbId);

        assertThat(incidentFromDb, is(notNullValue()));
    }

    @Test
    void testInsertOnlyDescription(){
        Incident incident = new Incident();
        String description = "It's wednesday my dudes";
        incident.setDescription(description);

        DatabaseTestHelper.insertIncident(incident);
        long dbId = incident.getId();

        Incident incidentFromDb = DatabaseTestHelper.getIncidentById(dbId);

        assertThat(incidentFromDb.getDescription(), equalTo(description));
    }

    @Test
    void testEqualIdAfterTwoInsertion(){
        Incident incident = DummyIncident.createIncident();

        DatabaseTestHelper.insertIncident(incident);
        long dbId0 = incident.getId();

        DatabaseTestHelper.insertIncident(incident);
        long dbId1 = incident.getId();

        assertThat(dbId1, equalTo(dbId0));
    }

    @Test
    void testOnlyOneItemInDatabaseAfterTwoInsertionId(){
        Incident incident = DummyIncident.createIncident();

        DatabaseTestHelper.insertIncident(incident);
        DatabaseTestHelper.insertIncident(incident);
        long dbId = incident.getId();

        List<Incident> incidents = DatabaseTestHelper.getIncidentListById(dbId);
        assertThat(incidents, hasSize(1));
    }

    @Test
    void testDeleteIncident(){
        Incident incident = DummyIncident.createIncident();
        DatabaseTestHelper.insertIncident(incident);
        long dbId = incident.getId();

        //TODO: delete incident
        fail();

        List<Incident> incidents = DatabaseTestHelper.getIncidentListById(dbId);
        assertThat(incidents, hasSize(0));
    }

}
