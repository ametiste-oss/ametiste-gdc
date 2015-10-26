package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code highestAverage} function.
 * <p>
 * {@code highestAverage} takes one metric or a wildcard seriesList followed by an integer N.
 * Out of all metrics passed, draws only the top N metrics with the highest average value for the time period specified.
 */
public class HighestAverage implements Function<String, String> {

    private final int n;

    public HighestAverage(int n) {
        isTrue(n > 0, "'n' represents number of metrics and must be greater than zero: ", n);
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("highestAverage(%s,%d)", s, n);
    }
}
