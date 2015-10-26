package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code legendValue} function.
 * <p>
 * {@code legendValue} takes one metric or a wildcard seriesList and a string in quotes.
 * Appends a value to the metric name in the legend. Currently one or several of: last, avg, total, min, max.
 * The last argument can be si (default) or binary, in that case values will be formatted in the corresponding system.
 */
public class LegendValue implements Function<String, String> {

    private final String[] valueTypes;

    public LegendValue(String[] valueTypes) {
        notEmpty(valueTypes, "'valueTypes' must be not empty array");
        this.valueTypes = valueTypes.clone();
    }

    @Override
    public String apply(String s) {
        StringBuilder sb = new StringBuilder("legendValue(").append(s);
        for (String value : valueTypes) {
            sb.append(",'").append(value).append('\'');
        }
        return sb.append(')').toString();
    }
}
