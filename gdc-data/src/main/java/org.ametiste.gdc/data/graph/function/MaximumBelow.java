package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code maximumBelow} function.
 * <p>
 * {@code maximumBelow} takes one metric or a wildcard seriesList followed by a constant n.
 * Draws only the metrics with a maximum value below n.
 */
public class MaximumBelow implements Function<String, String> {

    private final int n;

    public MaximumBelow(int n) {
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("maximumBelow(%s,%d)", s, n);
    }
}