package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code divideSeries} function.
 * <p>
 * {@code divideSeries} takes a dividend metric and a divisor metric and draws the division result.
 * <p>
 * A constant may not be passed. To divide by a constant, use the scale() function (which is essentially
 * a multiplication operation) and use the inverse of the dividend. (Division by 8 = multiplication by 1/8 or 0.125).
 */
public class DivideSeries implements Function<String, String> {

    private final String divisorSeries;

    public DivideSeries(String divisorSeries) {
        notEmpty(divisorSeries, "'divisorSeries' must be not empty string");
        this.divisorSeries = divisorSeries;
    }

    @Override
    public String apply(String s) {
        return String.format("divisorSeries(%s,%s)", s, divisorSeries);
    }
}
