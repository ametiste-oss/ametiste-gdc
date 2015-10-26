package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.Graph;

import java.util.Date;

/**
 * Interface {@code DashboardBuilder} provides protocol to create dashboard object.
 */
public interface DashboardBuilder {

    /**
     * Set name of dashboard.
     * @param name not empty string with name for dashboard.
     * @return {@code DashboardBuilder} instance.
     */
    DashboardBuilder setName(String name);

    /**
     * Set size of dashboard graphs.
     * @param width width of graph in points.
     * @param height height of graph in points.
     * @return {@code DashboardBuilder} instance.
     */
    DashboardBuilder setGraphSize(int width, int height);

    /**
     * Set auto refresh dashboard parameters.
     * @param enabled enable auto refresh.
     * @param interval time interval for refresh (in milliseconds).
     * @return {@code DashboardBuilder} instance.
     */
    DashboardBuilder setAutoRefresh(boolean enabled, int interval);

    /**
     * Set absolute time period parameters.
     * @param start start time point.
     * @param end end time point.
     * @return {@code DashboardBuilder} instance.
     */
    DashboardBuilder setAbsoluteTimePeriod(Date start, Date end);

    /**
     * Set relative time period parameters.
     * @param start value of start time point.
     * @param startUnits time units of start time point.
     * @param end value of end time point.
     * @param endUnits time units of end time point.
     * @return {@code DashboardBuilder} instance.
     */
    DashboardBuilder setRelativeTimePeriod(int start, RelativeTimeUnit startUnits, int end, RelativeTimeUnit endUnits);

    /**
     * Set time mode for dashboard.
     * @param relative use relative time parameters. If set false absolute parameters will be use.
     * @return {@code DashboardBuilder} instance.
     */
    DashboardBuilder setTimeMode(boolean relative);

    /**
     * Add graph to dashboard.
     * @param graph {@code Graph} interface implementation.
     * @return {@code DashboardBuilder} instance.
     */
    DashboardBuilder addGraph(Graph graph);

    /**
    DashboardBuilder apply(String json);

    /**
     * Build dashboard.
     * @return {@code Dashboard} interface implementation.
     */
    Dashboard build();
}
