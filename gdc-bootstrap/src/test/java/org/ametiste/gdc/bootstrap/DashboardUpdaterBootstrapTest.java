package org.ametiste.gdc.bootstrap;

import org.ametiste.gdc.data.dashboard.Dashboard;
import org.ametiste.gdc.data.dashboard.DashboardFragment;
import org.ametiste.gdc.data.dashboard.NoDefaultsDashboardBuilder;
import org.ametiste.gdc.data.dashboard.NoDefaultsDashboardFragmentBuilder;
import org.ametiste.gdc.data.graph.Graph;
import org.ametiste.gdc.data.graph.GraphTarget;
import org.ametiste.gdc.data.graph.GraphiteGraphBuilder;
import org.ametiste.gdc.data.graph.function.AliasByNode;
import org.ametiste.gdc.provider.DashboardProvider;
import org.ametiste.gdc.provider.HttpDashboardProvider;
import org.junit.Test;

public class DashboardUpdaterBootstrapTest {

    @Test
    public void testSimple() throws Exception {
        Graph gr = GraphiteGraphBuilder.create().setTitle("test_2")
                .addTarget(new GraphTarget("stats.statsd.processing_time").toString())
                .build();

        Graph grb = GraphiteGraphBuilder.create().setTitle("test_3")
                .addTarget(new GraphTarget("stats.statsd.processing_time").addFunction(new AliasByNode(1, 2)).toString())
                .build();

        DashboardFragment dash = NoDefaultsDashboardFragmentBuilder.createInstance()
                .addGraph(gr)
                .setAutoRefresh(true, 10000)
                .build();

        Dashboard dashboard = NoDefaultsDashboardBuilder.createInstance("api_test")
                .addGraph(grb)
                .build();

        DashboardProvider provider = new HttpDashboardProvider("graphite.depositphotos.net", 1000, 1000);

        // TODO uncomment when mock graphite server
        /*
        new DashboardUpdaterBootstrap("api_test", dash, provider, true).update();
        new DashboardUpdaterBootstrap("api_test", dashboard, provider, true).update();
        */
    }
}