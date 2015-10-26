package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code averageAbove} function.
 * <p>
 * {@code averageAbove} takes one metric or a wildcard seriesList followed by an integer N.
 * Out of all metrics passed, draws only the metrics with an average value above N for the time period specified.
 */
public class AverageAbove implements Function<String, String> {

    private final int n;

    public AverageAbove(int n) {
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("averageAbove(%s,%d)", s, n);
    }
}
