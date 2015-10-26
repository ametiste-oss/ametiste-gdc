package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code lowestAverage} function.
 * <p>
 * {@code lowestAverage} takes one metric or a wildcard seriesList followed by an integer N.
 * Out of all metrics passed, draws only the bottom N metrics with the lowest average value for
 * the time period specified.
 */
public class LowestAverage implements Function<String, String> {

    private final int n;

    public LowestAverage(int n) {
        isTrue(n > 0, "'n' represents number of metrics and must be greater than zero: ", n);
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("lowestAverage(%s,%d)", s, n);
    }
}