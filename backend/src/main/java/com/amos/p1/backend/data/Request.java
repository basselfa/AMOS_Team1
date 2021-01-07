package com.amos.p1.backend.data;
import com.amos.p1.backend.database.MyRepo;

import javax.persistence.*;
import java.time.LocalDateTime ;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NamedQuery(
        name="geRequestFromTime",
        query="SELECT r FROM Request r WHERE r.requestTime = :requestTime"
)

@Entity
public class Request {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime requestTime;
    @Transient
    List<Incident> incidents ;

    @Transient
    Boolean incidentSavedInDb= false ;


    public Request() {
        super();
    }
    // parses Incidents to string ids of incidents
    public void setIncidents(List<Incident> incidents){

        this.incidents = incidents;

    }

    // parses string ids of incidents to list of Incidents
    public List<Incident> getIncidents(){
       if (incidentSavedInDb == true) {

           List<Incident> incidentAsList;
           incidentAsList = MyRepo.getEntityManager().createNamedQuery("getFromRequestId")
                   .setParameter("requestId", getId())
                   .getResultList();

           System.out.println(getId());
           // update if anything gets deleted from incidents
           setIncidents(incidentAsList);
           return incidentAsList;

       }
           return incidents;


    }



    public Boolean getIncidentsSavedInDb() {return incidentSavedInDb; }
    public void setIncidentsSavedInDb(Boolean incidentSavedInDb) {this.incidentSavedInDb = incidentSavedInDb; }

    @Basic
    @Column(name = "id")
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;  }
    @Basic
    @Column(name = "requestTime",columnDefinition="DATETIME")
    public LocalDateTime getRequestTime() { return requestTime;}
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", requestTime=" + requestTime +
                ", incidents=" + getIncidents() +
                ", incidentSavedInDb=" + incidentSavedInDb +
                '}';
    }
}
