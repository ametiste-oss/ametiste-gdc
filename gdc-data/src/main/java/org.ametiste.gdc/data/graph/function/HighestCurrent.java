package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code highestCurrent} function.
 * <p>
 * {@code highestCurrent} takes one metric or a wildcard seriesList followed by an integer N.
 * Out of all metrics passed, draws only the N metrics with the highest value at the end of the time period specified.
 */
public class HighestCurrent implements Function<String, String> {

    private final int n;

    public HighestCurrent(int n) {
        isTrue(n > 0, "'n' represents number of metrics and must be greater than zero: ", n);
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("highestCurrent(%s,%d)", s, n);
    }
}
