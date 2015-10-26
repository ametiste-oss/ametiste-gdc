package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code currentAbove} function.
 * <p>
 * {@code currentAbove} takes one metric or a wildcard seriesList followed by an integer N.
 * Out of all metrics passed, draws only the metrics whose value is above N at the end of the time period specified.
 */
public class CurrentAbove implements Function<String, String> {

    private final int n;

    public CurrentAbove(int n) {
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("currentAbove(%s,%d)", s, n);
    }
}
