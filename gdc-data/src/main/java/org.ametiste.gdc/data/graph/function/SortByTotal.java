package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code sortByTotal} function.
 * {@code sortByTotal} takes one metric or a wildcard seriesList. Sorts the list of metrics by the sum of values
 * across the time period specified.
 */
public class SortByTotal implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "sortByTotal(" + s + ")";
    }
}