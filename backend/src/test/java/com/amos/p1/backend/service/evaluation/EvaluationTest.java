package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import com.amos.p1.backend.service.requestcreator.RequestCreatorDummyBerlinSmall;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class EvaluationTest {

    Request request;

    public EvaluationTest() {
        RequestCreator requestCreator = new RequestCreatorDummyBerlinSmall();
        List<Request> requests = requestCreator.buildRequests();

        requests
                .stream()
                .filter(request -> request.getCityName().equals("Berlin"))
                .findFirst()
                .ifPresent(request -> this.request = request);
    }


    @Test
    void testCalculation() {
        Evaluation evaluation = new Evaluation();
        List<EvaluationCandidate> evaluationCandidateList = evaluation.calculateCandidates(request);

        List<Incident> herreIncidents = request.getIncidents().stream().filter(e -> e.getProvider().equals("0")).collect(Collectors.toList());
        List<Incident> tomTomIncidents = request.getIncidents().stream().filter(e -> e.getProvider().equals("1")).collect(Collectors.toList());

        // coorect size
        assertThat( evaluationCandidateList.size(),equalTo( herreIncidents.size() * tomTomIncidents.size()));

        // containing Matchers
        evaluationCandidateList.forEach(e -> {
            assertThat( e.getMatcherList().size(), equalTo(3));
        });
    }

    @Test
    void testManifolds() {
        Evaluation evaluation = new Evaluation();
        List<EvaluationCandidate> evaluationCandidateList = evaluation.calculateCandidates(request);
        List<EvaluationCandidate> reEvaluatedCandidateList = evaluation.dropManifolds(evaluationCandidateList);

        //todo: find good test case
    }


    @Test
    void testSameIncidentLocal() {
        Request req = new Request();

        List<Incident> incidents = new ArrayList<>();
        Incident incident = new Incident();
        incident.setStartPositionLatitude("52.534080");
        incident.setStartPositionLongitude("13.290938");
        incident.setEndPositionLatitude("52.534576");
        incident.setEndPositionLongitude("13.300873");
        incident.setProvider("1");
        incident.setType(Incident.IncidentTypes.ACCIDENT.toString());
        incidents.add(incident);

        incident = new Incident();
        incident.setStartPositionLatitude("52.534080");
        incident.setStartPositionLongitude("13.290938");
        incident.setEndPositionLatitude("52.534576");
        incident.setEndPositionLongitude("13.300873");
        incident.setProvider("0");
        incident.setType(Incident.IncidentTypes.ACCIDENT.toString());
        incidents.add(incident);

        req.setIncidents(incidents);

        Evaluation evaluation = new Evaluation();
        List<EvaluationCandidate> evaluationCandidateList = evaluation.calculateCandidates(req);

        assertThat( evaluationCandidateList.size() , equalTo(1));

        List<EvaluationCandidate> reEvaluatedCandidateList = evaluation.dropManifolds(evaluationCandidateList);

        assertThat( reEvaluatedCandidateList.size() , equalTo(1));

    }

    @Test
    void testdifferentIncidentLocal() {
        Request req = new Request();

        List<Incident> incidents = new ArrayList<>();
        Incident incident = new Incident();
        incident.setStartPositionLatitude("52.534080");
        incident.setStartPositionLongitude("13.290938");
        incident.setEndPositionLatitude("52.534576");
        incident.setEndPositionLongitude("13.300873");
        incident.setProvider("1");
        incidents.add(incident);

        incident = new Incident();
        incident.setStartPositionLatitude("52.534080");
        incident.setStartPositionLongitude("13.290938");
        incident.setEndPositionLatitude("52.534576");
        incident.setEndPositionLongitude("14.300873");
        incident.setProvider("0");
        incidents.add(incident);

        req.setIncidents(incidents);

        Evaluation evaluation = new Evaluation();
        List<EvaluationCandidate> evaluationCandidateList = evaluation.calculateCandidates(req);

        assertThat( evaluationCandidateList.size() , equalTo(1));

        List<EvaluationCandidate> reEvaluatedCandidateList = evaluation.dropManifolds(evaluationCandidateList);

        assertThat( reEvaluatedCandidateList.size() , equalTo(0));
    }


}
