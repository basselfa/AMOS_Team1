package com.amos.p1.backend.service.requestcreator;

import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RequestCreatorDummyBerlinSmallTest {

    @Test
    void testDummyRequestAndIncidentSize(){
        RequestCreator requestCreator = new RequestCreatorDummyBerlinSmall();
        List<Request> requests = requestCreator.buildRequests();

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
        RequestCreator requestCreator = new RequestCreatorDummyBerlinSmall();
        List<Request> requests = requestCreator.buildRequests();


        long typeConstructionAmount =
                requests.get(0).getIncidents()
                        .stream()
                        .filter(incident -> incident.getType().equals(Incident.IncidentTypes.ROADWORKS.toString()))
                        .count();

        assertThat(typeConstructionAmount, is(43L));
    }


    @Test
    void testDummyEvaluationCandidate(){
        RequestCreator requestCreator = new RequestCreatorDummyBerlinSmall();

        List<Request> requests = requestCreator.buildRequests();

        List<EvaluationCandidate> evaluationCandidates = requests.get(0).getEvaluationCandidate();
        assertThat(evaluationCandidates, hasSize(8));

        assertThat(evaluationCandidates.get(0).getHereIncident(), is(notNullValue()));
        assertThat(evaluationCandidates.get(0).getTomTomIncident(), is(notNullValue()));
        assertThat(evaluationCandidates.get(0).getScore(), is(not(0)));
        assertThat(evaluationCandidates.get(0).getConfidenceDescription(), not(equalTo("")));
    }



}