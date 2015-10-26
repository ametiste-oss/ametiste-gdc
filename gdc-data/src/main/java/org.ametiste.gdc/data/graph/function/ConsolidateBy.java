package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code consolidateBy} function.
 * <p>
 * {@code consolidateBy} takes one metric or a wildcard seriesList and a consolidation function name.
 * Valid function names are ‘sum’, ‘average’, ‘min’, and ‘max’.
 * <p>
 * When a graph is drawn where width of the graph size in pixels is smaller than the number of datapoints to be graphed,
 * Graphite consolidates the values to to prevent line overlap. The consolidateBy() function changes the consolidation
 * function from the default of ‘average’ to one of ‘sum’, ‘max’, or ‘min’. This is especially useful in sales graphs,
 * where fractional values make no sense and a ‘sum’ of consolidated values is appropriate.
 */
public class ConsolidateBy implements Function<String, String> {

    private final String consolidationFunc;

    public ConsolidateBy(String consolidationFunc) {
        notEmpty(consolidationFunc, "'consolidationFunc' must be not empty string");
        this.consolidationFunc = consolidationFunc;
    }

    @Override
    public String apply(String s) {
        return String.format("consolidateBy(%s,'%s')", s, consolidationFunc);
    }
}
