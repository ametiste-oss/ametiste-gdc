package org.ametiste.gdc.data.graph;

import org.ametiste.gdc.data.RelativeTimeUnit;

import java.util.Date;

/**
 * Interface {@code GraphBuilder} provide protocol for creating Graphite graphs.
 * <p>
 * Builder not provides possibility of change display format. By default format is png (image in png format).
 * This parameter is omitted because common usage of builder are build graphs for dashboards that represents
 * graphs as images.
 * If necessary change display format use {@code setCustomParameter} method with "format" name value.
 */
public interface GraphBuilder {

    /**
     * Add description of target object to target list of graph.
     * @param target string representation of target object to add.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder addTarget(String target);

    /**
     * Set graph title.
     * @param title graph title.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setTitle(String title);

    /**
     * Set size parameters for graph.
     * @param width width of graph in points.
     * @param height height of graph in points.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setSize(int width, int height);

    /**
     * Set 'from' time point with relative value.
     * @param quantity value of relative interval. Must be not negative.
     * @param units units of time interval.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setFrom(int quantity, RelativeTimeUnit units);

    /**
     * Set 'from' time point with date value.
     * @param date {@code Date} object that contains target date value.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setFromDate(Date date);

    /**
     * Set 'from' time point with date and time value.
     * @param date {@code Date} object that contains target date and time value.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setFromDateTime(Date date);

    /**
     * Set 'until' time point with relative value.
     * @param quantity value of relative interval. Must be not negative.
     * @param units units of time interval.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setUntil(int quantity, RelativeTimeUnit units);

    /**
     * Set 'until' time point with date value.
     * @param date {@code Date} object that contains target date value.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setUntilDate(Date date);

    /**
     * Set 'from' time point with date and time value.
     * @param date {@code Date} object that contains target date and time value.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setUntilDateTime(Date date);

    /**
     * Set 'until' time point to current time.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setUntilNow();

    /**
     * Add custom parameter to graph render url.
     * @param name name of parameter.
     * @param value parameter value in string format.
     * @return instance of current {@code GraphBuilder}.
     */
    GraphBuilder setCustomParameter(String name, String value);

    /**
     * Build {@code GraphiteGraph} object with specified in builder parameters.
     * @return new {@code Graph} object.
     */
    Graph build();
}

