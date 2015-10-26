package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code cactiStyle} function.
 * <p>
 * {@code cactiStyle} takes a series list and modifies the aliases to provide column aligned output with
 * Current, Max, and Min values in the style of cacti. Optionally takes a “system” value to apply unit formatting
 * in the same style as the Y-axis.
 * <p>
 * A possible value for {@code system} is 'si', which would express your values in multiples of a thousand.
 * A second option is to use binary which will instead express your values in multiples of 1024 (useful for network
 * devices). Column alignment of the Current, Max, Min values works under two conditions:
 * you use a monospace font such as terminus and use a single cactiStyle call, as separate cactiStyle calls are not
 * aware of each other. In case you have different targets for which you would like to have cactiStyle to line up,
 * you can use group() to combine them before applying cactiStyle.
 */
public class CactiStyle implements Function<String, String> {

    private final String system;

    public CactiStyle() {
        system = null;
    }

    public CactiStyle(String system) {
        notEmpty(system, "'system' must be not empty string");
        this.system = system;
    }

    @Override
    public String apply(String s) {
        if (system == null) {
            return "cactiStyle(" + s + ")";
        } else {
            return String.format("cactiStyle(%s,'%s')", s, system);
        }
    }
}
