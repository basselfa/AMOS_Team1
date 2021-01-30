package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DescriptionMatcherTest {

    DescriptionMatcher descriptionMatcher;

    @Test
    void testDescrContained() {
        Incident incident1 = new Incident();
        incident1.setDescription("road");
        incident1.setType(Incident.IncidentTypes.ACCIDENT.toString());

        Incident incident2 = new Incident();
        incident2.setDescription("Road closed");

        incident2.setType(Incident.IncidentTypes.ROADCLOSURE.toString());

        descriptionMatcher = new DescriptionMatcher(incident1, incident2);
        assertThat(descriptionMatcher.getConfidence(), equalTo(1));
        assertThat(descriptionMatcher.isDropped(), equalTo(false));
    }

    @Test
    void testTypeMatch() {
        Incident incident1 = new Incident();
        incident1.setDescription("ro333ad");
        incident1.setType(Incident.IncidentTypes.ACCIDENT.toString());

        Incident incident2 = new Incident();
        incident2.setDescription("Road closed");

        incident2.setType(Incident.IncidentTypes.ACCIDENT.toString());

        descriptionMatcher = new DescriptionMatcher(incident1, incident2);
        assertThat(descriptionMatcher.getConfidence(), equalTo(1));
        assertThat(descriptionMatcher.isDropped(), equalTo(false));
    }

    @Test
    void testEmptyStringNotMatch() {
        Incident incident1 = new Incident();
        incident1.setDescription("");
        incident1.setType(Incident.IncidentTypes.ACCIDENT.toString());

        Incident incident2 = new Incident();
        incident2.setDescription("Road closed");

        incident2.setType(Incident.IncidentTypes.ROADHAZARD.toString());

        descriptionMatcher = new DescriptionMatcher(incident1, incident2);
        assertThat(descriptionMatcher.getConfidence(), equalTo(0));
        assertThat(descriptionMatcher.isDropped(), equalTo(true));
    }

    @Test
    void testNoNullException() {
        Incident incident1 = new Incident();
        Incident incident2 = new Incident();
        descriptionMatcher = new DescriptionMatcher(incident1, incident2);
    }

}