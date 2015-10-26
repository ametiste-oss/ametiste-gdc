package org.ametiste.gdc.data.graph;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

class GraphiteGraph implements Graph {

    private final List<String> targets;
    private final String title;
    private final Map<String, String> params;
    private String from;
    private String until;
    private int width;
    private int height;

    /**
     * Create graph with specified size and displayed time period.
     * <p>
     * Title, from and until parameters may be {@code null}. In this case this parameters not include in
     * render url and Graphite will use default values.
     * @param targets list of metrics (optional with functions).
     * @param title graph title.
     * @param from string that contains time point for begin of displayed period in specified format.
     * @param until string that contains time point for end of displayed period in specified format.
     * @param width width of graph in points.
     * @param height height of graph in points.
     */
    public GraphiteGraph(List<String> targets, String title, String from, String until,
                         int width, int height, Map<String, String> params) {
        notNull(targets, "'targets' must be initialized");
        notNull(params, "'params' must be initialized");
        isTrue(width > 0, "'width' must be positive number");
        isTrue(height > 0, "'height' must be positive number");

        this.targets = targets;
        this.title = title;
        this.from = from;
        this.until = until;
        this.width = width;
        this.height = height;
        this.params = params;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getUntil() {
        return until;
    }

    @Override
    public void setUntil(String until) {
        this.until = until;
    }

    @Override
    public Map<String, String> getCustomParameters() {
        return Collections.unmodifiableMap(params);
    }

    @Override
    public void resize(int width, int height) {
        isTrue(width > 0, "'width' must be positive number");
        isTrue(height > 0, "'height' must be positive number");

        this.width = width;
        this.height = height;
    }

    @Override
    public List<String> createTargetsList() {
        return targets;
    }

    @Override
    public String createRenderUrl() {
        StringBuilder sb = new StringBuilder("/render?");
        sb.append("width=").append(width);
        sb.append("&height=").append(height);

        if (title != null) {
            sb.append("&title=").append(title);
        }
        if (from != null) {
            sb.append("&from=").append(from);
        }
        if (until != null) {
            sb.append("&until=").append(until);
        }

        targets.stream().forEach(p -> sb.append("&target=").append(p));
        params.entrySet().stream().forEach(p -> sb.append(String.format("&%s=%s", p.getKey(), p.getValue())));

        return sb.toString();
    }
}
