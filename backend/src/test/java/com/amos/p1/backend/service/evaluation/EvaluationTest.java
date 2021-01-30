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
import static org.hamcrest.Matchers.in;

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
        assertThat(evaluationCandidateList.size(), equalTo(herreIncidents.size() * tomTomIncidents.size()));

        // containing Matchers
        evaluationCandidateList.forEach(e -> {
            assertThat(e.getMatcherList().size(), equalTo(4));
        });
    }

    @Test
    void testManifolds() {
        Evaluation evaluation = new Evaluation();
        Request req = new Request();
        List<Incident> incidents = new ArrayList<>();

        // 0 and 1 should be matching

        incidents.add(getBaseIncident(true, 0));

        Incident inc = getBaseIncident(false, 1);
        inc.setDescription("Accident");
        incidents.add(inc);

        inc = getBaseIncident(false, 2);
        inc.setDescription("Bullshit");
        incidents.add(inc);


        req.setIncidents(incidents);


        List<EvaluationCandidate> evaluationCandidateList = evaluation.calculateCandidates(req);
        List<EvaluationCandidate> reEvaluatedCandidateList = evaluation.dropManifolds(evaluationCandidateList);

        assertThat(evaluationCandidateList.size(), equalTo(2));
        assertThat(reEvaluatedCandidateList.get(0).getTomTomIncident().getId(), equalTo(1L));


        //todo: find good test case
    }

    private Incident getBaseIncident(boolean here, long id) {
        Incident incident = new Incident();
        incident.setStartPositionLatitude("52.0");
        incident.setStartPositionLongitude("13.0");
        incident.setEndPositionLatitude("52.0");
        incident.setEndPositionLongitude("13.0");
        incident.setType(Incident.IncidentTypes.ACCIDENT.toString());
        incident.setDescription("An Accident");
        incident.setProvider(here ? "0" : "1");
        incident.setLengthInMeter(50);
        incident.setId(id);

        return incident;
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

        assertThat(evaluationCandidateList.size(), equalTo(1));

        List<EvaluationCandidate> reEvaluatedCandidateList = evaluation.dropManifolds(evaluationCandidateList);

        assertThat(reEvaluatedCandidateList.size(), equalTo(1));

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

        assertThat(evaluationCandidateList.size(), equalTo(1));

        List<EvaluationCandidate> reEvaluatedCandidateList = evaluation.dropManifolds(evaluationCandidateList);

        assertThat(reEvaluatedCandidateList.size(), equalTo(0));
    }
}
