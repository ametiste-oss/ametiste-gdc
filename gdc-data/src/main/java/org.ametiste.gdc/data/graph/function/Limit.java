package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code limit} function.
 * <p>
 * {@code limit} takes one metric or a wildcard seriesList followed by an integer N.
 * Only draw the first N metrics. Useful when testing a wildcard in a metric.
 */
public class Limit implements Function<String, String> {

    private final int n;

    public Limit(int n) {
        isTrue(n > 0, "'n' represents number of metrics and must be greater than zero: ", n);
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("limit(%s,%d)", s, n);
    }
}
