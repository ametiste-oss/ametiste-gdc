package org.ametiste.gdc.data.graph;

import org.ametiste.gdc.data.graph.function.Absolute;
import org.ametiste.gdc.data.graph.function.Alias;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GraphTargetTest {

    @Test
    public void testCreateTargetWithoutFunctions() throws Exception {
        String metric = "cpu.*.speed";

        GraphTarget target = new GraphTarget(metric);
        String result = target.toString();

        assertNotNull(result);
        assertEquals(metric, result);
    }

    @Test
    public void testCreateTargetWithOneFunction() throws Exception {
        String metric = "cpu.*.speed";
        String alias = "my_metric";

        GraphTarget target = new GraphTarget(metric);
        String result = target.addFunction(new Alias(alias)).toString();

        assertNotNull(result);
        assertEquals(String.format("alias(%s,'%s')", metric, alias), result);
    }

    @Test
    public void testCreateTargetWithFewFunctions() throws Exception {
        String metric = "cpu.*.speed";
        String alias = "my_metric";

        GraphTarget target = new GraphTarget(metric);
        String result = target.addFunction(new Alias(alias)).addFunction(new Absolute()).toString();

        assertNotNull(result);
        assertEquals(String.format("absolute(alias(%s,'%s'))", metric, alias), result);
    }
}