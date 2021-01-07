package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

/**
 * See Thesis page 38
 */
public class AngleMatcher implements Matcher {
    private static float angleTolerance = 15; // todo: wo soll dieser Configwert stehen?

    //todo: wollen wir einen state haben?
    int confidence;
    float angle;

    @Override
    public void match(Incident incident1, Incident incident2) {
        confidence = 0;
        IncidentsWrapper locations = new IncidentsWrapper(incident1, incident2);

        float angle1 = getAngle(
                locations.start1Lat,
                locations.start1Lng,
                locations.end1Lat,
                locations.end1Lng
        );

        float angle2 = getAngle(
                locations.start2Lat,
                locations.start2Lng,
                locations.end2Lat,
                locations.end2Lng
        );

        angle = Math.abs(angle1 - angle2);

        if (angle < angleTolerance) {
            confidence = 1;
        } else if (angle < angleTolerance * 2) {
            confidence = -1;
        }else{
            return;
        }
    }

    @Override
    public int getConfidence() {
        return confidence;
    }

    @Override
    public boolean isDropped() {
        return confidence==0;
    }

    public double getDegree() {
        return angle;
    }

    //todo: check if coorect https://stackoverflow.com/questions/9970281/java-calculating-the-angle-between-two-points-in-degrees
    public float getAngle(double lat1, double lon1, double lat2, double lon2) {
        float angle = (float) Math.toDegrees(Math.atan2(lat1 - lat2, lon1  - lon2));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

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
}
