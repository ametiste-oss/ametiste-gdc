package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code color} function.
 * <p>
 * {@code color} assigns the given color to the seriesList.
 * Color can be alias name like 'green', 'gray', etc. Or as RGB (RGBA) string. For example, 'ff0000', '6464ffaa'.
 */
public class Color implements Function<String, String> {

    private final String color;

    public Color(String color) {
        notEmpty(color, "'color' must be not empty string");
        this.color = color;
    }

    @Override
    public String apply(String s) {
        return String.format("color(%s,'%s')", s, color);
    }
}
