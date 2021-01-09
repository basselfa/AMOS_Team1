package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.ProviderNormalizer;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        throw new IllegalStateException("Need to be tested");
    }

    @Test
    void testManifolds(){
        Evaluation evaluation = new Evaluation();
        List<EvaluationCandidate> evaluationCandidateList = evaluation.calculateCandidates(request);
        List<EvaluationCandidate> reEvaluatedCandidateList = evaluation.dropManifolds(evaluationCandidateList);

        throw new IllegalStateException("Need to be tested");
    }

}
