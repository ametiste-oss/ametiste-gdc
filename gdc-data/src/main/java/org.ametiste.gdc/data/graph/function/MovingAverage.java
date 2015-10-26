package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code movingAverage} function.
 * <p>
 * {@code movingAverage} graphs the moving average of a metric (or metrics) over a fixed number of past points,
 * or a time interval.
 * <p>
 * Takes one metric or a wildcard seriesList followed by a number N of datapoints or a quoted string with a length of
 * time like ‘1hour’ or ‘5min’ (See from / until in the render_api for examples of time formats).
 * <p>
 * Graphs the average of the preceding datapoints for each point on the graph. All previous datapoints are set to
 * None at the beginning of the graph.
 */
public class MovingAverage implements Function<String, String> {

    private final Integer windowSize;
    private final String windowSizeString;

    public MovingAverage(int windowSize) {
        isTrue(windowSize > 0, "'windowSize' must be positive number: ", windowSize);
        this.windowSize = windowSize;
        this.windowSizeString = null;
    }

    public MovingAverage(String windowSizeString) {
        notEmpty(windowSizeString, "'windowSizeString' must be not empty string");
        this.windowSize = null;
        this.windowSizeString = windowSizeString;
    }

    @Override
    public String apply(String s) {
        if (windowSizeString == null) {
            return String.format("movingAverage(%s,%d)", s, windowSize);
        } else {
            return String.format("movingAverage(%s,'%s')", s, windowSizeString);
        }
    }
}
