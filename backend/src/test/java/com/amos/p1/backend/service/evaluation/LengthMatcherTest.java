package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class LengthMatcherTest {

    private LengthMatcher lengthMatcher;

    @Test
    void testSameLengthMatch() {
        Incident incident1 = EvaluationTest.getBaseIncident(true,0);
        incident1.setLengthInMeter(100);

        Incident incident2 = EvaluationTest.getBaseIncident(true,0);
        incident2.setLengthInMeter(100);

        lengthMatcher = new LengthMatcher(incident1, incident2);
        assertThat(lengthMatcher.getConfidence(), equalTo(0));
        assertThat(lengthMatcher.isDropped(), equalTo(false));
        assertThat(lengthMatcher.getLengthDifference(), equalTo(0d));
    }

    @Test
    void testSimilarLengthMatch() {
        Incident incident1 = EvaluationTest.getBaseIncident(true,0);
        incident1.setLengthInMeter(100);

        Incident incident2 = EvaluationTest.getBaseIncident(true,0);
        incident2.setLengthInMeter(90);

        lengthMatcher = new LengthMatcher(incident1, incident2);
        assertThat(lengthMatcher.getConfidence(), equalTo(0));
        assertThat(lengthMatcher.isDropped(), equalTo(false));
        assertThat(lengthMatcher.getLengthDifference(), equalTo(10.526315789473685d));
    }

    @Test
    void testexactly120Differnece() {
        Incident incident1 = EvaluationTest.getBaseIncident(true,0);
        incident1.setLengthInMeter(50);

        Incident incident2 = EvaluationTest.getBaseIncident(true,0);
        incident2.setLengthInMeter(200);

        lengthMatcher = new LengthMatcher(incident1, incident2);
        assertThat(lengthMatcher.getConfidence(), equalTo(0));
        assertThat(lengthMatcher.isDropped(), equalTo(false));
        assertThat(lengthMatcher.getLengthDifference(), equalTo(120d));
    }
    @Test
    void testToLongLengthDiffernece() {
        Incident incident1 = EvaluationTest.getBaseIncident(true,0);
        incident1.setLengthInMeter(50);

        Incident incident2 = EvaluationTest.getBaseIncident(true,0);
        incident2.setLengthInMeter(201);

        lengthMatcher = new LengthMatcher(incident1, incident2);
        assertThat(lengthMatcher.getConfidence(), equalTo(-1));
        assertThat(lengthMatcher.isDropped(), equalTo(false));
        assertThat(lengthMatcher.getLengthDifference(), equalTo(120.3187250996016d));
    }
}