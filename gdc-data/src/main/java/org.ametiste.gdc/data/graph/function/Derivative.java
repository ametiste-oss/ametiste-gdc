package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code derivative} function.
 * <p>
 * This is the opposite of the integral function. This is useful for taking a running total metric and calculating
 * the delta between subsequent data points.
 * <p>
 * This function does not normalize for periods of time, as a true derivative would. Instead see the perSecond()
 * function to calculate a rate of change over time.
 */
public class Derivative implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "derivative(" + s + ")";
    }
}
