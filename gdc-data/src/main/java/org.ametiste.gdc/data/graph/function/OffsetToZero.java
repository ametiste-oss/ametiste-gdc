package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code offsetToZero} function.
 * {@code offsetToZero} offsets a metric or wildcard seriesList by subtracting the minimum value in the series from
 * each datapoint.
 * <p>
 * Useful to compare different series where the values in each series may be higher or lower on average but you’re
 * only interested in the relative difference.
 */
public class OffsetToZero implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "offsetToZero(" + s + ")";
    }
}