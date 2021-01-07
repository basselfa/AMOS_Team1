package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Location;

// Thesis page 36
public class SearchRadiusMatcher implements Matcher {
    private static int searchboundaryInMeters = 20; // todo: wo soll dieser Configwert stehen?

    //todo: wollen wir einen state haben?
    int confidence;


    @Override
    public void match(Incident incident1, Incident incident2) {
        confidence = 0; // reset state todo: wollen wir einen state haben?


        IncidentsWrapper locations = new IncidentsWrapper(incident1, incident2);

        //berechne akzepierte distanz zwischen start und endpunkt
        double distanceIncident1 = calculateDistance(
                locations.start1Lat,
                locations.start1Lng,
                locations.end1Lat,
                locations.end1Lng
        );
        double distanceIncident2 = calculateDistance(
                locations.start2Lat,
                locations.start2Lng,
                locations.end2Lat,
                locations.end2Lng
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
                locations.start1Lat,
                locations.start1Lng,
                locations.start2Lat,
                locations.start2Lng
        );

        double distanceEndPoints = calculateDistance(
                locations.end1Lat,
                locations.end1Lng,
                locations.end2Lat,
                locations.end2Lng
        );

        // check start and end point in same area
        if (distanceStartPoints > accepatbleDistance && distanceEndPoints > accepatbleDistance) // no match
            return;
        else if (distanceStartPoints > accepatbleDistance ^ distanceEndPoints > accepatbleDistance) { // exactly one point matched
            confidence += 1;
        } else {
            confidence += 2;
        }


        //todo: "least distance"

    }

    @Override
    public boolean isDropped() {
        return confidence == 0;
    }

    @Override
    public int getConfidence() {
        return confidence;
    }


    //todo:refactor!

    class IncidentsWrapper {
        double start1Lat;
        double start1Lng;
        double end1Lat;
        double end1Lng;
        double start2Lat;
        double start2Lng;
        double end2Lat;
        double end2Lng;

        public IncidentsWrapper(Incident incident1, Incident incident2) {
            this.start1Lat = Double.parseDouble(incident1.getStartPositionLatitude());
            this.start1Lng = Double.parseDouble(incident1.getStartPositionLongitude());
            this.end1Lat = Double.parseDouble(incident1.getEndPositionLatitude());
            this.end1Lng = Double.parseDouble(incident1.getEndPositionLongitude());
            this.start2Lat = Double.parseDouble(incident2.getStartPositionLatitude());
            this.start2Lng = Double.parseDouble(incident2.getStartPositionLongitude());
            this.end2Lat = Double.parseDouble(incident2.getEndPositionLatitude());
            this.end2Lng = Double.parseDouble(incident2.getEndPositionLongitude());
        }
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
