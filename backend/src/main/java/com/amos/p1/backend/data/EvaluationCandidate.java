package com.amos.p1.backend.data;

import com.amos.p1.backend.service.evaluation.Matcher;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EvaluationCandidate {

    private Incident tomTomIncident;
    private Incident hereIncident;

    private final List<Matcher> matcherList = new ArrayList<>(); // Dont need to be stored in db

    private String confidenceDescription;
    private int score = 0;

    public Incident getTomTomIncident() {
        return tomTomIncident;
    }

    public void setTomTomIncident(Incident tomTomIncident) {
        this.tomTomIncident = tomTomIncident;
    }

    public Incident getHereIncident() {
        return hereIncident;
    }

    public void setHereIncident(Incident hereIncident) {
        this.hereIncident = hereIncident;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Matcher> getMatcherList() {
        return Collections.unmodifiableList(matcherList);
    }

    public void addMatcherToMatcherList(Matcher matcher) {
        matcherList.add(matcher);
    }

    public String getConfidenceDescription() {
        return confidenceDescription;
    }

    public void setConfidenceDescription(String confidenceDescription) {
        this.confidenceDescription = confidenceDescription;
    }
}
