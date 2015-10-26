package org.ametiste.gdc.bootstrap;

import org.ametiste.gdc.data.dashboard.*;
import org.ametiste.gdc.provider.DashboardProvider;
import org.ametiste.gdc.provider.GraphiteOperationException;
import com.google.gson.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class {@code DashboardUpdaterBootstrap} provides functionality for dashboard updating in Graphite on service startup.
 * Creator takes provider for connection with Graphite instance and object that implements {@code DashboardFragment}
 * interface.
 * <p>
 * By default class not throws any exception during dashboard update process. If you want to enable exceptions
 * use constructor with third parameter {@code strict} with value true.
 */
public class DashboardUpdaterBootstrap {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String dashboardName;
    private final DashboardFragment fragment;
    private final DashboardProvider provider;

    private boolean strict;
    private JsonDeserializer<Dashboard> deserializer;
    private Class<? extends DashboardBuilder> builder;

    /**
     * Create updater with default dashboard builder and deserializer. Updater ignores any exceptions during operation
     * with graphite. It guarantee that service not crash.
     * @param dashboardName name of target dashboard.
     * @param fragment fragment with date for update.
     * @param provider graphite provider for communication with Graphite instance.
     */
    public DashboardUpdaterBootstrap(String dashboardName, DashboardFragment fragment, DashboardProvider provider) {
        this(dashboardName, fragment, provider, false);
    }

    /**
     * Create default updater with strict parameter.
     * @param dashboardName name of target dashboard.
     * @param fragment fragment with date for update.
     * @param provider graphite provider for communication with Graphite instance.
     * @param strict flag specify exception processing. If it set to {@code true} updater throws exceptions, if
     *               {@code false} updater ignores exception and only write report to log.
     */
    public DashboardUpdaterBootstrap(String dashboardName, DashboardFragment fragment, DashboardProvider provider,
                                     boolean strict) {
        this(dashboardName, fragment, provider, strict, new NoDefaultsDashboardDeserializer());
    }

    /**
     * Create updater with custom deserializer for parsing json to dashboard.
     * @param dashboardName name of target dashboard.
     * @param fragment fragment with date for update.
     * @param provider graphite provider for communication with Graphite instance.
     * @param strict flag specify exception processing. If it set to {@code true} updater throws exceptions, if
     *               {@code false} updater ignores exception and only write report to log.
     * @param deserializer {@code JsonDeserializer<Dashboard>} implementation for parsing loaded description.
     */
    public DashboardUpdaterBootstrap(String dashboardName, DashboardFragment fragment, DashboardProvider provider,
                                     boolean strict, JsonDeserializer<Dashboard> deserializer) {
        this(dashboardName, fragment, provider, strict, deserializer, NoDefaultsDashboardBuilder.class);
    }

    /**
     * Create updater with custom dashboard builder for creating new default dashboard in case when dashboard with
     * specified name not exists in Graphite instance.
     * @param dashboardName name of target dashboard.
     * @param fragment fragment with date for update.
     * @param provider graphite provider for communication with Graphite instance.
     * @param strict flag specify exception processing. If it set to {@code true} updater throws exceptions, if
     *               {@code false} updater ignores exception and only write report to log.
     * @param deserializer {@code JsonDeserializer<Dashboard>} implementation for parsing loaded description.
     * @param defaultBuilderClass {@code DashboardBuilder} implementation.
     */
    public DashboardUpdaterBootstrap(String dashboardName, DashboardFragment fragment, DashboardProvider provider,
                                     boolean strict, JsonDeserializer<Dashboard> deserializer,
                                     Class<? extends DashboardBuilder> defaultBuilderClass) {
        this.strict = strict;
        this.dashboardName = dashboardName;
        this.fragment = fragment;
        this.provider = provider;
        this.deserializer = deserializer;
        this.builder = defaultBuilderClass;
    }

    /**
     * Update dashboard with specified name in Graphite instance. If dashboard not exists method create new default
     * dashboard, update it with fragment and save dashboard in Graphite instance.
     * @throws GraphiteOperationException when any error occurred in process of dashboard updating.
     */
    public void update() throws GraphiteOperationException {
        Dashboard dashboard;
        try {
            if (!provider.find(dashboardName).contains(dashboardName)) {
                dashboard = builder.newInstance().setName(dashboardName).build();
            } else {
                dashboard = provider.load(dashboardName, deserializer);
            }

            dashboard.update(fragment);
            provider.save(dashboardName, dashboard.getDashboardString());
        } catch (Exception e) {
            if (strict) {
                throw new GraphiteOperationException(e.getMessage(), e);
            } else {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
