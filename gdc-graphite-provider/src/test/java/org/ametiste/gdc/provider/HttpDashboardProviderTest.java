package org.ametiste.gdc.provider;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpDashboardProviderTest {
    private static final String GRAPHITE_HOST = "http://graphite.local.net";

    private HttpDashboardProvider provider;
    private HttpClient client;

    @Before
    public void setUp() throws Exception {
        client = mock(HttpClient.class);
        provider = new HttpDashboardProvider(GRAPHITE_HOST, 1000, 1000);

        Field clientField = provider.getClass().getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(provider, client);
    }

    @Test
    public void testSaveSuccess() throws Exception {
        HttpEntity entity = new StringEntity("{\"success\":true}");
        when(client.execute(any(HttpHost.class), any(HttpPost.class)))
                .thenReturn(createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON));

        provider.save("test", "{}");
    }

    @Test(expected = GraphiteOperationException.class)
    public void testSaveFailed() throws Exception {
        HttpEntity entity = new StringEntity("{\"success\":false}");
        when(client.execute(any(HttpHost.class), any(HttpPost.class)))
                .thenReturn(createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON));

        provider.save("test", "{}");
    }

    @Test
    public void testLoadSuccess() throws Exception {
        String dashboardName = "dash";

        when(client.execute(any(HttpHost.class), any(HttpGet.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();

            HttpGet request = (HttpGet)args[1];
            if (request.getURI().getPath().endsWith(dashboardName)) {
                HttpEntity entity = new StringEntity("{\"state\":{}}");
                return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
            } else {
                HttpEntity entity = new StringEntity("{\"error\":\"Dashboard " + dashboardName + " does not exist.\"}");
                return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
            }
        });

        String dashboard = provider.load(dashboardName);

        assertNotNull(dashboard);
        assertEquals(dashboard, "{}");
    }

    @Test(expected = GraphiteOperationException.class)
    public void testLoadFailNotFound() throws Exception {
        String dashboardName = "dash";

        when(client.execute(any(HttpHost.class), any(HttpGet.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();

            HttpGet request = (HttpGet) args[1];
            if (request.getURI().getPath().endsWith(dashboardName)) {
                HttpEntity entity = new StringEntity("{\"state\":{}}");
                return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
            } else {
                HttpEntity entity = new StringEntity("{\"error\":\"Dashboard does not exist.\"}");
                return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
            }
        });

        provider.load("test");
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        String dashboardName = "dash";

        when(client.execute(any(HttpHost.class), any(HttpGet.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();

            HttpGet request = (HttpGet) args[1];
            if (request.getURI().getPath().endsWith(dashboardName)) {
                HttpEntity entity = new StringEntity("{\"success\":true}");
                return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
            } else {
                HttpEntity entity = new StringEntity("{\"error\":\"Dashboard does not exist.\"}");
                return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
            }
        });

        provider.delete(dashboardName);
    }

    @Test(expected = GraphiteOperationException.class)
    public void testDeleteFailed() throws Exception {
        String dashboardName = "dash";

        when(client.execute(any(HttpHost.class), any(HttpGet.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();

            HttpGet request = (HttpGet) args[1];
            if (request.getURI().getPath().endsWith(dashboardName)) {
                HttpEntity entity = new StringEntity("{\"success\":true}");
                return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
            } else {
                HttpEntity entity = new StringEntity("{\"error\":\"Dashboard does not exist.\"}");
                return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
            }
        });

        provider.delete("test");
    }


    @Test
    public void testFindQueryResults() throws Exception {
        when(client.execute(any(HttpHost.class), any(HttpPost.class))).thenAnswer(invocation -> {
            HttpEntity entity = new StringEntity("{\"dashboards\":[{\"name\":\"andy\"}, " +
                    "{\"name\":\"candy\"}]}");
            return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
        });

        List<String> dashboards = provider.find("an");

        assertNotNull(dashboards);
        assertEquals(2, dashboards.size());
        assertEquals("andy", dashboards.get(0));
    }

    @Test
    public void testFindQueryResultsEmpty() throws Exception {
        when(client.execute(any(HttpHost.class), any(HttpPost.class))).thenAnswer(invocation -> {
            HttpEntity entity = new StringEntity("{\"dashboards\":[]}");
            return createHttpResponse(200, "OK", entity, ContentType.APPLICATION_JSON);
        });

        List<String> dashboards = provider.find("an");

        assertNotNull(dashboards);
        assertEquals(0, dashboards.size());
    }

    private HttpResponse createHttpResponse(int code, String reasonPhrase, HttpEntity entity, ContentType contentType) {
        HttpResponse response = new BasicHttpResponse(
                new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), code, reasonPhrase));
        response.setEntity(entity);
        response.setHeader("Content-Type", contentType.toString());
        return response;
    }
}