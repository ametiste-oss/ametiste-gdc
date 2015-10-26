package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code fallbackSeries} function.
 * <p>
 * {@code fallbackSeries} takes a wildcard seriesList, and a second fallback metric. If the wildcard does not match
 * any series, draws the fallback metric.
 */
public class FallbackSeries implements Function<String, String> {

    private final String fallback;

    public FallbackSeries(String fallback) {
        notEmpty(fallback, "'fallback' must be not empty string");
        this.fallback = fallback;
    }

    @Override
    public String apply(String s) {
        return String.format("fallbackSeries(%s,%s)", s, fallback);
    }
}
