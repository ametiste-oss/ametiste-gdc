package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code sortByName} function.
 * {@code sortByName} takes one metric or a wildcard seriesList. Sorts the list of metrics by the metric name.
 */
public class SortByName implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "sortByName(" + s + ")";
    }
}