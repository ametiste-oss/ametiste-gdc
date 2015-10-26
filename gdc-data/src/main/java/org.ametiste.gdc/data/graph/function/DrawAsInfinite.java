package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code drawAsInfinite} function.
 * <p>
 * {@code drawAsInfinite} takes one metric or a wildcard seriesList. If the value is zero, draw the line at 0.
 * If the value is above zero, draw the line at infinity. If the value is null or less than zero, do not draw the line.
 * <p>
 * Useful for displaying on/off metrics, such as exit codes. (0 = success, anything else = failure.)
 */
public class DrawAsInfinite implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "drawAsInfinite(" + s + ")";
    }
}
