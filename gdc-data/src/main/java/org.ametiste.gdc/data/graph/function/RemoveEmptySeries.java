package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code removeEmptySeries} function.
 * {@code removeEmptySeries} takes one metric or a wildcard seriesList. Out of all metrics passed,
 * draws only the metrics with not empty data.
 */
public class RemoveEmptySeries implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "removeEmptySeries(" + s + ")";
    }
}
