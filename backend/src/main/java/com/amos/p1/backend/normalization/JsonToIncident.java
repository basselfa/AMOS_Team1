package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.Incident;

import java.util.List;

public interface JsonToIncident {
    enum IncidentTypes {    //do not change order!
        ACCIDENT,
        CONGESTION,
        DISABLEDVEHICLE,
        ROADHAZARD,
        ROADWORKS,
        PLANNEDEVENT,
        DETOUR,
        MISC,       // TODO: MISC FOR "UNKNOWN" OR "NO CATEGORY"
        WEATHER,
        ROADCLOSURE,
        LANERESTRICTION;

        int getId() {
            return ordinal();
        }
    }

    Incident normalizeOneIncident(String json);

    List<Incident> normalize(String json);
}
