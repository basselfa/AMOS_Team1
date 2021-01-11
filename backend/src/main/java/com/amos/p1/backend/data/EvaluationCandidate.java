package com.amos.p1.backend.data;

import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.evaluation.Matcher;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NamedQuery(
        name = "getEvaluationCandidateFromRequestId",
        query = "SELECT i FROM Incident i WHERE i.requestId = :requestId"
)
@Entity
public class EvaluationCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long requestId;
    private Long tomTomIncidentId;
    private Long hereIncidentId;
    private String confidenceDescription;
    @Transient
    private Incident tomTomIncident;
    @Transient
    private Incident hereIncident;
    @Transient
    private final List<Matcher> matcherList = new ArrayList<>(); // Dont need to be stored in db



    public EvaluationCandidate(Incident tomTomIncident ,Incident hereIncident ) {
        super();
        this.tomTomIncident = tomTomIncident;
        this.hereIncident = hereIncident  ;
        this.tomTomIncidentId = tomTomIncident.getId();
        this.hereIncidentId = hereIncident.getId();
    }

    public EvaluationCandidate() {

    }

    //private int score = 0;
    @Basic
    @Column(name = "requestId", nullable = true)
    public Long getRequestId() {    return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public Long getId() {return id; }
    public void setId(Long id) { this.id = id;    }
    @Basic
    @Column(name = "tomTomIncidentId", nullable = true)
    public Long getTomTomIncidentId() {    return tomTomIncidentId; }
    public void setTomTomIncidentId(Long tomTomIncidentId) {  this.tomTomIncidentId = tomTomIncidentId; }

    @Basic
    @Column(name = "hereIncidentId", nullable = true)
    public Long getHereIncidentId() {   return hereIncidentId;    }
    public void setHereIncidentId(Long hereIncidentId) { this.hereIncidentId = hereIncidentId; }

    @Basic
    @Column(name = "confidenceDescription", nullable = true)
    public String getConfidenceDescription() {
        return confidenceDescription;
    }

    public void setConfidenceDescription(String confidenceDescription) {
        this.confidenceDescription = confidenceDescription;
    }

    public Incident getTomTomIncident() {
        List<Incident> incidentAsList;
        incidentAsList = MyRepo.getEntityManager().createNamedQuery("getFromid")
                .setParameter("id", tomTomIncidentId)
                .getResultList();
        return incidentAsList.get(0);
    }

    public void setTomTomIncident(Incident tomTomIncident) {
        this.tomTomIncident = tomTomIncident;
        setTomTomIncidentId(tomTomIncident.getId());

    }

    public Incident getHereIncident() {
        List<Incident> incidentAsList;
        incidentAsList = MyRepo.getEntityManager().createNamedQuery("getFromid")
                .setParameter("id", hereIncidentId)
                .getResultList();
        return incidentAsList.get(0);
    }
    public void setHereIncident(Incident hereIncident) {
        this.hereIncident = hereIncident;
        setHereIncidentId(hereIncident.getId());
    }

    public int getScore() {
        return matcherList.stream().mapToInt(matcher -> matcher.getConfidence()).sum();
    }

    public boolean isDropped(){
        return  matcherList.stream().anyMatch(matcher -> matcher.isDropped());
    }

    public List<Matcher> getMatcherList() {
        return Collections.unmodifiableList(matcherList);
    }

    public void addMatcherToMatcherList(Matcher matcher) {
        matcherList.add(matcher);
    }

    @Override
    public String toString() {
        return "EvaluationCandidate{" +
                "id=" + id +
                ", requestId=" + requestId +
                ", tomTomIncidentId=" + tomTomIncidentId +
                ", hereIncidentId=" + hereIncidentId +
                ", confidenceDescription='" + confidenceDescription + '\'' +
                ", tomTomIncident=" + tomTomIncident +
                ", hereIncident=" + hereIncident +
                ", matcherList=" + matcherList +
                '}';
    }

    /*public void addMatcherToMatcherList(Class matcherClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Matcher matcher = (Matcher) matcherClass.getDeclaredConstructor(Incident.class, Incident.class).newInstance(tomTomIncident, hereIncident);
        matcherList.add(matcher);
    }*/


}
