package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

/**
 * See Thesis page 38
 */
public class AngleMatcher implements Matcher {

    @Override
    public void match(Incident incident1, Incident incident2) {
        throw new IllegalStateException("Needs to be implemented");
    }

    @Override
    public int getConfidence() {
        throw new IllegalStateException("Needs to be implemented");
    }

    @Override
    public boolean isDropped() {
        throw new IllegalStateException("Needs to be implemented");
    }

    public double getDegree(){
        throw new IllegalStateException("Needs to be implemented");
    }
}
