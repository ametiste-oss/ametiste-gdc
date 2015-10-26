package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * {@code Function} interface implementation that implement {@code groupByNode} function.
 * <p>
 * {@code groupByNode} takes a serieslist and maps a callback to subgroups within as defined by a common node.
 */
public class GroupByNode implements Function<String, String> {

    private final int nodeNum;
    private final String callback;

    public GroupByNode(int nodeNum, String callback) {
        isTrue(nodeNum >= 0, "'nodeNum' must be not negative number: ", nodeNum);
        notEmpty(callback, "'callback' must be not empty string");

        this.nodeNum = nodeNum;
        this.callback = callback;
    }

    @Override
    public String apply(String s) {
        return String.format("groupByNode(%s,%d,'%s')", s, nodeNum, callback);
    }
}
