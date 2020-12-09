package com.amos.p1.backend.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Testing marshalling of incident (Incident -> Json)
 *
 *
 * Data objects like incident are called POJOs (Plain old java object). They have a private class attributes that can
 * be accessed by getter and setter methods. A constructor with zero argument is given.
 *
 * Jackson Marshalling: when POJOs are exposed via the rest endpoint the data structure will be transformed with
 * the Jackson marshalling. It will transform every object given by get-methods. I.e. Locations getLocations() will
 * be transformed to a json with this structure {locations: {...}}. The name of the json attribute is given in
 * the get method (getLocations -> locations, getDescriptionblabla -> descriptionblabla)
 *
 * Some times its not wanted to marshall every object given by the get-functions. So use @JsonIgnore annotation.
 *
 * To test json marshalling we will use ObjectMapper. It will marshall the POJOs to json. With hasJsonPath the json
 * path and its values can be tested. See tests.
 *
 *
 * More information:
 * - https://www.baeldung.com/java-pojo-class
 * - https://www.baeldung.com/jackson
 * - https://github.com/json-path/JsonPath/tree/master/json-path-assert
 */
public class IncidentMarshallingTest {

    private String json;

    public IncidentMarshallingTest(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Incident value = getIncident();
            json = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Incident getIncident() {
        Incident incident = new Incident();

        incident.setDescription("This is a cool description");
        incident.setProvider("1");

        Locations locations = getLocations();
        incident.setEdgesAsLocations(locations);

        //TODO: add length, ...

        return incident;
    }

    private Locations getLocations() {
        Location location1 = new Location();
        location1.setLatitude("1.2345");
        location1.setLongitude("2.3456");

        Location location2 = new Location();
        location2.setLatitude("3.4567");
        location2.setLongitude("4.5678");

        Locations locations = new Locations();
        locations.addLocation(location1);
        locations.addLocation(location2);

        return locations;
    }

    @Test
    void printJson() throws JSONException {
        JSONObject json = new JSONObject(this.json);

        System.out.println(json.toString(3));
    }

    @Test
    void testDescription(){
        assertThat(json, hasJsonPath("$.description", equalTo("This is a cool description")));
    }

    @Test
    void testLocationAmount(){
        assertThat(json, hasJsonPath("$.edgesAsLocations.locationsList", hasSize(2)));
    }

    @Test
    void testLocationIndex0(){
        assertThat(json, hasJsonPath("$.edgesAsLocations.locationsList[0].latitude", equalTo("1.2345")));
        assertThat(json, hasJsonPath("$.edgesAsLocations.locationsList[0].longitude", equalTo("2.3456")));
    }

    //TODO: add more tests
}
