package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code minSeries} function.
 * <p>
 * {@code minSeries} takes one metric or a wildcard seriesList.
 * For each datapoint from each metric passed in, pick the minimum value and graph it.
 */
public class MinSeries implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "minSeries(" + s + ")";
    }
}
