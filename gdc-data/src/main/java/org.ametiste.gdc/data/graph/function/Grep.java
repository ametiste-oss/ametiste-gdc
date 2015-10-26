package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code grep} function.
 * <p>
 * {@code grep} takes a metric or a wildcard seriesList, followed by a regular expression in double quotes.
 * Excludes metrics that don’t match the regular expression.
 */
public class Grep implements Function<String, String> {

    private final String pattern;

    public Grep(String pattern) {
        notEmpty(pattern, "'pattern' must be not empty string");
        this.pattern = pattern;
    }

    @Override
    public String apply(String s) {
        return String.format("grep(%s,'%s')", s, pattern);
    }
}
