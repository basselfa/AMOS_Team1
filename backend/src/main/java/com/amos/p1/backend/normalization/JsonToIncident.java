package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.Incident;

import java.util.List;

public interface JsonToIncident {

    Incident normalizeOneIncident(String json);

    List<Incident> normalize(String json);
}
