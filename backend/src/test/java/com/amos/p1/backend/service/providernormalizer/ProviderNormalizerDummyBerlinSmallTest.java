package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ProviderNormalizerDummyBerlinSmallTest {

    @Test
    void testDummyRequestAndIncidentSize(){
        ProviderNormalizer providerNormalizer = new ProviderNormalizerDummyBerlinSmall();
        List<Request> requests = providerNormalizer.parseCurrentRequest();

        assertThat(requests.size(), equalTo(1));
        assertThat(requests.get(0).getIncidents(), hasSize(107));

        List<Incident> tomtomIncidents =
                requests.get(0).getIncidents()
                        .stream()
                        .filter(incident -> incident.getProvider().equals("1"))
                        .collect(Collectors.toList());

        List<Incident> hereIncidents =
                requests.get(0).getIncidents()
                        .stream()
                        .filter(incident -> incident.getProvider().equals("0"))
                        .collect(Collectors.toList());

        assertThat(tomtomIncidents, hasSize(58));
        assertThat(hereIncidents, hasSize(49));

    }

    @Test
    void testDummyConstructionTypeAmount(){
        ProviderNormalizer providerNormalizer = new ProviderNormalizerDummyBerlinSmall();
        List<Request> requests = providerNormalizer.parseCurrentRequest();


        long typeConstructionAmount =
                requests.get(0).getIncidents()
                        .stream()
                        .filter(incident -> incident.getType().equals("CONSTRUCTION"))
                        .count();

        assertThat(typeConstructionAmount, is(41L));
    }

    @Test
    void testDummyEvaluationCandidate(){
        ProviderNormalizer providerNormalizer = new ProviderNormalizerDummyBerlinSmall();

        List<Request> requests = providerNormalizer.parseCurrentRequest();

        List<EvaluationCandidate> evaluationCandidates = requests.get(0).getEvaluationCandidate();
        assertThat(evaluationCandidates, hasSize(8));

        assertThat(evaluationCandidates.get(0).getHereIncident(), is(notNullValue()));
        assertThat(evaluationCandidates.get(0).getTomTomIncident(), is(notNullValue()));
        assertThat(evaluationCandidates.get(0).getScore(), is(not(0)));
        assertThat(evaluationCandidates.get(0).getConfidenceDescription(), not(equalTo("")));
    }



}