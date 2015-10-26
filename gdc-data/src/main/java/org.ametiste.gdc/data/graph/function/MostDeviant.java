package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code mostDeviant} function.
 * <p>
 * {@code mostDeviant} takes one metric or a wildcard seriesList followed by an integer N.
 * Draws the N most deviant metrics. To find the deviants, the standard deviation (sigma) of each series
 * is taken and ranked. The top N standard deviations are returned.
 */
public class MostDeviant implements Function<String, String> {

    private final int n;

    public MostDeviant(int n) {
        isTrue(n > 0, "'n' represents number of metrics and must be greater than zero: ", n);
        this.n = n;
    }

    @Override
    public String apply(String s) {
        return String.format("mostDeviant(%s,%d)", s, n);
    }
}