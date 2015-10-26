package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code keepLastValue} function.
 * <p>
 * {@code keepLastValue} takes one metric or a wildcard seriesList, and optionally a limit to the number of ‘None’ values to skip over.
 * Continues the line with the last received value when gaps (‘None’ values) appear in your data, rather than
 * breaking your line.
 */
public class KeepLastValue implements Function<String, String> {

    private final Integer limit;

    public KeepLastValue() {
        limit = null;
    }

    public KeepLastValue(int limit) {
        isTrue(limit > 0, "'limit' must be greater than zero: ", limit);
        this.limit = limit;
    }

    @Override
    public String apply(String s) {
        if (limit == null) {
            return "keepLastValue(" + s + ")";
        } else {
            return String.format("keepLastValue(%s,%d)", s, limit);
        }
    }
}
