package com.amos.p1.backend.demo;

import java.util.ArrayList;
import java.util.List;

public class Comparisons {

    private List<Comparison> comparisons = new ArrayList<>();

    public List<Comparison> getComparisons() {
        return comparisons;
    }

    public void addComparison(Comparison comparison){
        comparisons.add(comparison);
    }

    @Override
    public String toString() {
        return "Comparisons{" +
                "comparisons=" + comparisons +
                '}';
    }
}
