package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.data.Request;

import java.time.LocalDateTime;
import java.util.List;

public interface ProviderNormalizer {

    List<Request> parseCurrentRequest();

}
