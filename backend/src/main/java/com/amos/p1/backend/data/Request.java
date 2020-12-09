package com.amos.p1.backend.data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime ;
import java.util.List;

@Entity
public class Request {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long requestId;
    private LocalDateTime requestTime;

    public Request() {
        super();
    }

    public void addIncidents(List<Incident> incidents){
        //TODO
    }

    public List<Incident> getIncidents(){
        //TODO
        return null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;  }
    @Basic
    @Column(name = "requestTime",  nullable = true)
    public LocalDateTime getRequestTime() { return requestTime;}
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }

    @Basic
    @Column(name = "requestId",columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }





}
