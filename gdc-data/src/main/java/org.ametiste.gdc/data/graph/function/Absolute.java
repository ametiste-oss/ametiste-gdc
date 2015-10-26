package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code absolute} function.
 * {@code absolute} takes one metric or a wildcard seriesList and applies the mathematical abs function
 * to each datapoint transforming it to its absolute value.
 */
public class Absolute implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "absolute(" + s + ")";
    }
}
