package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code multiplySeriesWithWildcards} function.
 * {@code multiplySeriesWithWildcards} call multiplySeries after inserting wildcards at the given position(s).
 */
public class MultiplySeriesWithWildcards implements Function<String, String> {

    private final int position;

    public MultiplySeriesWithWildcards(int position) {
        isTrue(position >= 0, "'position' must not be negative: ", position);
        this.position = position;
    }

    @Override
    public String apply(String s) {
        return String.format("multiplySeriesWithWildcards(%s,%d)", s, position);
    }
}