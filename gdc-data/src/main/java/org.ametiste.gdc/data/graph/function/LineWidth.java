package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code lineWidth} function.
 * <p>
 * {@code lineWidth} takes one metric or a wildcard seriesList, followed by a float F.
 * <p>
 * Draw the selected metrics with a line width of F, overriding the default value of 1, or the lineWidth=X.X parameter.
 * Useful for highlighting a single metric out of many, or having multiple line widths in one graph
 */
public class LineWidth implements Function<String, String> {

    private final float width;

    public LineWidth(float width) {
        isTrue(width > 0.0, "'width' must be positive number: ", width);
        this.width = width;
    }

    @Override
    public String apply(String s) {
        return String.format("lineWidth(%s,%.1f)", s, width);
    }
}
