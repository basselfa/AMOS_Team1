package com.amos.p1.backend.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Date;

public class ComparisonEvaluationDTO {

    private int tomTomIncidentsAmount;
    private int hereIncidentsAmount;
    private int sameIncidentAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    public void setTomTomIncidentsAmount(int tomTomIncidentsAmount) {
        this.tomTomIncidentsAmount = tomTomIncidentsAmount;
    }

    public void setHereIncidentsAmount(int hereIncidentsAmount) {
        this.hereIncidentsAmount = hereIncidentsAmount;
    }

    public void setSameIncidentAmount(int sameIncidentAmount) {
        this.sameIncidentAmount = sameIncidentAmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getTomTomIncidentsAmount(){
        return this.tomTomIncidentsAmount;
    }

    public int getHereIncidentsAmount(){
        return this.hereIncidentsAmount;
    }

    public int getSameIncidentAmount(){
        return this.sameIncidentAmount;
    }

    @Override
    public String toString() {
        return "ComparisonEvaluationDTO{" +
                "tomTomIncidentsAmount=" + tomTomIncidentsAmount +
                ", hereIncidentsAmount=" + hereIncidentsAmount +
                ", sameIncidentAmount=" + sameIncidentAmount +
                ", date=" + date +
                '}';
    }
}
