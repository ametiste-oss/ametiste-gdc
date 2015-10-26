package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code aliasSub} function.
 * {@code aliasSub} runs series names through a regex search/replace.
 */
public class AliasSub implements Function<String, String> {
    private final String search;
    private final String replace;

    public AliasSub(String search, String replace) {
        notEmpty(search, "'search' must be not empty string");
        notEmpty(replace, "'replace' must be not empty string");

        this.search = search;
        this.replace = replace;
    }

    @Override
    public String apply(String s) {
        return String.format("aliasSub(%s,%s,%s)", s, search, replace);
    }
}
