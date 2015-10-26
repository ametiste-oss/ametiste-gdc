package org.ametiste.gdc.data.graph;

import java.util.List;
import java.util.Map;

/**
 * Interface {@code Graph} describes external interface of Graph object.
 * <p>
 * Interface provides possibility of change time period and size of graph. This opportunities uses by dashboard
 * to set same parameters for all graphs that dashboard includes.
 * Also interface provides functionality for create render url that can be used for rendering standalone graph.
 */
public interface Graph {

    /**
     * Return title of graph.
     * @return string with title of graph or {@code null} if title not specified.
     */
    String getTitle();

    /**
     * Return start time point of displayed time period.
     * @return formatted string with time value.
     */
    String getFrom();

    /**
     * Set time point for begin of displayed time period.
     * @param from string that contains time point in specified format. Value may be {@code null}.
     *             In this case 'from' parameter will be missing and default value "-24h" will be use.
     */
    void setFrom(String from);

    /**
     * Return end time point of displayed time period.
     * @return formatted string with time value.
     */
    String getUntil();

    /**
     * Set time point for end of displayed time period.
     * @param until string that contains time point in specified format. Value may be {@code null}.
     *             In this case 'until' parameter will be missing and current time will be use as end time point.
     */
    void setUntil(String until);

    /**
     * @return map with custom parameters.
     */
    Map<String, String> getCustomParameters();

    /**
     * Change size of graph.
     * @param width width of graph in points.
     * @param height height of graph in points.
     */
    void resize(int width, int height);

    /**
     * Create list of targets that contains graph.
     * @return list of target strings. If no one target in graph method will return empty list.
     */
    List<String> createTargetsList();

    /**
     * Create render url for graph with stored in {@code GraphiteGraph} parameters.
     * @return string with render url.
     */
    String createRenderUrl();
}