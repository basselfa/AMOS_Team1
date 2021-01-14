package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AngleMatcherTest {

    AngleMatcher angleMatcher;

    @Test
    void testMatchingAngleUnder15Degree(){

        Incident incident1 = new Incident();
        incident1.setStartPositionLatitude("52.534080");
        incident1.setStartPositionLongitude("13.290938");
        incident1.setEndPositionLatitude("52.534576");
        incident1.setEndPositionLongitude("13.300873");
                
        Incident incident2 = new Incident();
        incident2.setStartPositionLatitude("52.533689");
        incident2.setStartPositionLongitude("13.290952");
        incident2.setEndPositionLatitude("52.533767");
        incident2.setEndPositionLongitude("13.301173");

        angleMatcher = new AngleMatcher(incident1, incident2);
        assertThat(angleMatcher.getConfidence(), equalTo(1));
        assertThat(angleMatcher.isDropped(), equalTo(false));
        assertThat(angleMatcher.getDegree(), equalTo(2.4208526611328125)); // TODO: needs other degree
    }

    @Test
    void testMisMatchAngleBetween22Point5And30Degree(){
        Incident incident1 = new Incident();
        incident1.setStartPositionLatitude("52.534080");
        incident1.setStartPositionLongitude("13.290938");
        incident1.setEndPositionLatitude("52.534576");
        incident1.setEndPositionLongitude("13.300873");

        Incident incident2 = new Incident();
        incident2.setStartPositionLatitude("52.533689");
        incident2.setStartPositionLongitude("13.290952");
        incident2.setEndPositionLatitude("52.531070");
        incident2.setEndPositionLongitude("13.298252");

        angleMatcher= new AngleMatcher(incident1, incident2);
        assertThat(angleMatcher.getConfidence(), equalTo(-1));
        assertThat(angleMatcher.isDropped(), equalTo(false));
        assertThat(angleMatcher.getDegree(), equalTo(22.594406127929688));
    }

    @Test
    void testMisMatchOver30Degree(){
        Incident incident1 = new Incident();
        incident1.setStartPositionLatitude("52.534080");
        incident1.setStartPositionLongitude("13.290938");
        incident1.setEndPositionLatitude("52.534576");
        incident1.setEndPositionLongitude("13.300873");

        Incident incident2 = new Incident();
        incident2.setStartPositionLatitude("52.533689");
        incident2.setStartPositionLongitude("13.290952");
        incident2.setEndPositionLatitude("52.529486");
        incident2.setEndPositionLongitude("13.289921");

        angleMatcher= new AngleMatcher(incident1, incident2);
        assertThat(angleMatcher.isDropped(), equalTo(true));
        assertThat(angleMatcher.getDegree(), equalTo(106.64066314697266));
    }

    @Test
    void testMisMatch180Degree(){
        Incident incident1 = new Incident();
        incident1.setStartPositionLatitude("52.534080");
        incident1.setStartPositionLongitude("13.290938");
        incident1.setEndPositionLatitude("52.534576");
        incident1.setEndPositionLongitude("13.300873");

        Incident incident2 = new Incident();
        incident2.setStartPositionLatitude("52.534576");
        incident2.setStartPositionLongitude("13.300873");
        incident2.setEndPositionLatitude("52.534080");
        incident2.setEndPositionLongitude("13.290938");

        angleMatcher= new AngleMatcher(incident1, incident2);
        assertThat(angleMatcher.isDropped(), equalTo(true));
        assertThat(angleMatcher.getDegree(), equalTo(180.0));
    }

}
