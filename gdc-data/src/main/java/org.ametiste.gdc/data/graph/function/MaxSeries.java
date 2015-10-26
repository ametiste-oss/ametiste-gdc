package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code maxSeries} function.
 * <p>
 * {@code maxSeries} takes one metric or a wildcard seriesList.
 * For each datapoint from each metric passed in, pick the maximum value and graph it.
 */
public class MaxSeries implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "maxSeries(" + s + ")";
    }
}
