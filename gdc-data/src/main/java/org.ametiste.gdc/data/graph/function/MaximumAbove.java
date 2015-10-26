package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code maximumAbove} function.
 * <p>
 * {@code maximumAbove} takes one metric or a wildcard seriesList followed by a constant n.
 * Draws only the metrics with a maximum value above n.
 */
public class MaximumAbove implements Function<String, String> {

    private final int n;

    public MaximumAbove(int n) {
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("maximumAbove(%s,%d)", s, n);
    }
}