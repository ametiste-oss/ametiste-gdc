package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code sortByMaxima} function.
 * {@code sortByMaxima} takes one metric or a wildcard seriesList. Sorts the list of metrics by the maximum value
 * across the time period specified.
 * <p>
 * Useful with the areaMode=all parameter, to keep the lowest value lines visible.
 */
public class SortByMaxima implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "sortByMaxima(" + s + ")";
    }
}
