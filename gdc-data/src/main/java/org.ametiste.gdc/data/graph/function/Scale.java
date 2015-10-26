package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code scale} function.
 * {@code scale} takes one metric or a wildcard seriesList followed by a constant, and multiplies the datapoint by
 * the constant provided at each point.
 */
public class Scale implements Function<String, String> {

    private final int factor;

    public Scale(int factor) {
        this.factor = factor;
    }

    @Override
    public String apply(String s) {
        return String.format("scale(%s,%d)", s, factor);
    }
}