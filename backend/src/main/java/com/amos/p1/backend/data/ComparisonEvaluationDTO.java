package com.amos.p1.backend.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ComparisonEvaluationDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    public Date getDate(){
        throw new IllegalStateException("Needs to be implemented");
    }

    public int getTomTomIncidentAmount(){
        throw new IllegalStateException("Needs to be implemented");
    }

    public int getHereIncidentAmount(){
        throw new IllegalStateException("Needs to be implemented");
    }

    public int getSameIncidentAmount(){
        throw new IllegalStateException("Needs to be implemented");
    }

}
