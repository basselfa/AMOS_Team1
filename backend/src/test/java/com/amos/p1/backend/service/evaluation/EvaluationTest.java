package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.ProviderNormalizer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class EvaluationTest {

    Request request;

    public EvaluationTest(){
        ProviderNormalizer providerNormalizer = new ProviderNormalizer(true);
        List<Request> requests = providerNormalizer.parseCurrentRequest();

        requests
            .stream()
                .filter(request -> request.getCityName().equals("Berlin"))
                .findFirst()
            .ifPresent(request -> this.request = request);
    }


    @Test
    void testCalculation(){
        Evaluation evaluation = new Evaluation();
        List<EvaluationCandidate> evaluationCandidateList = evaluation.calculateCandidates(request);

        List<Incident> herreIncidents = request.getIncidents().stream().filter(e -> e.getProvider().equals("0")).collect(Collectors.toList());
        List<Incident> tomTomIncidents = request.getIncidents().stream().filter(e -> e.getProvider().equals("1")).collect(Collectors.toList());

        // coorect size
        assert  evaluationCandidateList.size() == herreIncidents.size() * tomTomIncidents.size();

        // containing Matchers
        evaluationCandidateList.forEach( e-> {
            assert e.getMatcherList().size()>=1;
        });
    }

    @Test
    void testManifolds(){
        Evaluation evaluation = new Evaluation();
        List<EvaluationCandidate> evaluationCandidateList = evaluation.calculateCandidates(request);
        List<EvaluationCandidate> reEvaluatedCandidateList = evaluation.dropManifolds(evaluationCandidateList);

        //todo: find good test case
    }

}
