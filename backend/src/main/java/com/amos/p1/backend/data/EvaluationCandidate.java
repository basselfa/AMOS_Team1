package com.amos.p1.backend.data;

import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.evaluation.Matcher;
import com.amos.p1.backend.service.evaluation.SearchRadiusMatcher;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@NamedQuery(
        name = "getEvaluationCandidateFromRequestId",
        query = "SELECT i FROM EvaluationCandidate i WHERE i.requestId = :requestId"
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
    private int score;
    @Transient
    private Incident tomTomIncident;
    @Transient
    private Incident hereIncident;
    @Transient
    private final List<Matcher> matcherList = new ArrayList<>(); // Dont need to be stored in db
    @Transient
    Boolean evaluationCandidateSavedInDb = false;
    @Transient
    private boolean dropped;

    public EvaluationCandidate(Incident tomTomIncident, Incident hereIncident) {
        super();
        this.tomTomIncident = tomTomIncident;
        this.hereIncident = hereIncident;
        this.tomTomIncidentId = tomTomIncident.getId();
        this.hereIncidentId = hereIncident.getId();
    }

    public EvaluationCandidate() {

    }

    @Basic
    @Column(name = "requestId", nullable = true)
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "tomTomIncidentId", nullable = true)
    public Long getTomTomIncidentId() {
        return tomTomIncidentId;
    }

    public void setTomTomIncidentId(Long tomTomIncidentId) {
        this.tomTomIncidentId = tomTomIncidentId;
    }

    @Basic
    @Column(name = "hereIncidentId", nullable = true)
    public Long getHereIncidentId() {
        return hereIncidentId;
    }

    public void setHereIncidentId(Long hereIncidentId) {
        this.hereIncidentId = hereIncidentId;
    }

    @Basic
    @Column(name = "confidenceDescription", nullable = true)
    public String getConfidenceDescription() {
        return confidenceDescription;
    }

    public void setConfidenceDescription(String confidenceDescription) {
        this.confidenceDescription = confidenceDescription;
    }

    public Incident getTomTomIncident() {
        if (evaluationCandidateSavedInDb == false) return tomTomIncident;
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

    public void setScore(int score) {
        this.score = score;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    public void setEvaluationCandidateSavedInDb(Boolean evaluationCandidateSavedInDb) {
        this.evaluationCandidateSavedInDb = evaluationCandidateSavedInDb;
    }

    public Incident getHereIncident() {
        if (evaluationCandidateSavedInDb == false) return hereIncident;

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
        return score;
    }

    public boolean isDropped() {
        return dropped;
    }

    public List<Matcher> getMatcherList() {
        return Collections.unmodifiableList(matcherList);
    }

    public void addMatcherToMatcherList(Matcher matcher) {
        matcherList.add(matcher);
    }

    public Matcher getMatcherByClass(Class matcherClass) {
        Optional<Matcher> matcher = matcherList.stream().filter(m -> matcherClass.isInstance(m)).findFirst();
        if (matcher.isPresent())
            return matcher.get();
        return null;
    }


//    public class getMatcherByClass<T>{
//        public T get(Class matcherClass) {
//            Optional<Matcher> matcher = matcherList.stream().filter(m -> matcherClass.isInstance(m)).findFirst();
//            if (matcher.isPresent())
//                return (T) matcher.get();
//            return null;
//        }
//    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluationCandidate that = (EvaluationCandidate) o;
        return score == that.score && dropped == that.dropped && Objects.equals(id, that.id) && Objects.equals(requestId, that.requestId) && Objects.equals(tomTomIncidentId, that.tomTomIncidentId) && Objects.equals(hereIncidentId, that.hereIncidentId) && Objects.equals(confidenceDescription, that.confidenceDescription) && Objects.equals(tomTomIncident, that.tomTomIncident) && Objects.equals(hereIncident, that.hereIncident) && Objects.equals(matcherList, that.matcherList) && Objects.equals(evaluationCandidateSavedInDb, that.evaluationCandidateSavedInDb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestId, tomTomIncidentId, hereIncidentId, confidenceDescription, score, tomTomIncident, hereIncident, matcherList, evaluationCandidateSavedInDb, dropped);
    }
}
