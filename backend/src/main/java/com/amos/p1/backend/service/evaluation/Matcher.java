package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

public abstract class Matcher {
    double start1Lat;
    double start1Lng;
    double end1Lat;
    double end1Lng;
    double start2Lat;
    double start2Lng;
    double end2Lat;
    double end2Lng;

    public Matcher(Incident incident1, Incident incident2){
        this.start1Lat = Double.parseDouble(incident1.getStartPositionLatitude());
        this.start1Lng = Double.parseDouble(incident1.getStartPositionLongitude());
        this.end1Lat = Double.parseDouble(incident1.getEndPositionLatitude());
        this.end1Lng = Double.parseDouble(incident1.getEndPositionLongitude());
        this.start2Lat = Double.parseDouble(incident2.getStartPositionLatitude());
        this.start2Lng = Double.parseDouble(incident2.getStartPositionLongitude());
        this.end2Lat = Double.parseDouble(incident2.getEndPositionLatitude());
        this.end2Lng = Double.parseDouble(incident2.getEndPositionLongitude());
    }

    abstract boolean isDropped();

    abstract int getConfidence();
}
