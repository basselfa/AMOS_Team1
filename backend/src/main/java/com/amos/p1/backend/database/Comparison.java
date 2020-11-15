package com.amos.p1.backend.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comparison {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String source1;
    private String source2;

    /**
     * Only for JPA needed. So its protected
     */
    protected Comparison() {

    }
    public Comparison(String source1, String source2) {
        this.source1 = source1;
        this.source2 = source2;
    }


    public Long getId() {
        return id;
    }

    public String getSource1() {
        return source1;
    }

    public String getSource2() {
        return source2;
    }


    @Override
    public String toString() {
        return "Comparison{" +
                "id=" + id +
                ", source1='" + source1 + '\'' +
                ", source2='" + source2 + '\'' +
                '}';
    }
}
