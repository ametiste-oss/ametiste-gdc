package org.ametiste.gdc.bootstrap;

import org.ametiste.gdc.data.dashboard.Dashboard;
import org.ametiste.gdc.provider.DashboardProvider;
import org.ametiste.gdc.provider.GraphiteOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class {@code DashboardCreatorBootstrap} has one function - create/update dashboard in Graphite on service startup.
 * Creator takes provider for connection with Graphite instance and object that implements {@code Dashboard} interface.
 * <p>
 * By default class not throws any exception during dashboard creation process. If you want to enable exceptions
 * use constructor with third parameter {@code strict} with value true.
 */
public class DashboardCreatorBootstrap {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final boolean strict;
    private final Dashboard dashboard;
    private final DashboardProvider provider;

    /**
     * Create dashboard bootstrap creator that not throw any exceptions by default.
     * @param provider {@code DashboardProvider} for communication with Graphite instance.
     * @param dashboard {@code Dashboard} object for storing in graphite on startup.
     */
    public DashboardCreatorBootstrap(DashboardProvider provider, Dashboard dashboard) {
        this.strict = false;
        this.dashboard = dashboard;
        this.provider = provider;
    }

    /**
     * Create dashboard bootstrap creator that can throw exceptions when operations with Graphite fail.
     * @param provider {@code DashboardProvider} for communication with Graphite instance.
     * @param dashboard {@code Dashboard} object for storing in graphite on startup.
     * @param strict flag that define behavior on exception situations. If it is {@code true} class will be throw
     *               exceptions, in other case will not.
     */
    public DashboardCreatorBootstrap(DashboardProvider provider, Dashboard dashboard, boolean strict) {
        this.strict = strict;
        this.dashboard = dashboard;
        this.provider = provider;
    }

    /**
     * Create/update dashboard with specified name and description in Graphite instance connection with which provider
     * specified provider.
     * @throws GraphiteOperationException if {@code strict} is true and error occurred in process of dashboard creation.
     */
    public void create() throws GraphiteOperationException {
        try {
            provider.save(dashboard.getName(), dashboard.getDashboardString());
        } catch (GraphiteOperationException e) {
            if (strict) {
                throw e;
            } else {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
