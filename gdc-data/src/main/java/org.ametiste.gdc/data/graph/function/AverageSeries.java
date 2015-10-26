package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code averageSeries} function.
 * <p>
 * {@code averageSeries} takes one metric or a wildcard seriesList. Draws the average value of all metrics
 * passed at each time.
 */
public class AverageSeries implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "averageSeries(" + s + ")";
    }
}
