package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code squareRoot} function.
 * {@code squareRoot} takes one metric or a wildcard seriesList, and computes the square root of each datapoint.
 */
public class SquareRoot implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "squareRoot(" + s + ")";
    }
}