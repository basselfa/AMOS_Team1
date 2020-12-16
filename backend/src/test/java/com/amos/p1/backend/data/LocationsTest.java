package com.amos.p1.backend.data;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocationsTest {

    @Test
    void testEmptyLocationString(){
        Locations locations = new Locations("");

        List<Location> locationList = locations.getLocationsList();
        assertThat(locationList, hasSize(0));
    }

    @Test
    void testOneLocationStringLocationsSize(){
        Locations locations = new Locations("12.34:56.78");

        List<Location> locationList = locations.getLocationsList();
        assertThat(locationList, hasSize(1));
    }

    @Test
    void testTwoLocationsLocationsSize(){
        Locations locations = new Locations("12.34:56.78,124.34:556.78,");

        List<Location> locationList = locations.getLocationsList();
        assertThat(locationList, hasSize(2));
    }

    @Test
    void testOneLocationStringValues(){
        Locations locations = new Locations("12.34:56.78");

        List<Location> locationList = locations.getLocationsList();
        assertThat(locationList.get(0).getLatitude(), equalTo("12.34"));
        assertThat(locationList.get(0).getLongitude(), equalTo("56.78"));
    }

    @Test
    void testLocationStringWithCommaAtEnd(){
        assertThrows(IllegalStateException.class, () -> new Locations("12.34:56.78,"));
    }
}
