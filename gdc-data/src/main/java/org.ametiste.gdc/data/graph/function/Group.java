package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code group} function.
 * <p>
 * {@code group} takes an arbitrary number of seriesLists and adds them to a single seriesList.
 * This is used to pass multiple seriesLists to a function which only takes one.
 */
public class Group implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "group(" + s + ")";
    }
}
