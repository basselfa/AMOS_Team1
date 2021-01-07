package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

// Thesis page 41
public class ExtraCloseMatcher extends Matcher{
    public ExtraCloseMatcher(Incident incident1, Incident incident2) {
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
