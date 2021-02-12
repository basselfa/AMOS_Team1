package com.amos.p1.backend.service.evaluation;

import com.amos.p1.backend.data.Incident;

/**
 * See Thesis page 38
 */
public class AngleMatcher extends Matcher {
    private static float angleTolerance = 15;
    private int confidence;
    private float angle;

    public AngleMatcher(Incident incident1, Incident incident2) {
        super(incident1, incident2);

        float angle1 = getAngle(
                start1Lat,
                start1Lng,
                end1Lat,
                end1Lng
        );

        float angle2 = getAngle(
                start2Lat,
                start2Lng,
                end2Lat,
                end2Lng
        );

        angle = Math.abs(angle1 - angle2);

        if (angle < angleTolerance) {
            confidence = 1;
        } else if (angle < angleTolerance * 2) {
            confidence = -1;
        } else {
            return;
        }
    }

    @Override
    public String getDescription() {
        return "[SearchRadiusMatcher: score " + confidence + " |isDropped" + isDropped() + " |angle" + angle + "]";
    }

    @Override
    public int getConfidence() {
        return confidence;
    }

    @Override
    public boolean isDropped() {
        return confidence == 0;
    }

    public double getDegree() {
        return angle;
    }

    //todo: check if coorect https://stackoverflow.com/questions/9970281/java-calculating-the-angle-between-two-points-in-degrees
    public float getAngle(double lat1, double lon1, double lat2, double lon2) {
        float angle = (float) Math.toDegrees(Math.atan2(lat1 - lat2, lon1 - lon2));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
}
