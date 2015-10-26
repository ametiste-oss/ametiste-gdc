package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code pow} function.
 * {@code pow} takes one metric or a wildcard seriesList followed by a constant, and raises the datapoint by the power
 * of the constant provided at each point.
 */
public class Pow implements Function<String, String> {

    private final int factor;

    public Pow(int factor) {
        this.factor = factor;
    }

    @Override
    public String apply(String s) {
        return String.format("pow(%s,%d)", s, factor);
    }
}
