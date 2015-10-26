package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code sumSeriesWithWildcards} function.
 * {@code sumSeriesWithWildcards} call sumSeries after inserting wildcards at the given position(s).
 */
public class SumSeriesWithWildcards implements Function<String, String> {

    private final int position;

    public SumSeriesWithWildcards(int position) {
        isTrue(position >= 0, "'position' must not be negative: ", position);
        this.position = position;
    }

    @Override
    public String apply(String s) {
        return String.format("sumSeriesWithWildcards(%s,%d)", s, position);
    }
}