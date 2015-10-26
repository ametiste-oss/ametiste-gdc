package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code stddevSeries} function.
 * {@code stddevSeries} takes one metric or a wildcard seriesList. Draws the standard deviation of all metrics
 * passed at each time.
 */
public class StddevSeries implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "stddevSeries(" + s + ")";
    }
}
