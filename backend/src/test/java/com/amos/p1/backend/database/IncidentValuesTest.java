package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Insert a incident with all values set and test every value of incident
 */
public class IncidentValuesTest {

    private final Incident incidentFromDb;

    public IncidentValuesTest(){
        //Wipe database

        Incident incident = DummyIncident.createIncident();

        DatabaseTestHelper.insertIncident(incident);
        long dbId = incident.getId();

        incidentFromDb = DatabaseTestHelper.getIncidentById(dbId);
    }

    @Test
    void testNotNull(){
        assertThat(incidentFromDb, is(notNullValue()));
    }

    @Test
    void testType(){
        assertThat(incidentFromDb.getType(), equalTo(DummyIncident.TYPE));
    }

    @Test
    void testSize(){
        assertThat(incidentFromDb.getSize(), equalTo(DummyIncident.SIZE));
    }

    @Test
    void testDescription(){
        assertThat(incidentFromDb.getDescription(), equalTo(DummyIncident.DESCRIPTION));
    }

    @Test
    void testCity(){
        assertThat(incidentFromDb.getCity(), equalTo(DummyIncident.CITY));
    }

    @Test
    void testCountry(){
        assertThat(incidentFromDb.getCountry(), equalTo(DummyIncident.COUNTRY));
    }

    @Test
    void testLengthInMeter(){
        assertThat(incidentFromDb.getLengthInMeter(), equalTo(DummyIncident.LENGTH_IN_METER));
    }

    @Test
    void testStartPositionLatitude(){
        assertThat(incidentFromDb.getStartPositionLatitude(), equalTo(DummyIncident.START_POSITION_LATITUDE));
    }

    @Test
    void testStartPositionLongitude(){
        assertThat(incidentFromDb.getStartPositionLongitude(), equalTo(DummyIncident.START_POSITION_LONGITUDE));
    }

    @Test
    void testStartPositionStreet(){
        assertThat(incidentFromDb.getStartPositionStreet(), equalTo(DummyIncident.START_POSITION_STREET));
    }

    @Test
    void testEndPositionLatitude(){
        assertThat(incidentFromDb.getEndPositionLatitude(), equalTo(DummyIncident.END_POSITION_LATITUDE));
    }

    @Test
    void testEndPositionLongitude(){
        assertThat(incidentFromDb.getEndPositionLongitude(), equalTo(DummyIncident.END_POSITION_LONGITUDE));
    }

    @Test
    void testEndPositionStreet(){
        assertThat(incidentFromDb.getEndPositionStreet(), equalTo(DummyIncident.END_POSITION_STREET));
    }

    @Test
    void testVerified(){
        assertThat(incidentFromDb.getVerified(), equalTo(DummyIncident.VERIFIED));
    }

    @Test
    void testProvider(){
        assertThat(incidentFromDb.getProvider(), equalTo(DummyIncident.PROVIDER));
    }

    @Test
    void testEntryTime(){
        assertThat(incidentFromDb.getEntryTime(), equalTo(DummyIncident.ENTRY_TIME));
    }

    @Test
    void testEndTime(){
        assertThat(incidentFromDb.getEndTime(), equalTo(DummyIncident.END_TIME));
    }

    @Test
    void testEdges(){
        assertThat(incidentFromDb.getEdges(), equalTo(DummyIncident.EDGES));
    }

}
