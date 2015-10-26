package org.ametiste.gdc.data.graph;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.function.Alias;
import org.ametiste.gdc.data.graph.function.AliasByNode;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class GraphiteGraphTest {

    @Test
    public void testGraphCreation() throws Exception {
        String targetUrl = "/render?width=500&height=300&from=-2d&until=-1d" +
                "&target=aliasByNode(stat.*.cpu.speed,1)";

        Graph graph = GraphiteGraphBuilder.create()
                .addTarget(new GraphTarget("stat.*.cpu.speed").addFunction(new AliasByNode(1)).toString())
                .setSize(500, 300)
                .setFrom(2, RelativeTimeUnit.DAYS)
                .setUntil(1, RelativeTimeUnit.DAYS)
                .build();

        String url = graph.createRenderUrl();

        assertNotNull(url);
        assertEquals(targetUrl, url);
    }

    @Test
    public void testGraphCreationWithDate() throws Exception {
        String targetUrl = "/render?width=330&height=250&from=00:15_20150102&target=alias(stat.*.cpu.speed,'foo')";
        long relative = 1420157700;

        Graph graph = GraphiteGraphBuilder.create()
                .addTarget(new GraphTarget("stat.*.cpu.speed").addFunction(new Alias("foo")).toString())
                .setFromDateTime(new Date(relative * 1000))
                .build();

        String url = graph.createRenderUrl();

        assertNotNull(url);
        assertEquals(targetUrl, url);
    }

    @Test
    public void testCreateDashboardDescription() throws Exception {
        String target = "aliasByNode(stat.*.cpu.speed,1)";
        String targetUrl = "/render?width=500&height=300&from=-2d&until=-1d&target=" + target;

        Graph graph = GraphiteGraphBuilder.create()
                .addTarget(new GraphTarget("stat.*.cpu.speed").addFunction(new AliasByNode(1)).toString())
                .setSize(500, 300)
                .setFrom(2, RelativeTimeUnit.DAYS)
                .setUntil(1, RelativeTimeUnit.DAYS)
                .build();

        List<String> targets = graph.createTargetsList();
        String renderUrl = graph.createRenderUrl();

        assertNotNull(targets);
        assertEquals(1, targets.size());
        assertEquals(target, targets.get(0));

        assertNotNull(renderUrl);
        assertEquals(targetUrl, renderUrl);
    }
}