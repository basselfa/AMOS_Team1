package com.amos.p1.backend.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.math.BigDecimal;

@NamedQuery(
        name="findAllComparisonsWithId",
        query="SELECT c FROM Comparison c WHERE c.id = :id"
)
@Entity
public class Comparison {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String source1;
    private String source2;

    public Comparison() {
        super();
    }
    public Comparison(String source1, String source2) {
        super();
        this.source1 = source1;
        this.source2 = source2;
    }

    @Column(name = "ID", precision = 0)
    public Long getId() {
        return id;
    }
    public void setId(Long id) { this.id = id; }

    @Basic
    @Column(name = "Source1", nullable = true)
    public String getSource1() {
        return source1;
    }
    public void setSource1(String source1) {  this.source1 = source1; }

    @Basic
    @Column(name = "Source2", nullable = true)
    public String getSource2() {
        return source2;
    }
    public void setSource2(String source2) {  this.source2 = source2; }



    @Override
    public String toString() {
        return "Comparison{" +
                "id=" + id +
                ", source1='" + source1 + '\'' +
                ", source2='" + source2 + '\'' +
                '}';
    }
}
