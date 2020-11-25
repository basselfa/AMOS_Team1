package com.amos.p1.backend.demo;

import java.util.ArrayList;
import java.util.List;

public class Comparison {

    private List<Location> shape = new ArrayList<>();
    private Incident provider1;
    private Incident provider2;

    public Comparison(List<Location> shape, Incident provider1, Incident provider2) {
        this.shape = shape;
        this.provider1 = provider1;
        this.provider2 = provider2;
    }

    public List<Location> getShape() {
        return shape;
    }

    public Incident getProvider1() {
        return provider1;
    }

    public Incident getProvider2() {
        return provider2;
    }

    @Override
    public String toString() {
        return "Comparison{" +
                "shape=" + shape +
                ", provider1=" + provider1 +
                ", provider2=" + provider2 +
                '}';
    }
}
