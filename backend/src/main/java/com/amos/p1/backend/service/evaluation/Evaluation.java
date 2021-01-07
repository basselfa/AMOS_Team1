package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;

import java.util.List;
import java.util.stream.Collectors;

public class Evaluation {
    public List<EvaluationCandidate> calculateCandidates(Request request) {

        List<Incident> herreIncidents = request.getIncidents().stream().filter(e -> e.getProvider().equals("0")).collect(Collectors.toList());
        List<Incident> tomTomIncidents = request.getIncidents().stream().filter(e -> e.getProvider().equals("1")).collect(Collectors.toList());

        // create all permutations of Incidents of both providers
        List<EvaluationCandidate> evaluationCandidates = herreIncidents.parallelStream().flatMap(
                herreIncident -> tomTomIncidents.stream().map(
                        tomTomIncident -> new EvaluationCandidate(tomTomIncident, herreIncident)
                )
        ).collect(Collectors.toList());

        request.setEvaluatedCandidates(evaluationCandidates);

        evaluationCandidates.forEach(
                candidate -> {
                    AngleMatcher angleMatcher = new AngleMatcher(candidate.getHereIncident(), candidate.getTomTomIncident());
                    SearchRadiusMatcher searchRadiusMatcher = new SearchRadiusMatcher(candidate.getHereIncident(), candidate.getTomTomIncident());

                    candidate.addMatcherToMatcherList(angleMatcher);
                    candidate.addMatcherToMatcherList(searchRadiusMatcher);
                }
        );

        return null;
    }

    public List<EvaluationCandidate> dropManifolds(List<EvaluationCandidate> evaluationCandidateList) {
        List<EvaluationCandidate> undroppedCandidates = evaluationCandidateList.parallelStream().filter(
                //filter all, wich are discarded
                candidate -> !candidate.isDropped()
        ).collect(Collectors.toList());

        List<EvaluationCandidate> mainfoldCandidates = undroppedCandidates.parallelStream().filter(
                // filter candidates with mainfolds
                candidate ->
                        undroppedCandidates.stream().filter(
                                otherCandidate -> (
                                        (candidate.getHereIncident() == otherCandidate.getHereIncident()
                                                || candidate.getTomTomIncident() == otherCandidate.getTomTomIncident())
                                                && candidate != otherCandidate)
                        ).count() > 0
        ).collect(Collectors.toList());

        throw new IllegalStateException("Needs to be implemented");
        //return evaluationCandidateList;
    }
}
