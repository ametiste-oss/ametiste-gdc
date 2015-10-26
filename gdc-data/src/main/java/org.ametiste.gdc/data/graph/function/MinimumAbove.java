package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code minimumAbove} function.
 * <p>
 * {@code minimumAbove} takes one metric or a wildcard seriesList followed by a constant n.
 * Draws only the metrics with a minimum value above n.
 */
public class MinimumAbove implements Function<String, String> {

    private final int n;

    public MinimumAbove(int n) {
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("minimumAbove(%s,%d)", s, n);
    }
}
