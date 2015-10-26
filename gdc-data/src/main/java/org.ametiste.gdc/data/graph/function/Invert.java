package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code invert} function.
 * <p>
 * {@code invert} takes one metric or a wildcard seriesList, and inverts each datapoint (i.e. 1/x).
 */
public class Invert implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "invert(" + s + ")";
    }
}
