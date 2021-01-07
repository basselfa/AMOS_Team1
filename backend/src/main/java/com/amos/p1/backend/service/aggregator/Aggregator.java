package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationOverTimeDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;

import java.time.LocalDateTime;
import java.util.List;

public interface Aggregator {

    List<Incident> getFromCity(String city);

    List<Incident> getFromCityAndTypes(String city, List<String> filter);

    List<Incident> getFromCityAndTimeStamp(String city, LocalDateTime timestamp);

    List<LocalDateTime> getTimestampsFromCity(String city);

    List<String> getCities();

    List<Incident> getAllData();

    List<EvaluationCandidate> getEvaluationCandiate(String city, LocalDateTime timestamp);

    ComparisonEvaluationOverTimeDTO getComparisonEvaluationOverTime();
}
