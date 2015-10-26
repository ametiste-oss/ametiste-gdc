package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.Graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * {@code NoDefaultsDashboardFragmentBuilder} is implementation of {@code DashboardFragmentBuilder} interface
 * that help to construct complex dashboard fragments.
 * @see NoDefaultsDashboardFragment
 */
 public class NoDefaultsDashboardFragmentBuilder implements DashboardFragmentBuilder {

    private List<Graph> graphs;
    private Boolean refreshEnabled;
    private Integer refreshInterval;
    private Boolean timeConfigRelative;
    private Integer relativeStartQuantity;
    private RelativeTimeUnit relativeStartUnits;
    private Integer relativeUntilQuantity;
    private RelativeTimeUnit relativeUntilUnits;
    private Date startDate;
    private Date endDate;
    private Integer graphWidth;
    private Integer graphHeight;

    private NoDefaultsDashboardFragmentBuilder() {}

    /**
     * Create dashboard builder with default empty list of graphs.
     * @return new {@code NoDefaultsDashboardFragmentBuilder} instance.
     */
    public static NoDefaultsDashboardFragmentBuilder createInstance() {
        NoDefaultsDashboardFragmentBuilder builder = new NoDefaultsDashboardFragmentBuilder();
        builder.graphs = new ArrayList<>();
        return builder;
    }

    @Override
    public DashboardFragmentBuilder setGraphSize(int width, int height) {
        isTrue(width > 0, "'width' must be positive number");
        isTrue(height > 0, "'height' must be positive number");

        this.graphWidth = width;
        this.graphHeight = height;
        return this;
    }

    @Override
    public DashboardFragmentBuilder setAutoRefresh(boolean enabled, int interval) {
        isTrue(interval > 0, "'interval' must be positive number");

        this.refreshEnabled = enabled;
        this.refreshInterval = interval;
        return this;
    }

    @Override
    public DashboardFragmentBuilder setAbsoluteTimePeriod(Date start, Date end) {
        notNull(start, "'start' must be initialized");
        notNull(end, "'end' must be initialized");

        this.startDate = start;
        this.endDate = end;
        return this;
    }

    @Override
    public DashboardFragmentBuilder setRelativeTimePeriod(int start, RelativeTimeUnit startUnits,
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
    public DashboardFragmentBuilder setTimeMode(boolean relative) {
        this.timeConfigRelative = relative;
        return this;
    }

    @Override
    public DashboardFragmentBuilder addGraph(Graph graph) {
        notNull(graph, "'graph' must be initialized");

        graphs.add(graph);
        return this;
    }

    @Override
    public DashboardFragment build() {
        return new NoDefaultsDashboardFragment(graphs, refreshEnabled, refreshInterval,
                timeConfigRelative, relativeStartQuantity, relativeStartUnits, relativeUntilQuantity, relativeUntilUnits,
                startDate, endDate, graphWidth, graphHeight);
    }
}
