package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code multiplySeries} function.
 * <p>
 * {@code multiplySeries} takes two or more series and multiplies their points. A constant may not be used.
 * To multiply by a constant, use the scale() function.
 */
public class MultiplySeries implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "multiplySeries(" + s + ")";
    }
}
