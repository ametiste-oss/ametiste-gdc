package org.ametiste.gdc.data.graph;

import org.ametiste.gdc.data.RelativeTimeUnit;

import java.util.*;

import static org.ametiste.gdc.data.GraphiteTimeConverter.*;
import static org.apache.commons.lang3.Validate.*;

/**
 * Builder for GraphiteGraph complex objects.
 */
public class GraphiteGraphBuilder implements GraphBuilder {

    public static final int DEFAULT_GRAPH_WIDTH = 330;
    public static final int DEFAULT_GRAPH_HEIGHT = 250;

    private static final String DATE_MUST_BE_INITIALIZED = "'date' must be initialized";

    private final Map<String, String> params = new HashMap<>();
    private List<String> targets;
    private String title;
    private String from;
    private String until;
    private int width;
    private int height;

    private GraphiteGraphBuilder() {}

    /**
     * Create builder for {@code GraphiteGraph} with default parameters.
     * Note: default width is 330 and height is 250. This values taken form graphite documentation.
     * By default from and until value are omitted and time interval will be last 24 hours from now.
     * @return new instance of {@code GraphBuilder} with default parameters.
     */
    public static GraphiteGraphBuilder create() {
        GraphiteGraphBuilder builder = new GraphiteGraphBuilder();
        builder.targets = new ArrayList<>();
        builder.width = DEFAULT_GRAPH_WIDTH;
        builder.height = DEFAULT_GRAPH_HEIGHT;
        return builder;
    }

    @Override
    public GraphiteGraphBuilder addTarget(String target) {
        notEmpty(target, "'target' must be not empty string");
        targets.add(target);
        return this;
    }

    @Override
    public GraphBuilder setTitle(String title) {
        notEmpty(title, "'title' must be not empty string");
        this.title = title;
        return this;
    }

    @Override
    public GraphiteGraphBuilder setSize(int width, int height) {
        isTrue(width > 0, "'width' must be positive number");
        isTrue(height > 0, "'height' must be positive number");

        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public GraphiteGraphBuilder setFrom(int quantity, RelativeTimeUnit units) {
        isTrue(quantity >= 0, "'quantity' must not be negative");
        notNull(units, "'units' must be initialized");

        this.from = convert(quantity, units, true);
        return this;
    }

    @Override
    public GraphiteGraphBuilder setFromDate(Date date) {
        notNull(date, DATE_MUST_BE_INITIALIZED);

        this.from = convertGraphDate(date);
        return this;
    }

    @Override
    public GraphiteGraphBuilder setFromDateTime(Date date) {
        notNull(date, DATE_MUST_BE_INITIALIZED);

        this.from = convertGraph(date);
        return this;
    }

    @Override
    public GraphiteGraphBuilder setUntil(int quantity, RelativeTimeUnit units) {
        isTrue(quantity >= 0, "'quantity' must not be negative");
        notNull(units, "'units' must be initialized");

        this.until = convert(quantity, units, true);
        return this;
    }

    @Override
    public GraphiteGraphBuilder setUntilDate(Date date) {
        notNull(date, DATE_MUST_BE_INITIALIZED);

        this.from = convertGraphDate(date);
        return this;
    }

    @Override
    public GraphiteGraphBuilder setUntilDateTime(Date date) {
        notNull(date, DATE_MUST_BE_INITIALIZED);

        this.from = convertGraph(date);
        return this;
    }

    @Override
    public GraphiteGraphBuilder setUntilNow() {
        this.until = null;
        return this;
    }

    @Override
    public GraphBuilder setCustomParameter(String name, String value) {
        notEmpty(name, "'name' must be not empty string");
        notEmpty(value, "'value' must be not empty string");

        params.put(name, value);
        return this;
    }

    @Override
    public Graph build() {
        return new GraphiteGraph(this.targets, this.title, this.from, this.until, this.width, this.height, this.params);
    }
}