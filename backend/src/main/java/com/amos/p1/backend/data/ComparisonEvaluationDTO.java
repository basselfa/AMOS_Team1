package com.amos.p1.backend.data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ComparisonEvaluationDTO {

    private int tomTomIncidentsAmount;
    private int hereIncidentsAmount;
    private int sameIncidentAmount;
    private Date date;

    public void setTomTomIncidentsAmount(int tomTomIncidentsAmount) {
        this.tomTomIncidentsAmount = tomTomIncidentsAmount;
    }

    public void setHereIncidentsAmount(int hereIncidentsAmount) {
        this.hereIncidentsAmount = hereIncidentsAmount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSameIncidentAmount(int sameIncidentAmount) {
        this.sameIncidentAmount = sameIncidentAmount;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    public Date getDate(){
        return this.date;
    }

    public int getTomTomIncidentAmount(){
        return this.tomTomIncidentsAmount;
    }

    public int getHereIncidentAmount(){
        return this.hereIncidentsAmount;
    }

    public int getSameIncidentAmount(){
        return this.sameIncidentAmount;
    }



}
