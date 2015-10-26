package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code isNonNull} function.
 * <p>
 * {@code isNonNull} takes a metric or wild card seriesList and counts up how many non-null values are specified.
 * <p>
 * This is useful for understanding which metrics have data at a given point in time (ie, to count which
 * servers are alive).
 */
public class IsNonNull implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "isNotNull(" + s + ")";
    }
}
