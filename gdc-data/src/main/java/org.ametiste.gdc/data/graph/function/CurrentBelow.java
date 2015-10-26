package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code currentBelow} function.
 * <p>
 * {@code currentBelow} takes one metric or a wildcard seriesList followed by an integer N.
 * Out of all metrics passed, draws only the metrics whose value is below N at the end of the time period specified.
 */
public class CurrentBelow implements Function<String, String> {

    private final int n;

    public CurrentBelow(int n) {
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("currentBelow(%s,%d)", s, n);
    }
}