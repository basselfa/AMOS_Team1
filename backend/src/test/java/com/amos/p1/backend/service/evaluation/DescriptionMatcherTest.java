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
        Incident incident1 = EvaluationTest.getBaseIncident(true,0);
        incident1.setDescription("road");
        incident1.setType(Incident.IncidentTypes.ACCIDENT.toString());

        Incident incident2 = EvaluationTest.getBaseIncident(true,0);
        incident2.setDescription("Road closed");

        incident2.setType(Incident.IncidentTypes.ROADCLOSURE.toString());

        descriptionMatcher = new DescriptionMatcher(incident1, incident2);
        assertThat(descriptionMatcher.getConfidence(), equalTo(1));
        assertThat(descriptionMatcher.isDropped(), equalTo(false));
    }

    @Test
    void testTypeMatch() {
        Incident incident1 = EvaluationTest.getBaseIncident(true,0);
        incident1.setDescription("ro333ad");
        incident1.setType(Incident.IncidentTypes.ACCIDENT.toString());

        Incident incident2 = EvaluationTest.getBaseIncident(true,0);
        incident2.setDescription("Road closed");

        incident2.setType(Incident.IncidentTypes.ACCIDENT.toString());

        descriptionMatcher = new DescriptionMatcher(incident1, incident2);
        assertThat(descriptionMatcher.getConfidence(), equalTo(1));
        assertThat(descriptionMatcher.isDropped(), equalTo(false));
    }

    @Test
    void testEmptyStringNotMatch() {
        Incident incident1 = EvaluationTest.getBaseIncident(true,0);
        incident1.setDescription("");
        incident1.setType(Incident.IncidentTypes.ACCIDENT.toString());

        Incident incident2 = EvaluationTest.getBaseIncident(true,0);
        incident2.setDescription("Road closed");
        incident2.setType(null);

        incident2.setType(Incident.IncidentTypes.ROADHAZARD.toString());

        descriptionMatcher = new DescriptionMatcher(incident1, incident2);
        assertThat(descriptionMatcher.getConfidence(), equalTo(0));
        assertThat(descriptionMatcher.isDropped(), equalTo(false));
    }

    @Test
    void testNoNullException() {
        Incident incident1 = EvaluationTest.getBaseIncident(true,0);
        incident1.setDescription(null);
        incident1.setType(null);
        Incident incident2 = EvaluationTest.getBaseIncident(true,0);
        incident2.setDescription(null);
        incident2.setType(null);
        descriptionMatcher = new DescriptionMatcher(incident1, incident2);
    }

}