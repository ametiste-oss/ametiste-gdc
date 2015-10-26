package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.Graph;

import java.util.Date;

/**
 * Interface {@code DashboardFragmentBuilder} provides protocol to create dashboard fragment object.
 */
public interface DashboardFragmentBuilder {

    /**
     * Set size of dashboard graphs.
     * @param width width of graph in points.
     * @param height height of graph in points.
     * @return {@code DashboardFragmentBuilder} instance.
     */
    DashboardFragmentBuilder setGraphSize(int width, int height);

    /**
     * Set auto refresh dashboard parameters.
     * @param enabled enable auto refresh.
     * @param interval time interval for refresh (in milliseconds).
     * @return {@code DashboardFragmentBuilder} instance.
     */
    DashboardFragmentBuilder setAutoRefresh(boolean enabled, int interval);

    /**
     * Set absolute time period parameters.
     * @param start start time point.
     * @param end end time point.
     * @return {@code DashboardFragmentBuilder} instance.
     */
    DashboardFragmentBuilder setAbsoluteTimePeriod(Date start, Date end);

    /**
     * Set relative time period parameters.
     * @param start value of start time point.
     * @param startUnits time units of start time point.
     * @param end value of end time point.
     * @param endUnits time units of end time point.
     * @return {@code DashboardFragmentBuilder} instance.
     */
    DashboardFragmentBuilder setRelativeTimePeriod(int start, RelativeTimeUnit startUnits, int end, RelativeTimeUnit endUnits);

    /**
     * Set time mode for dashboard fragment.
     * @param relative use relative time parameters. If set false absolute parameters will be use.
     * @return {@code DashboardFragmentBuilder} instance.
     */
    DashboardFragmentBuilder setTimeMode(boolean relative);

    /**
     * Add graph to dashboard fragment.
     * @param graph {@code Graph} interface implementation.
     * @return {@code DashboardFragmentBuilder} instance.
     */
    DashboardFragmentBuilder addGraph(Graph graph);

    /**
     * Build dashboard fragment.
     * @return {@code DashboardFragment} interface implementation.
     */
    DashboardFragment build();
}
