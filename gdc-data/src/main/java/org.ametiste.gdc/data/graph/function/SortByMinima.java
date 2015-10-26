package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code sortByMinima} function.
 * {@code sortByMinima} takes one metric or a wildcard seriesList.
 * <p>
 * Sorts the list of metrics by the lowest value across the time period specified.
 */
public class SortByMinima implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "sortByMinima(" + s + ")";
    }
}