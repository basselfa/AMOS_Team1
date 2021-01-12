package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SearchRadiusMatcherTest {

    SearchRadiusMatcher radiusMatcher;

    @Test
    void testIdenticalIncident() {
        Incident incident1 = new Incident();
        incident1.setStartPositionLatitude("52.534080");
        incident1.setStartPositionLongitude("13.290938");
        incident1.setEndPositionLatitude("52.534576");
        incident1.setEndPositionLongitude("13.300873");

        Incident incident2 = new Incident();
        incident2.setStartPositionLatitude("52.534080");
        incident2.setStartPositionLongitude("13.290938");
        incident2.setEndPositionLatitude("52.534576");
        incident2.setEndPositionLongitude("13.300873");

        radiusMatcher = new SearchRadiusMatcher(incident1, incident2);
        assertThat(radiusMatcher.getConfidence(), equalTo(2));
        assertThat(radiusMatcher.isDropped(), equalTo(false));
        assertThat(radiusMatcher.getLegthDifferencePercentage(), equalTo(0.0));
        assertThat(radiusMatcher.getFromAndToDistanceSum(), equalTo(0.0));
    }

    @Test
    void testReserveIncident() {
        Incident incident1 = new Incident();
        incident1.setStartPositionLatitude("52.534080");
        incident1.setStartPositionLongitude("13.290938");
        incident1.setEndPositionLatitude("52.534576");
        incident1.setEndPositionLongitude("13.300873");

        Incident incident2 = new Incident();
        incident2.setEndPositionLatitude("52.534080");
        incident2.setEndPositionLongitude("13.290938");
        incident2.setStartPositionLatitude("52.534576");
        incident2.setStartPositionLongitude("13.300873");

        radiusMatcher = new SearchRadiusMatcher(incident1, incident2);
        assertThat(radiusMatcher.getConfidence(), equalTo(0));
        assertThat(radiusMatcher.isDropped(), equalTo(true));
        assertThat(radiusMatcher.getLegthDifferencePercentage(), equalTo(0.0));
        assertThat(radiusMatcher.getFromAndToDistanceSum(), equalTo(1348.4274553637495));
    }

    @Test
    void testOneMatchingPoint() {
        Incident incident1 = new Incident();
        incident1.setStartPositionLatitude("52.534080");
        incident1.setStartPositionLongitude("13.290938");
        incident1.setEndPositionLatitude("52.534576");
        incident1.setEndPositionLongitude("13.300873");

        Incident incident2 = new Incident();
        incident2.setStartPositionLatitude("52.534080");
        incident2.setStartPositionLongitude("13.290938");
        incident2.setEndPositionLatitude("52.534576");
        incident2.setEndPositionLongitude("14.300873");

        radiusMatcher = new SearchRadiusMatcher(incident1, incident2);
        assertThat(radiusMatcher.getConfidence(), equalTo(1));
        assertThat(radiusMatcher.isDropped(), equalTo(false));
        assertThat(radiusMatcher.getLegthDifferencePercentage(), equalTo(10031.280171999533));
        assertThat(radiusMatcher.getFromAndToDistanceSum(), equalTo(67634.13938629092));
    }


    @Test
    void testNoMatchingPoint() {
        Incident incident1 = new Incident();
        incident1.setStartPositionLatitude("52.534080");
        incident1.setStartPositionLongitude("13.290938");
        incident1.setEndPositionLatitude("52.534576");
        incident1.setEndPositionLongitude("13.300873");

        Incident incident2 = new Incident();
        incident2.setStartPositionLatitude("53.534576");
        incident2.setStartPositionLongitude("12.300873");
        incident2.setEndPositionLatitude("53.534080");
        incident2.setEndPositionLongitude("12.290938");

        radiusMatcher = new SearchRadiusMatcher(incident1, incident2);
        assertThat(radiusMatcher.getConfidence(), equalTo(0));
        assertThat(radiusMatcher.isDropped(), equalTo(true));
        assertThat(radiusMatcher.getLegthDifferencePercentage(), equalTo(2.3300313648132946));
        assertThat(radiusMatcher.getFromAndToDistanceSum(), equalTo(259486.14650060693));
    }

}