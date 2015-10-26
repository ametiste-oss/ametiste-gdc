package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code countSeries} function.
 * <p>
 * {@code countSeries} draws a horizontal line representing the number of nodes found in the seriesList.
 */
public class CountSeries implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "countSeries(" + s + ")";
    }
}
