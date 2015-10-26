package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code averageBelow} function.
 * <p>
 * {@code averageBelow} takes one metric or a wildcard seriesList followed by an integer N.
 * Out of all metrics passed, draws only the metrics with an average value below N for the time period specified.
 */
public class AverageBelow implements Function<String, String> {

    private final int n;

    public AverageBelow(int n) {
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("averageBelow(%s,%d)", s, n);
    }
}
