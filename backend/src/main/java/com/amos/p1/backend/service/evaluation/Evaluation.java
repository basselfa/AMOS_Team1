package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

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


        evaluationCandidates.forEach(
                candidate -> {
                    AngleMatcher angleMatcher = new AngleMatcher(candidate.getHereIncident(), candidate.getTomTomIncident());
                    SearchRadiusMatcher searchRadiusMatcher = new SearchRadiusMatcher(candidate.getHereIncident(), candidate.getTomTomIncident());

                    candidate.addMatcherToMatcherList(angleMatcher);
                    candidate.addMatcherToMatcherList(searchRadiusMatcher);
                }
        );

        evaluationCandidates = dropManifolds(evaluationCandidates);

        return evaluationCandidates;
    }

    public List<EvaluationCandidate> dropManifolds(List<EvaluationCandidate> evaluationCandidateList) {
        HashSet<EvaluationCandidate> finalCandidates = new HashSet();

        // filter all dropped elements out
        List<EvaluationCandidate> unDroppedElements = evaluationCandidateList.parallelStream().filter(
                candidate -> !candidate.isDropped()
        ).filter(
                c -> c.getMatcherList().stream().filter(m -> m instanceof SearchRadiusMatcher).map(m -> ((SearchRadiusMatcher) m).getLegthDifferencePercentage() < 60d).anyMatch(r -> r == true)
        ).collect(Collectors.toList());

        // collect all Mainfolds
        List<List<EvaluationCandidate>> mainFolds = unDroppedElements.parallelStream().map(
                candidate ->
                        unDroppedElements.stream().filter(
                                otherCandidate -> (
                                        (candidate.getHereIncident() == otherCandidate.getHereIncident()
                                                || candidate.getTomTomIncident() == otherCandidate.getTomTomIncident()))
                        ).collect(Collectors.toList())
        ).filter(list -> list.size() > 1).collect(Collectors.toList());


        // add none-mainfolds to the final Set
        HashSet mainfoldElements = new HashSet();
        mainFolds.forEach(
                s -> mainfoldElements.addAll(s.stream().collect(Collectors.toList()))
        );
        finalCandidates.addAll(
                unDroppedElements.stream().filter(e -> !mainfoldElements.contains(e)).collect(Collectors.toList())
        );


        //for each set of pairs
        for (List<EvaluationCandidate> candidates : mainFolds) {

            //find the ones with highest configence
            int highestConfidence = candidates.stream().mapToInt(c -> c.getScore()).max().getAsInt();
            List<EvaluationCandidate> highestConfidenceCandidates = candidates.stream().filter(e -> e.getScore() == highestConfidence).collect(Collectors.toList());

            // if(highestConfidenceCandidates.stream().count()>1){
            // if more than one pair
            // highestConfidenceCandidates = highestConfidenceCandidates.stream().filter(c -> c.getMatcherList().stream().filter(m -> m instanceof SearchRadiusMatcher).map(m -> ((SearchRadiusMatcher) m).getLegthDifferencePercentage() < 60d).anyMatch(r -> r == true)).collect(Collectors.toList());
            //  }
            if (highestConfidenceCandidates.stream().count() > 1) {
                // if more than one pair
                double smallestDistancePionts = highestConfidenceCandidates.stream().mapToDouble(c -> c.getMatcherList().stream().filter(m -> m instanceof SearchRadiusMatcher).mapToDouble(m -> ((SearchRadiusMatcher) m).getFromAndToDistanceSum()).findFirst().getAsDouble()).min().getAsDouble();
                highestConfidenceCandidates = highestConfidenceCandidates.stream().filter(c -> c.getMatcherList().stream().filter(m -> m instanceof SearchRadiusMatcher).map(m -> ((SearchRadiusMatcher) m).getFromAndToDistanceSum() == smallestDistancePionts).anyMatch(r -> r == true)).collect(Collectors.toList());

            }
            Optional<EvaluationCandidate> cacndidate = highestConfidenceCandidates.stream().findFirst();
            if (cacndidate.isPresent())
                finalCandidates.add(cacndidate.get());
        }
        return new ArrayList(finalCandidates);
    }
}
