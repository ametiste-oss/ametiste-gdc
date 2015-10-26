package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code sumSeries} function.
 * <p>
 * {@code sumSeries} twill add metrics together and return the sum at each datapoint.
 * (See integral for a sum over time).
 */
public class SumSeries implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "sumSeries(" + s + ")";
    }
}
