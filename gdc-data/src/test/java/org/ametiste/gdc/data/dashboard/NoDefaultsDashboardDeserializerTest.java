package org.ametiste.gdc.data.dashboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NoDefaultsDashboardDeserializerTest {

    @Test
    public void testParseSimpleDashboard() throws Exception {
        String etalon = "{\"name\": \"TEST_1\",\"refreshConfig\":{\"interval\":10000,\"enabled\":true}," +
                "\"graphs\":[[\"target=alias(com.stat.*.cpu,'cpu')\",{\"target\":[\"alias(com.stat.*.cpu," +
                "'cpu')\"],\"title\": \"Graph 1\",\"from\": \"-2d\",\"until\": \"now\"}," +
                "\"/render?width=500&height=300&title=Graph 1&from=-2d&until=now&target=" +
                "alias(com.stat.*.cpu,'cpu')\"],[\"target=absolute(org.ametiste.foo.*.speed)\"," +
                "{\"target\":[\"absolute(org.ametiste.foo.*.speed)\"],\"title\": \"Graph 2\",\"from\": \"-2d\",\"until\":" +
                " \"now\"},\"/render?width=500&height=300&title=" +
                "Graph 2&from=-2d&until=now&target=absolute(org.ametiste.foo.*.speed)\"]],\"timeConfig\":" +
                "{\"type\":\"relative\",\"relativeStartQuantity\":\"2\",\"relativeStartUnits\":\"d\"," +
                "\"relativeUntilQuantity\":\"\",\"relativeUntilUnits\":\"now\",\"startDate\":" +
                "\"2015-06-04T14:06:23\",\"startTime\":\"14:06 PM\",\"endDate\":\"2015-06-05T14:06:23\"," +
                "\"endTime\":\"14:06 PM\"},\"graphSize\":{\"width\":500, \"height\":300}}";

        Gson gson = new GsonBuilder().registerTypeAdapter(Dashboard.class, new NoDefaultsDashboardDeserializer())
                .create();

        Dashboard dashboard = gson.fromJson(etalon, Dashboard.class);

        assertNotNull(dashboard);
        assertEquals("TEST_1", dashboard.getName());
        assertEquals(etalon, dashboard.getDashboardString());
    }
}