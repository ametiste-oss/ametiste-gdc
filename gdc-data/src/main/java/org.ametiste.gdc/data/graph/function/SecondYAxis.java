package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code secondYAxis} function.
 * {@code secondYAxis} graph the series on the secondary Y axis.
 */
public class SecondYAxis implements Function<String, String> {
    @Override
    public String apply(String s) {
        return "secondYAxis(" + s + ")";
    }
}
