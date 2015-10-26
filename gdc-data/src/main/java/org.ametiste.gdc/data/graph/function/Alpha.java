package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code alpha} function.
 * {@code alpha} assigns the given alpha transparency setting to the series. Takes a float value between 0 and 1.
 */
public class Alpha implements Function<String, String> {

    private final float alpha;

    public Alpha(float alpha) {
        isTrue(alpha >= 0.0 && alpha <= 1.0, "'alpha' must be in range [0..1]: ", alpha);
        this.alpha = alpha;
    }

    @Override
    public String apply(String s) {
        return String.format("alpha(%s,%.2f)", s, alpha);
    }
}
