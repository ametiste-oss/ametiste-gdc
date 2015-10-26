package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code integral} function.
 * <p>
 * {@code integral} will show the sum over time, sort of like a continuous addition function.
 * Useful for finding totals or trends in metrics that are collected per minute.
 */
public class Integral implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "integral(" + s + ")";
    }
}
