package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Location;

// Thesis page 36
public class SearchRadiusMatcher extends Matcher {
    private static int searchboundaryInMeters = 20;
    private int confidence;

    public SearchRadiusMatcher(Incident incident1, Incident incident2) {
        super(incident1, incident2);

        //berechne akzepierte distanz zwischen start und endpunkt
        double distanceIncident1 = calculateDistance(
                start1Lat,
                start1Lng,
                end1Lat,
                end1Lng
        );
        double distanceIncident2 = calculateDistance(
                start2Lat,
                start2Lng,
                end2Lat,
                end2Lng
        );

        int accepatbleDistance;
        if (distanceIncident1 > 500 || distanceIncident2 > 500) {
            accepatbleDistance = searchboundaryInMeters * 4;
        } else if (distanceIncident1 > 5000 || distanceIncident2 > 5000) {
            accepatbleDistance = searchboundaryInMeters * 10;
        } else {
            accepatbleDistance = searchboundaryInMeters;
        }

        double distanceStartPoints = calculateDistance(
                start1Lat,
                start1Lng,
                start2Lat,
                start2Lng
        );

        double distanceEndPoints = calculateDistance(
                end1Lat,
                end1Lng,
                end2Lat,
                end2Lng
        );

        // check start and end point in same area
        if (distanceStartPoints > accepatbleDistance && distanceEndPoints > accepatbleDistance) // no match
            return;
        else if (distanceStartPoints > accepatbleDistance ^ distanceEndPoints > accepatbleDistance) { // exactly one point matched
            confidence += 1;
        } else {
            confidence += 2;
        }


        //todo: "least distance" -> eigener Matcher

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
    double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist * 1.609344;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
