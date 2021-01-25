package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

// Thesis page 39
public class DescriptionMatcher extends Matcher {
    int confidence;
    String description = "";

    public DescriptionMatcher(Incident incident1, Incident incident2) {
        super();
        String typeInc1 = incident1.getType();
        String typeInc2 = incident1.getType();

        if (typeInc1 != null && typeInc2 != null && typeInc1.equals(typeInc2)) {
            confidence += 1;
            description += "Type matched | ";
        }

        String descInc1 = incident1.getDescription();
        String descInc2 = incident2.getDescription();

        if (descInc1!=null && descInc2 != null && descInc1.length() > 1 && descInc2.length() > 1 && (descInc1.toLowerCase().contains(descInc2.toLowerCase()) || descInc2.toLowerCase().contains(descInc1.toLowerCase())) ) {
            confidence += 1;
            description += "description contained";
        }

    }


    @Override
    public boolean isDropped() {
        return confidence == 0;
    }

    @Override
    public int getConfidence() {
        return confidence;
    }

    @Override
    public String getDescription() {
        return "[DescriptionMatcher: score " + confidence + " |isDropped" + isDropped() + "| " + description + " ]";
    }
}
