package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code alias} function.
 * {@code alias} takes one metric or a wildcard seriesList and a string in quotes.
 * Prints the string instead of the metric name in the legend.
 */
public class Alias implements Function<String, String> {

    private final String newName;

    /**
     * Create new alias function for apply new name to metrics.
     * @param newName alias for metrics.
     */
    public Alias(String newName) {
        notEmpty(newName, "'newName' must not be empty string");
        this.newName = newName;
    }

    @Override
    public String apply(String s) {
        return String.format("alias(%s,'%s')", s, newName);
    }
}
