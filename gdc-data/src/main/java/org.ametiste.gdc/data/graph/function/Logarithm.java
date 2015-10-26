package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code logarithm} function.
 * <p>
 * {@code logarithm} takes one metric or a wildcard seriesList, a base, and draws the y-axis in logarithmic format.
 * If base is omitted, the function defaults to base 10.
 */
public class Logarithm implements Function<String, String> {

    private final Integer base;

    public Logarithm() {
        base = null;
    }

    public Logarithm(int base) {
        isTrue(base > 0, "'base' must be positive number");
        this.base = base;
    }

    @Override
    public String apply(String s) {
        if (base == null) {
            return "logarithm(" + s + ")";
        } else {
            return String.format("logarithm(%s,%d)", s, base);
        }
    }
}
