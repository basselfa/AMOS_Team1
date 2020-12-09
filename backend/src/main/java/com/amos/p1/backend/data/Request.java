package com.amos.p1.backend.data;
import com.amos.p1.backend.database.MyRepo;

import javax.persistence.*;
import java.time.LocalDateTime ;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Request {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime requestTime;
    private String  incidentsId;


    public Request() {
        super();
    }
// parses Incidents to string ids of incidents
    public void addIncidents(List<Incident> incidents){
         incidentsId = "" ;
        for (Incident incident :incidents) {
            incidentsId += incident.getId().toString() ;
            if (incident != incidents.get(incidents.size()-1))
                incidentsId +=",";
        }

    }
    // parses string ids of incidents to list of Incidents
    public List<Incident> getIncidents(){
        String[] idSplit = incidentsId.split(",");
        List<Long>  idSplitasLongs =  Stream.of(idSplit).map(Long::valueOf).collect(Collectors.toList());

            List<Incident> incidentAsList =  MyRepo.getEntityManager().createNamedQuery("getFromids")
                    .setParameter("id" ,idSplitasLongs )
                    .getResultList();


        return incidentAsList;
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







}
