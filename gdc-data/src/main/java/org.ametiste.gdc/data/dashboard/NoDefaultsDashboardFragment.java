package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.Graph;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class {@code NoDefaultsDashboardFragment} implements {@code DashboardFragment} interface and represents model of
 * dashboard fragment. Class not contains default graph parameters ({@code defaultGraphParams}).
 * <p>
 * This class is package-local and used by {@code NoDefaultsDashboardFragmentBuilder} to create
 * {@code DashboardFragment} object.
 *
 * @see DashboardFragment
 * @see NoDefaultsDashboardFragmentBuilder
 */
class NoDefaultsDashboardFragment implements DashboardFragment {

    private final List<Graph> graphs;
    private final Boolean refreshEnabled;
    private final Integer refreshInterval;
    private final Boolean timeConfigRelative;
    private final Integer relativeStartQuantity;
    private final RelativeTimeUnit relativeStartUnits;
    private final Integer relativeUntilQuantity;
    private final RelativeTimeUnit relativeUntilUnits;
    private final Date startDate;
    private final Date endDate;
    private final Integer graphWidth;
    private final Integer graphHeight;


    public NoDefaultsDashboardFragment(List<Graph> graphs, Boolean refreshEnabled, Integer refreshInterval,
                                       Boolean timeConfigRelative,
                                       Integer relativeStartQuantity, RelativeTimeUnit relativeStartUnits,
                                       Integer relativeUntilQuantity, RelativeTimeUnit relativeUntilUnits,
                                       Date startDate, Date endDate,
                                       Integer graphWidth, Integer graphHeight) {
        this.graphs = graphs;
        this.refreshEnabled = refreshEnabled;
        this.refreshInterval = refreshInterval;
        this.timeConfigRelative = timeConfigRelative;
        this.relativeStartQuantity = relativeStartQuantity;
        this.relativeStartUnits = relativeStartUnits;
        this.relativeUntilQuantity = relativeUntilQuantity;
        this.relativeUntilUnits = relativeUntilUnits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.graphWidth = graphWidth;
        this.graphHeight = graphHeight;
    }

    @Override
    public List<Graph> graphs() {
        if (graphs == null) {
            return null;
        } else {
            return Collections.unmodifiableList(graphs);
        }
    }

    @Override
    public Boolean refreshEnabled() {
        return refreshEnabled;
    }

    @Override
    public Integer refreshInterval() {
        return refreshInterval;
    }

    @Override
    public Boolean timeConfigRelative() {
        return timeConfigRelative;
    }

    @Override
    public Integer relativeStartQuantity() {
        return relativeStartQuantity;
    }

    @Override
    public RelativeTimeUnit relativeStartUnits() {
        return relativeStartUnits;
    }

    @Override
    public Integer relativeUntilQuantity() {
        return relativeUntilQuantity;
    }

    @Override
    public RelativeTimeUnit relativeUntilUnits() {
        return relativeUntilUnits;
    }

    @Override
    public Date startDate() {
        return startDate;
    }

    @Override
    public Date endDate() {
        return endDate;
    }

    @Override
    public Integer graphWidth() {
        return graphWidth;
    }

    @Override
    public Integer graphHeight() {
        return graphHeight;
    }
}
