package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code dashed} function.
 * <p>
 * {@code dashed} takes one metric or a wildcard seriesList, followed by a float F. Draw the selected metrics with
 * a dotted line with segments of length F If omitted, the default length of the segments is 5.0.
 */
public class Dashed implements Function<String, String> {
    private final Float length;

    public Dashed() {
        length = null;
    }

    public Dashed(float length) {
        this.length = length;
    }

    @Override
    public String apply(String s) {
        if (length == null) {
            return "dashed(" + s + ")";
        } else {
            return String.format("dashed(%s,%.2f)", s, length);
        }
    }
}
