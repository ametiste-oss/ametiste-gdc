package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code highestMax} function.
 * <p>
 * {@code highestMax} takes one metric or a wildcard seriesList followed by an integer N.
 * <p>
 * Out of all metrics passed, draws only the N metrics with the highest maximum value in the time period specified.
 */
public class HighestMax implements Function<String, String> {

    private final int n;

    public HighestMax(int n) {
        isTrue(n > 0, "'n' represents number of metrics and must be greater than zero: ", n);
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("highestMax(%s,%d)", s, n);
    }
}
