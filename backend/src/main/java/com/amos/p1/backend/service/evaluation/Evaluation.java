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
                        tomTomIncident -> {
                            // create EvaluationCandidates and fill with data
                            EvaluationCandidate candidate = new EvaluationCandidate(tomTomIncident, herreIncident);

                            candidate.addMatcher(AngleMatcher.class);
                            candidate.addMatcher(SearchRadiusMatcher.class);
                            candidate.addMatcher(DescriptionMatcher.class);
                            candidate.addMatcher(LengthMatcher.class);
                            // ... add Matcher here

                            // dynamically evaluate Matchers ans store results in EvaluationCandidate
                            candidate.setScore(
                                    candidate.getMatcherList().stream().mapToInt(matcher -> matcher.getConfidence()).sum()
                            );

                            candidate.setDropped(
                                    candidate.getMatcherList().stream().anyMatch(matcher -> matcher.isDropped())
                            );

                            candidate.setConfidenceDescription(
                                    candidate.getMatcherList().stream().map(matcher -> matcher.getDescription()).reduce("", (a, b) -> (a + b))
                            );
                            return candidate;
                        }
                )
        ).collect(Collectors.toList());

        return evaluationCandidates;
    }

    public List<EvaluationCandidate> dropManifolds(List<EvaluationCandidate> evaluationCandidateList) {
        HashSet<EvaluationCandidate> finalCandidates = new HashSet();

        // filter all dropped elements out
        List<EvaluationCandidate> unDroppedElements = evaluationCandidateList.parallelStream().filter(
                candidate -> !candidate.isDropped()
        ).filter(
                // c -> c.getMatcherList().stream().filter(m -> m instanceof SearchRadiusMatcher).map(m -> ((SearchRadiusMatcher) m).getLegthDifferencePercentage() < 60d).anyMatch(r -> r == true)
                c -> (c.getMatcherByClass(SearchRadiusMatcher.class)).getFromAndToDistanceSum() < 60d
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
                //double smallestDistancePionts = highestConfidenceCandidates.stream().mapToDouble(c -> c.getMatcherList().stream().filter(m -> m instanceof SearchRadiusMatcher).mapToDouble(m -> ((SearchRadiusMatcher) m).getFromAndToDistanceSum()).findFirst().getAsDouble()).min().getAsDouble();
                double smallestDistancePionts = highestConfidenceCandidates.stream().mapToDouble(c -> c.getMatcherByClass(SearchRadiusMatcher.class).getConfidence()).min().getAsDouble();
//                double smallestDistancePionts = highestConfidenceCandidates.stream().mapToDouble(c -> new c.getMatcherByClass<SearchRadiusMatcher>().get().getConfidence()).min().getAsDouble();

                highestConfidenceCandidates = highestConfidenceCandidates.stream().filter(c -> c.getMatcherByClass(SearchRadiusMatcher.class).getFromAndToDistanceSum() == smallestDistancePionts).collect(Collectors.toList());

            }
            Optional<EvaluationCandidate> cacndidate = highestConfidenceCandidates.stream().findFirst();
            if (cacndidate.isPresent())
                finalCandidates.add(cacndidate.get());
        }
        return new ArrayList(finalCandidates);
    }
}
