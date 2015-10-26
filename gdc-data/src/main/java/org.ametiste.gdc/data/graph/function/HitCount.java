package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code hitcount} function.
 * <p>
 * {@code hitcount} estimate hit counts from a list of time series. This function assumes the values in each time
 * series represent hits per second. It calculates hits per some larger interval such as per day or per hour.
 * <p>
 * This function is like summarize(), except that it compensates automatically for different time scales
 * (so that a similar graph results from using either fine-grained or coarse-grained records) and handles
 * rarely-occurring events gracefully.
 */
public class HitCount implements Function<String, String> {

    private final String intervalString;

    public HitCount(String intervalString) {
        this.intervalString = intervalString;
    }

    @Override
    public String apply(String s) {
        return String.format("hitcount(%s,'%s')", s, intervalString);
    }
}
