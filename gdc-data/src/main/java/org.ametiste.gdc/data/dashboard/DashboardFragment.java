package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.Graph;

import java.util.Date;
import java.util.List;

/**
 * Interface {@code DashboardFragment} provides protocol for communicate with dashboard fragment.
 * <p>
 * Dashboard fragment is a partially description of dashboard (settings, graphs, etc.). Primary usage is
 * update existing dashboards with fragment data.
 * <p>
 * {@code DashboardFragment} isn't complete dashboard and can't be send to graphite independently.
 */
public interface DashboardFragment {

    /**
     * Return list of graphs.
     * @return list of graphs or {@code null} if graphs not present in fragment.
     */
    List<Graph> graphs();

    /**
     * Returns refresh enable flag.
     * @return {@code true} if refresh enabled or {@code false} if disabled. Method returns {@code null} if parameter
     * not present in fragment.
     */
    Boolean refreshEnabled();

    /**
     * Returns refresh time interval.
     * @return interval value in milliseconds. Method returns {@code null} if parameter not present in fragment.
     */
    Integer refreshInterval();

    /**
     * Returns flag that signal what type of time configuration use.
     * @return {@code true} if relative time configuration use, {@code false} if absolute. Method returns {@code null}
     * if parameter not present in fragment.
     */
    Boolean timeConfigRelative();

    /**
     * Returns start time point quantity for relative time configuration.
     * @return positive value of quantity. Method returns {@code null} if parameter not present in fragment.
     */
    Integer relativeStartQuantity();

    /**
     * Returns start time units for relative time configuration.
     * @return {@code RelativeTimeUnit} constant that represents time units. Method returns {@code null}
     * if parameter not present in fragment.
     */
    RelativeTimeUnit relativeStartUnits();

    /**
     * Returns end time point quantity for relative time configuration.
     * @return positive value of quantity. Method returns {@code null} if parameter not present in fragment.
     */
    Integer relativeUntilQuantity();

    /**
     * Returns end time units for relative time configuration.
     * @return {@code RelativeTimeUnit} constant that represents time units. Method returns {@code null}
     * if parameter not present in fragment.
     */
    RelativeTimeUnit relativeUntilUnits();

    /**
     * Returns start date for absolute time configuration.
     * @return {@code Date} object that contains date and time of start time point. Method returns {@code null}
     * if parameter not present in fragment.
     */
    Date startDate();

    /**
     * Returns end date for absolute time configuration.
     * @return {@code Date} object that contains date and time of end time point. Method returns {@code null}
     * if parameter not present in fragment.
     */
    Date endDate();

    /**
     * Returns width of graphs for dashboard.
     * @return width in points for graphs in dashboard. Method returns {@code null} if parameter not present
     * in fragment.
     */
    Integer graphWidth();

    /**
     * Returns height of graphs for dashboard.
     * @return height in points for graphs in dashboard. Method returns {@code null} if parameter not present
     * in fragment.
     */
    Integer graphHeight();
}
