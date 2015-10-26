package org.ametiste.gdc.data.graph.function;

import java.util.function.Function;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * {@code Function} interface implementation that implement {@code aliasByNode} function.
 * {@code aliasByNode} takes a seriesList and applies an alias derived from one or more “node” portion/s
 * of the target name. Node indices are 0 indexed.
 */
public class AliasByNode implements Function<String, String> {

    private final int[] nodes;

    public AliasByNode(int... nodes) {
        checkNodes(nodes);
        this.nodes = nodes;
    }

    @Override
    public String apply(String s) {
        return String.format("aliasByNode(%s,%s)", s, convertNodesToString(nodes));
    }

    private void checkNodes(int[] nodes) {
        isTrue(nodes.length > 0, "'nodes' must contain at least one element");
        for (int n : nodes) {
            isTrue(n >= 0, "'nodes' must not contains negative numbers");
        }
    }

    private String convertNodesToString(int[] nodes) {
        String result = String.valueOf(nodes[0]);
        for (int i = 1; i < nodes.length; i++) {
            result = result + "," + nodes[i];
        }
        return result;
    }
}
