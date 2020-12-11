package com.amos.p1.backend.data;
import com.amos.p1.backend.database.MyRepo;

import javax.persistence.*;
import java.time.LocalDateTime ;
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
    private String  incidentsId= "";
    @Transient
    List<Incident> incidents ;

    public Request() {
        super();
    }
    // parses Incidents to string ids of incidents
    public void setIncidents(List<Incident> incidents){
        this.incidents = incidents;
    }

    // parses string ids of incidents to list of Incidents
    public List<Incident> getIncidents(){
        return incidents;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;  }
    @Basic
    @Column(name = "requestTime",columnDefinition="DATETIME")
    public LocalDateTime getRequestTime() { return requestTime;}
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }

    @Basic
    @Column(name = "incidentsId",  nullable = true)
    public String getIncidentsId() { return incidentsId;}
    public void setIncidentsId(String incidentsId) { this.incidentsId = incidentsId; }


    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", requestTime=" + requestTime +
                ", incidentsId='" + incidentsId + '\'' +
                ", incidents=" + getIncidents() +
                '}';
    }
}
