package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

// Thesis page 40
public class LengthMatcher extends Matcher {
    double lengthDifference;

    public LengthMatcher(Incident incident1, Incident incident2) {
        super();

        lengthDifference = 100d * Math.abs(incident1.getLengthInMeter() - incident2.getLengthInMeter())
                / (Math.abs(incident1.getLengthInMeter() + incident2.getLengthInMeter()) / 2);
    }

    @Override
    public boolean isDropped() {
        return false;
    }

    @Override
    public String getDescription() {
        return "[length difference: " + lengthDifference +" | confidence " + getConfidence()+"]";
    }

    @Override
    public int getConfidence() {
        return lengthDifference > 120 ? -1 : 0;
    }

    public double getLengthDifference() {
        return lengthDifference;
    }
}
