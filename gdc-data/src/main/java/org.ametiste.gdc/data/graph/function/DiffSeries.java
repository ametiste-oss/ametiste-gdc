package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code diffSeries} function.
 * <p>
 * {@code diffSeries} subtracts series 2 through n from series 1.
 */
public class DiffSeries implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "diffSeries(" + s + ")";
    }
}
