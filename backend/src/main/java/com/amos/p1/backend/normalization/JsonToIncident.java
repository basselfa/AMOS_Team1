package com.amos.p1.backend.normalization;

import com.amos.p1.backend.from_client_to_backend.Incident;

public interface JsonToIncident {

    Incident normalize(String json);

}
