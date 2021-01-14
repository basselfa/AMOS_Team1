package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Location;

import java.io.IOException;

// Thesis page 36
public class SearchRadiusMatcher extends Matcher {
    private static int searchboundaryInMeters = 20;
    private int confidence;
    private double distanceIncident1;
    private double distanceIncident2;
    private double distanceStartPoints;
    private double distanceEndPoints;

    public SearchRadiusMatcher(Incident incident1, Incident incident2) {
        super(incident1, incident2);

        // berechen Länge beider incidents
        distanceIncident1 = calculateDistance(
                start1Lat,
                start1Lng,
                end1Lat,
                end1Lng
        );
        distanceIncident2 = calculateDistance(
                start2Lat,
                start2Lng,
                end2Lat,
                end2Lng
        );

        //berechne akzepierte distanz zwischen start und endpunkt
        int accepatbleDistance;
        if (distanceIncident1 > 500 || distanceIncident2 > 500) {
            accepatbleDistance = searchboundaryInMeters * 4;
        } else if (distanceIncident1 > 5000 || distanceIncident2 > 5000) {
            accepatbleDistance = searchboundaryInMeters * 10;
        } else {
            accepatbleDistance = searchboundaryInMeters;
        }

        distanceStartPoints = calculateDistance(
                start1Lat,
                start1Lng,
                start2Lat,
                start2Lng
        );

        distanceEndPoints = calculateDistance(
                end1Lat,
                end1Lng,
                end2Lat,
                end2Lng
        );

        // check start and end point in same area
//        if (distanceStartPoints > accepatbleDistance && distanceEndPoints > accepatbleDistance) // no match
//            return;
//        else if (distanceStartPoints > accepatbleDistance ^ distanceEndPoints > accepatbleDistance) { // exactly one point matched
//            confidence += 1;
//        } else {
//            confidence += 2;
//        }

        if (distanceStartPoints < accepatbleDistance)
            confidence += 1;

        if (distanceEndPoints < accepatbleDistance)
            confidence += 1;


        //todo: "least distance" -> eigener Matcher ??

    }

    @Override
    public boolean isDropped() {
        return confidence == 0;
    }

    @Override
    public int getConfidence() {
        return confidence;
    }


    /* calculate distance beteen Lat/Lng: https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude*/
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist * 1.609344 * 1000;
    }

    public double getLegthDifferencePercentage() {
        try {
           // return Math.abs(distanceIncident1 - distanceIncident2)*100 / (distanceIncident1 > distanceIncident2 ? distanceIncident1 : distanceIncident2);       // todo: check if correct
            return (Math.abs(distanceIncident1 - distanceIncident2)*100d) /  (distanceIncident1 > distanceIncident2 ? distanceIncident2 : distanceIncident1);

        } catch (RuntimeException ex) {
            return 0;
        }
    }

    public double getFromAndToDistanceSum() {
        return distanceStartPoints + distanceEndPoints;
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
