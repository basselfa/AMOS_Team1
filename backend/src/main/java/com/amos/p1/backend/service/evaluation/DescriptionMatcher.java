package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

// Thesis page 39
public class DescriptionMatcher extends Matcher{


    public DescriptionMatcher(Incident incident1, Incident incident2) {
        super(incident1,incident2);
        throw new IllegalStateException("Needs to be implemented");
    }

    @Override
    public boolean isDropped() {
        throw new IllegalStateException("Needs to be implemented");
    }

    @Override
    public int getConfidence() {
        throw new IllegalStateException("Needs to be implemented");
    }
}
