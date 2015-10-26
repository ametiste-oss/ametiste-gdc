package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code minimumBelow} function.
 * <p>
 * {@code minimumBelow} takes one metric or a wildcard seriesList followed by a constant n.
 * Draws only the metrics with a minimum value below n.
 */
public class MinimumBelow implements Function<String, String> {

    private final int n;

    public MinimumBelow(int n) {
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("minimumBelow(%s,%d)", s, n);
    }
}