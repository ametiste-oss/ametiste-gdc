package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code aliasByMetric} function.
 * {@code aliasByMetric} takes a seriesList and applies an alias derived from the base metric name.
 */
public class AliasByMetric implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "aliasByMetric(" + s + ")";
    }
}
