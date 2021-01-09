package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Aggregator {

    List<Incident> getIncidents(String city, Optional<LocalDateTime> timestamp, Optional<List<String>> types);

    List<LocalDateTime> getTimestampsFromCity(String city);

    List<String> getCities();

    List<Incident> getAllData();

    List<EvaluationCandidate> getEvaluationCandiate(String city, LocalDateTime timestamp);

    List<ComparisonEvaluationDTO> getComparisonEvaluationOverTime();
}
