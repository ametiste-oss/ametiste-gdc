package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code areaBetween} function.
 * <p>
 * {@code areaBetween} draws the vertical area in between the two series in seriesList.
 * <p>
 * Useful for visualizing a range such as the minimum and maximum latency for a service. areaBetween expects exactly
 * one argument that results in exactly two series. The order of the lower and higher values series does not matter.
 * The visualization only works when used in conjunction with areaMode=stacked.
 */
public class AreaBetween implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "areaBetween(" + s + ")";
    }
}
