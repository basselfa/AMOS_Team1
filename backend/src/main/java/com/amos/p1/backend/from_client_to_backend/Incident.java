package com.amos.p1.backend.from_client_to_backend;

/**
 * Marshalling durch Jackson: https://www.baeldung.com/jackson
 *
 * Damit das Marshalling funktioniert muss jede Klasse die get-Methoden fuer die Daten bereitstellen
 */
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
