package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.Incident;

public interface JsonToIncident {

    Incident normalize(String json);

}
