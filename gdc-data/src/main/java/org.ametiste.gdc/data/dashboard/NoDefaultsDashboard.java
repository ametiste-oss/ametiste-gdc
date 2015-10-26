package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.Graph;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.ametiste.gdc.data.GraphiteTimeConverter.convert;
import static org.ametiste.gdc.data.GraphiteTimeConverter.convertGraph;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Class {@code NoDefaultsDashboard} implements {@code Dashboard} interface and represents model of dashboard
 * for Graphite in JSON string format without default graph parameters.
 * <p>
 * This class is package-local and used by {@code NoDefaultsDashboardBuilder} to create {@code Dashboard} object.
 * For creating dashboards use {@code DashboardBuilder} implementations.
 */
class NoDefaultsDashboard implements Dashboard {

    private final String name;
    private final List<Graph> graphs;
    private final DashboardRefreshConfig refreshConfig;
    private final DashboardTimeConfig timeConfig;
    private final DashboardGraphSize graphSize;

    /**
     * Create dashboard object with specified parameters.
     * <p>
     * Constructor provides update size and time period operations for all graphs.
     * @param name dashboard name.
     * @param refreshConfig dashboard refresh configuration.
     * @param graphs list of dashboard graphs.
     * @param timeConfig dashboard time configuration.
     * @param graphSize dashboard graph sizes.
     */
    public NoDefaultsDashboard(String name,
                               DashboardRefreshConfig refreshConfig,
                               List<Graph> graphs,
                               DashboardTimeConfig timeConfig,
                               DashboardGraphSize graphSize) {
        notEmpty(name, "'name' must be not empty string");
        notNull(refreshConfig, "'refreshConfig' must be initialized");
        notNull(graphs, "'graphs' must be initialized");
        notNull(timeConfig, "'timeConfig' must be initialized");
        notNull(graphSize, "'graphSize' must be initialized");

        this.name = name;
        this.refreshConfig = refreshConfig;
        this.graphs = graphs;
        this.timeConfig = timeConfig;
        this.graphSize = graphSize;

        updateSizeInGraphs(graphSize.getWidth(), graphSize.getHeight());
        updateTimePeriod(timeConfig);
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Represent dashboard object as JSON string.
     * @return JSON string with dashboard description.
     */
    @Override
    public String getDashboardString() {

        StringBuilder sb = new StringBuilder("{\"name\": \"").append(name).append("\",");
        sb.append("\"refreshConfig\":").append(refreshConfig.toJsonString()).append(',');

        sb.append("\"graphs\":[");
        if (!graphs.isEmpty()) {
            for (Graph graph : graphs) {
                sb.append(createGraphDescription(graph)).append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("],");

        sb.append("\"timeConfig\":").append(timeConfig.toJsonString()).append(',');
        sb.append("\"graphSize\":").append(graphSize.toJsonString()).append('}');

        return sb.toString();
    }

    @Override
    public void update(DashboardFragment fragment) {
        notNull(fragment, "'fragment' must be initialized");

        setIfNotNull(fragment.refreshEnabled(), refreshConfig::setEnabled);
        setIfNotNull(fragment.refreshInterval(), refreshConfig::setInterval);
        setIfNotNull(timeConfigRelative(), timeConfig::setType);
        setIfNotNull(fragment.relativeStartQuantity(), timeConfig::setRelativeStartQuantity);
        setIfNotNull(fragment.relativeStartUnits(), timeConfig::setRelativeStartUnits);
        setIfNotNull(fragment.relativeUntilQuantity(), timeConfig::setRelativeUntilQuantity);
        setIfNotNull(fragment.relativeUntilUnits(), timeConfig::setRelativeUntilUnits);
        setIfNotNull(fragment.startDate(), timeConfig::setStartDate);
        setIfNotNull(fragment.endDate(), timeConfig::setEndDate);
        setIfNotNull(fragment.graphWidth(), graphSize::setWidth);
        setIfNotNull(fragment.graphHeight(), graphSize::setHeight);

        if (fragment.graphs() != null) {
            Map<String, Integer> graphMap = graphs.stream().filter(g -> g.getTitle() != null)
                    .collect(Collectors.toMap(Graph::getTitle, graphs::indexOf));

            for (Graph graph : fragment.graphs()) {
                if (graph.getTitle() == null || !graphMap.containsKey(graph.getTitle())) {
                    graphs.add(graph);
                } else {
                    graphs.set(graphMap.get(graph.getTitle()), graph);
                }
            }

            updateTimePeriod(timeConfig);
            updateSizeInGraphs(graphSize.getWidth(), graphSize.getHeight());
        }
    }

    private <T> void setIfNotNull(T v, Consumer<T> setter) {
        if (v != null) setter.accept(v);
    }

    private void updateSizeInGraphs(int width, int height) {
        graphs.stream().forEach(p -> p.resize(width, height));
    }

    private void updateTimePeriod(DashboardTimeConfig timeConfig) {
        String start;
        String until;

        if (timeConfig.getType() == DashboardTimeConfig.TimeConfigType.RELATIVE) {
            start = convert(timeConfig.getRelativeStartQuantity(), timeConfig.getRelativeStartUnits(), true);
            until = convert(timeConfig.getRelativeUntilQuantity(), timeConfig.getRelativeUntilUnits(), true);
        } else {
            start = convertGraph(timeConfig.getStartDate());
            until = convertGraph(timeConfig.getEndDate());
        }

        graphs.stream().forEach(p -> {
            p.setFrom(start);
            p.setUntil(until);
        });
    }

    /**
     * Create dashboard description for graph.
     * If no targets in graph {@code IllegalStateException} raised.
     * @return string with graph description for dashboard.
     */
    private String createGraphDescription(Graph graph) {
        List<String> targets = graph.createTargetsList();
        if (targets.isEmpty()) {
            throw new IllegalStateException("no metric in graphs");
        }

        StringBuilder sb = new StringBuilder("[");
        appendTargetString(sb, targets);
        sb.append(',');
        appendTargetObject(sb, graph);
        sb.append(",\"").append(graph.createRenderUrl()).append("\"]");

        return sb.toString();
    }

    private void appendTargetString(StringBuilder builder, List<String> targets) {
        builder.append("\"target=").append(targets.get(0));
        if (targets.size() > 1) {
            for (int i = 1; i < targets.size(); i++) {
                builder.append("&target=").append(targets.get(i));
            }
        }
        builder.append('\"');
    }

    private void appendTargetObject(StringBuilder builder, Graph graph) {
        builder.append("{");
        appendTargets(builder, graph.createTargetsList());
        builder.append(",");

        if (graph.getTitle() != null) {
            builder.append("\"title\": \"").append(graph.getTitle()).append("\",");
        }

        builder.append("\"from\": \"").append(graph.getFrom()).append("\",");
        builder.append("\"until\": \"").append(graph.getUntil()).append("\"");

        graph.getCustomParameters().entrySet().stream().forEach(entry ->
                builder.append(",\"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\""));
        builder.append("}");
    }

    private void appendTargets(StringBuilder builder, List<String> targets) {
        builder.append("\"target\":[\"").append(targets.get(0)).append('\"');
        for (int i = 1; i < targets.size(); i++) {
            builder.append(",\"").append(targets.get(i)).append('\"');
        }
        builder.append("]");
    }

    @Override
    public List<Graph> graphs() {
        return Collections.unmodifiableList(graphs);
    }

    @Override
    public Boolean refreshEnabled() {
        return refreshConfig.isEnabled();
    }

    @Override
    public Integer refreshInterval() {
        return refreshConfig.getInterval();
    }

    @Override
    public Boolean timeConfigRelative() {
        return timeConfig.getType() == DashboardTimeConfig.TimeConfigType.RELATIVE;
    }

    @Override
    public Integer relativeStartQuantity() {
        return timeConfig.getRelativeStartQuantity();
    }

    @Override
    public RelativeTimeUnit relativeStartUnits() {
        return timeConfig.getRelativeStartUnits();
    }

    @Override
    public Integer relativeUntilQuantity() {
        return timeConfig.getRelativeUntilQuantity();
    }

    @Override
    public RelativeTimeUnit relativeUntilUnits() {
        return timeConfig.getRelativeUntilUnits();
    }

    @Override
    public Date startDate() {
        return timeConfig.getStartDate();
    }

    @Override
    public Date endDate() {
        return timeConfig.getEndDate();
    }

    @Override
    public Integer graphWidth() {
        return graphSize.getWidth();
    }

    @Override
    public Integer graphHeight() {
        return graphSize.getHeight();
    }
}