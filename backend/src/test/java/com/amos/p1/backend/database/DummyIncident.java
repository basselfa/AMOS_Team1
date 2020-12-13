package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DummyIncident {

    public final static String TRAFFIC_ID = "222";
    public final static String TYPE = "baustelle";
    public final static String SIZE = "major";
    public final static String DESCRIPTION = "Traffic jam in Bergmannstraße";
    public final static String CITY = "Berlin";
    public final static String COUNTRY = "germany";
    public final static double LENGTH_IN_METER = 1;
    public final static String START_POSITION_LATITUDE = "45.5";
    public final static String START_POSITION_LONGITUDE = "67.4";
    public final static String START_POSITION_STREET = "Bergmannstraße";
    public final static String END_POSITION_LATITUDE = "46.5";
    public final static String END_POSITION_LONGITUDE = "69.5";
    public final static String END_POSITION_STREET = "Bergmannstraße";
    public final static int VERIFIED = 1;
    public final static String PROVIDER = "dummy";
    public final static LocalDateTime ENTRY_TIME = LocalDateTime.of(2020, 5, 1, 12, 30, 0);
    public final static LocalDateTime END_TIME = LocalDateTime.of(2020, 5, 1,12, 30, 0);
    public final static String EDGES = "670000:690000,681234:691234";

    public static Incident createIncident() {
        Incident incident = new Incident();

        incident.setTrafficId(TRAFFIC_ID);
        incident.setType(TYPE);
        incident.setSize(SIZE);
        incident.setDescription(DESCRIPTION);
        incident.setCity(CITY);
        incident.setCountry(COUNTRY);
        incident.setLengthInMeter(LENGTH_IN_METER);
        incident.setStartPositionLatitude(START_POSITION_LATITUDE);
        incident.setStartPositionLongitude(START_POSITION_LONGITUDE);
        incident.setStartPositionStreet(START_POSITION_STREET);
        incident.setEndPositionLatitude(END_POSITION_LATITUDE);
        incident.setEndPositionLongitude(END_POSITION_LONGITUDE);
        incident.setEndPositionStreet(END_POSITION_STREET);
        incident.setVerified(VERIFIED);
        incident.setProvider(PROVIDER);
        incident.setEntryTime(ENTRY_TIME);
        incident.setEndTime(END_TIME);
        incident.setEdges(EDGES);

        return incident;
    }

}
