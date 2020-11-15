package com.amos.p1.backend.from_client_to_backend;

public class Incident {

    private final int locationX;
    private final int locationY;
    private final String type;

    public Incident(int x, int y, String type) {
        this.locationX = x;
        this.locationY = y;
        this.type = type;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "locationX=" + locationX +
                ", locationY=" + locationY +
                ", type='" + type + '\'' +
                '}';
    }

}
