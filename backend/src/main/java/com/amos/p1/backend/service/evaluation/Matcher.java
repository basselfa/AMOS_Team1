package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

public interface Matcher {

    void match(Incident incident1, Incident incident2);

    boolean isDropped();

    int getConfidence();
}
