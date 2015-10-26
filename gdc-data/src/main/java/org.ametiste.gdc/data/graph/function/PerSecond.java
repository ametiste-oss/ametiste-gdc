package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

/**
 * {@code Function} interface implementation that implement {@code perSecond} function.
 * {@code perSecond} derivative adjusted for the series time interval.
 * This is useful for taking a running total metric and showing how many requests per second were handled.
 */
public class PerSecond implements Function<String, String> {

    @Override
    public String apply(String s) {
        return "perSecond(" + s + ")";
    }
}