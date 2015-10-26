package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.Graph;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.Validate.*;

/**
 * {@code NoDefaultsDashboardBuilder} is implementation of {@code DashboardBuilder} interface that help to construct
 * complex graphite dashboard without default time and size settings.
 * <p>
 * This settings can be omitted without any problems because graphite provides own default parameters.
 * When dashboard loads from model graphite produces intersection of own and dashboard default parameters.
 * Besides dashboard use time config and size settings from corresponding sections instead of default settings.
 * In this case default parameters doesn't have any useful applications and only make build process more complex.
 * <p>
 * Builder creates with static method {@code createInstance} that create builder instance and set default values
 * for common part of parameters. Most of this taken from Graphite sources.
 */
public class NoDefaultsDashboardBuilder implements DashboardBuilder {

    public static final int DEFAULT_REFRESH_INTERVAL = 60000;
    public static final int DEFAULT_GRAPH_WIDTH = 330;
    public static final int DEFAULT_GRAPH_HEIGHT = 250;

    private String name;
    private List<Graph> graphs;

    private boolean enableAutoRefresh;
    private int refreshInterval;

    private boolean relativeTime;
    private int relativeStartQuantity;
    private RelativeTimeUnit relativeStartUnits;
    private int relativeUntilQuantity;
    private RelativeTimeUnit relativeUntilUnits;
    private Date startDate;
    private Date endDate;

    private int graphWidth;
    private int graphHeight;

    private NoDefaultsDashboardBuilder() {}

    /**
     * Create dashboard builder with default preset of parameters.
     * @param name default name of dashboard.
     * @return new {@code NoDefaultsDashboardBuilder} instance.
     */
    public static NoDefaultsDashboardBuilder createInstance(String name) {
        NoDefaultsDashboardBuilder builder = new NoDefaultsDashboardBuilder();
        builder.name = name;
        builder.graphs = new ArrayList<>();

        builder.enableAutoRefresh = false;
        builder.refreshInterval = DEFAULT_REFRESH_INTERVAL;

        builder.relativeTime = true;
        builder.relativeStartQuantity = 2;
        builder.relativeStartUnits = RelativeTimeUnit.DAYS;
        builder.relativeUntilQuantity = 0;
        builder.relativeUntilUnits = RelativeTimeUnit.NOW;

        builder.endDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(builder.endDate);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        builder.startDate = cal.getTime();

        builder.graphWidth = DEFAULT_GRAPH_WIDTH;
        builder.graphHeight = DEFAULT_GRAPH_HEIGHT;

        return builder;
    }

    @Override
    public DashboardBuilder setName(String name) {
        notEmpty(name, "'name' must be initialized");
        this.name = name;
        return this;
    }

    @Override
    public DashboardBuilder setGraphSize(int width, int height) {
        isTrue(width > 0, "'width' must be positive number");
        isTrue(height > 0, "'height' must be positive number");
        this.graphWidth = width;
        this.graphHeight = height;
        return this;
    }

    @Override
    public DashboardBuilder setAutoRefresh(boolean enabled, int interval) {
        isTrue(interval > 0, "'interval' must be positive number");
        this.enableAutoRefresh = enabled;
        this.refreshInterval = interval;
        return this;
    }

    @Override
    public DashboardBuilder setAbsoluteTimePeriod(final Date start, final Date end) {
        notNull(start, "'start' must be initialized");
        notNull(end, "'end' must be initialized");

        this.startDate = start;
        this.endDate = end;
        return this;
    }

    @Override
    public DashboardBuilder setRelativeTimePeriod(int start, RelativeTimeUnit startUnits,
                                                  int end, RelativeTimeUnit endUnits) {
        isTrue(start >= 0, "'start' must be not negative number");
        notNull(startUnits, "'startUnits' must be initialized");
        isTrue(end >= 0, "'end' must be not negative number");
        notNull(endUnits, "'endUnits' must be initialized");

        this.relativeStartQuantity = start;
        this.relativeStartUnits = startUnits;
        this.relativeUntilQuantity = end;
        this.relativeUntilUnits = endUnits;
        return this;
    }

    @Override
    public DashboardBuilder setTimeMode(boolean relative) {
        relativeTime = relative;
        return this;
    }

    @Override
    public DashboardBuilder addGraph(Graph graph) {
        notNull(graph, "'graph' must be initialized");
        this.graphs.add(graph);
        return this;
    }

    @Override
    public Dashboard build() {
        return new NoDefaultsDashboard(name,
                new DashboardRefreshConfig(enableAutoRefresh, refreshInterval),
                graphs,
                new DashboardTimeConfig(relativeTime, relativeStartQuantity, relativeStartUnits,
                        relativeUntilQuantity, relativeUntilUnits, startDate, endDate),
                new DashboardGraphSize(graphWidth, graphHeight));
    }
}
