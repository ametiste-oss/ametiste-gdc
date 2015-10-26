package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code exclude} function.
 * <p>
 * {@code exclude} takes a metric or a wildcard seriesList, followed by a regular expression in double quotes.
 * Excludes metrics that match the regular expression.
 */
public class Exclude implements Function<String, String> {

    private final String pattern;

    public Exclude(String pattern) {
        notEmpty(pattern, "'pattern' must be not empty string");
        this.pattern = pattern;
    }

    @Override
    public String apply(String s) {
        return String.format("exclude(%s,'%s')", s, pattern);
    }
}
