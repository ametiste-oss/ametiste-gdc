package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code offset} function.
 * {@code offset} takes one metric or a wildcard seriesList followed by a constant, and adds the constant
 * to each datapoint.
 */
public class Offset implements Function<String, String> {

    private final int factor;

    public Offset(int factor) {
        this.factor = factor;
    }

    @Override
    public String apply(String s) {
        return String.format("offset(%s,%d)", s, factor);
    }
}
