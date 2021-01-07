package com.amos.p1.backend.data;

import com.amos.p1.backend.service.evaluation.Matcher;
import net.minidev.json.annotate.JsonIgnore;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EvaluationCandidate {
    public EvaluationCandidate(Incident tomTomIncident, Incident hereIncident) {
        this.tomTomIncident = tomTomIncident;
        this.hereIncident = hereIncident;
    }

    private Incident tomTomIncident;
    private Incident hereIncident;

    private final List<Matcher> matcherList = new ArrayList<>(); // Dont need to be stored in db

    private String confidenceDescription;
    //private int score = 0;

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
        return matcherList.stream().mapToInt(matcher -> matcher.getConfidence()).sum();
    }

    public boolean isDropped(){
        return  matcherList.stream().anyMatch(matcher -> matcher.isDropped());
    }

    public List<Matcher> getMatcherList() {
        return Collections.unmodifiableList(matcherList);
    }

    public void addMatcherToMatcherList(Matcher matcher) {
        matcherList.add(matcher);
    }

    /*public void addMatcherToMatcherList(Class matcherClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Matcher matcher = (Matcher) matcherClass.getDeclaredConstructor(Incident.class, Incident.class).newInstance(tomTomIncident, hereIncident);
        matcherList.add(matcher);
    }*/


    public String getConfidenceDescription() {
        return confidenceDescription;
    }

    public void setConfidenceDescription(String confidenceDescription) {
        this.confidenceDescription = confidenceDescription;
    }
}
