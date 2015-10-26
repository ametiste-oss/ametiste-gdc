package org.ametiste.gdc.data.graph;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Class represents target that specifies a path identifying one or several metrics, optionally with functions
 * acting on those metrics.
 */
public class GraphTarget {
    private final String metric;
    private Function<String, String> functions;

    /**
     * Create new target for metric(s) without functions.
     * @param metric metric description string.
     */
    public GraphTarget(String metric) {
        notEmpty(metric, "'metric' must be not empty string");

        this.metric = metric;
        functions = s -> s;
    }

    /**
     * Add function to acting on metrics.
     * Each next function proceed result of previous functions.
     * @param function {@code Function<String, String>} for acting on metrics.
     * @return current {@code GraphTarget} object. This makes possible to create complex target in one line.
     */
    public GraphTarget addFunction(Function<String, String> function) {
        notNull(function, "'function' must be initialized");

        functions = functions.andThen(function);
        return this;
    }

    /**
     * Method returns target as string without 'target' keyword at the beginning.
     * @return target string representation.
     */
    @Override
    public String toString() {
        return functions.apply(metric);
    }
}
