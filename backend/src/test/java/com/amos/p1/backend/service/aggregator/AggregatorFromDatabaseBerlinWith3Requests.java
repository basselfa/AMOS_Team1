package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummy;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AggregatorFromDatabaseBerlinWith3Requests {

    Aggregator aggregator = new AggregatorFromDatabase();

    public AggregatorFromDatabaseBerlinWith3Requests() {
        MyRepo.setUseTestDatabase(true);
        MyRepo.dropAll();

        // TODO add incidents for 3 request and evaluations candidates

        Incident incident1FromRequest1 = new Incident();
        incident1FromRequest1.setTrafficId("ABC");
        incident1FromRequest1.setProvider("0");

        List<Incident> incidentsFromRequest1 = new ArrayList<>();
        incidentsFromRequest1.add(incident1FromRequest1);

        EvaluationCandidate evaluationCandidateFromRequest1 = new EvaluationCandidate();
        List<EvaluationCandidate> evaluationCandidatesFromRequest1 = new ArrayList<>();
        evaluationCandidatesFromRequest1.add(evaluationCandidateFromRequest1);

        Request request1 = new Request();
        Request request2 = new Request();
        Request request3 = new Request();

        request1.setIncidents(incidentsFromRequest1);
        request1.setEvaluatedCandidates(evaluationCandidatesFromRequest1);

        MyRepo.insertRequest(request1);
        MyRepo.insertRequest(request2);
        MyRepo.insertRequest(request3);
    }


    @Test
    void testGetComparisonEvaluationOverTime(){
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOs = aggregator.getComparisonEvaluationOverTime("Berlin");

        assertThat(comparisonEvaluationDTOs, hasSize(3));

        // TODO change amount according to dummy data
        assertThat(comparisonEvaluationDTOs.get(0).getTomTomIncidentsAmount(), equalTo(2));
        assertThat(comparisonEvaluationDTOs.get(0).getHereIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(0).getSameIncidentAmount(), equalTo(1));

        assertThat(comparisonEvaluationDTOs.get(1).getTomTomIncidentsAmount(), equalTo(2));
        assertThat(comparisonEvaluationDTOs.get(1).getHereIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(1).getSameIncidentAmount(), equalTo(1));

        assertThat(comparisonEvaluationDTOs.get(2).getTomTomIncidentsAmount(), equalTo(2));
        assertThat(comparisonEvaluationDTOs.get(2).getHereIncidentsAmount(), equalTo(1));
        assertThat(comparisonEvaluationDTOs.get(2).getSameIncidentAmount(), equalTo(1));
    }

}
