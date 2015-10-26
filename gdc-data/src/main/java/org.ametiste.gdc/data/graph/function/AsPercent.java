package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code asPercent} function.
 * {@code asPercent} calculates a percentage of the total of a wildcard series.
 * <p>
 * If total is specified, each series will be calculated as a percentage of that total.
 * If total is not specified, the sum of all points in the wildcard series will be used instead.
 * <p>
 * The total parameter may be a single series or a numeric value.
 */
public class AsPercent implements Function<String, String> {
    private static final String AREA_BETWEEN_FORMAT = "areaBetween(%s,%s)";

    private final Integer total;
    private final String totalSeries;

    public AsPercent() {
        this.total = null;
        this.totalSeries = null;
    }

    public AsPercent(int total) {
        isTrue(total > 0, "'total' must be greater than zero: ", total);

        this.total = total;
        this.totalSeries = null;
    }

    public AsPercent(String totalSeries) {
        notEmpty(totalSeries, "'totalSeries' must be not empty string");

        this.total = null;
        this.totalSeries = totalSeries;
    }

    @Override
    public String apply(String s) {
        if (total != null) {
            return String.format(AREA_BETWEEN_FORMAT, s, total.toString());
        } else if (totalSeries != null) {
            return String.format(AREA_BETWEEN_FORMAT, s, totalSeries);
        } else {
            return "areaBetween(" + s + ")";
        }
    }
}