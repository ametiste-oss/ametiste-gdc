package org.ametiste.gdc.data.dashboard;

/**
 * Interface provides protocol for communicate with dashboards.
 * Any implementation of {@code Dashboard} must implement mechanism for represent dashboard
 * in text form.
 * <p>
 * Interface implementation must provide update of graph time and size parameters for each included graph.
 * It will guarantee same visual parameters for dashboard and rendered graphics.
 * <p>
 * Any dashboard implementation must provide valid dashboard description that we can send to graphite without errors.
 * <p>
 * {@code Dashboard} extends {@code DashboardFragment} and can be used as fragment to update other dashboards.
 * In this case will be update all: settings and graphs.
 */
public interface Dashboard extends DashboardFragment {

    /**
     * Return name of dashboard.
     * @return name of dashboard.
     */
    String getName();

    /**
     * Return text representation of dashboard.
     * @return string with dashboard description.
     */
    String getDashboardString();

    /**
     * Update dashboard with available data from fragment.
     * <p>
     * Note: if fragment contains graphs dashboard add dashboards without title to list and replace existing graph
     * with fragment graph with same title.
     * @param fragment {@code DashboardFragment} that contains information that must to be replaced current data
     *                  in dashboard.
     */
    void update(DashboardFragment fragment);
}
