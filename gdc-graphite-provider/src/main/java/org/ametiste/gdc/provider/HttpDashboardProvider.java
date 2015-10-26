package org.ametiste.gdc.provider;

import com.google.gson.*;
import org.ametiste.gdc.data.dashboard.Dashboard;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.Validate.*;

/**
 * Default implementation of {@code DashboardProvider} interface that provides communication with Graphite
 * instance via HTTP API for common dashboard operations like create/update, load, delete, find.
 * <p>
 * {@code HttpDashboardProvider} uses Apache {@code HttpClient} as http client and gson library for parsing
 * Graphite responses.
 * <p>
 * Provider use authentication with username and password parameters if both are set.
 * If username and password not set provider will operate without authentication.
 */
public class HttpDashboardProvider implements DashboardProvider {

    private static final String NAME_MUST_BE_INITIALIZED = "'name' must be initialized";
    private static final String RESPONSE_IS_NOT_A_JSON = "Response is not a json object";

    private final HttpClient client;
    private final HttpHost host;

    /**
     * Create new instance of {@code HttpDashboardProvider} for communication with Graphite instance with
     * specified host and connection parameters, without authentication.
     * @param graphiteHost host of target Graphite instance. Must be valid URL.
     * @param connectTimeout connection timeout in milliseconds.
     * @param readTimeout read timeout in milliseconds.
     */
    public HttpDashboardProvider(String graphiteHost, int connectTimeout, int readTimeout) {
        notNull(graphiteHost, "'graphiteHost must be initialized.");
        isTrue(connectTimeout >= 0, "'connectTimeout must not be negative: ", connectTimeout);
        isTrue(readTimeout >= 0, "'readTimeout' must not be negative: ", readTimeout);

        this.client = createHttpClient(null, null, connectTimeout, readTimeout);
        this.host = HttpHost.create(graphiteHost);
    }

    /**
     * Create new instance of {@code HttpDashboardProvider} for communication with Graphite instance with
     * specified host, connection parameters and authentication credentials.
     * @param graphiteHost host of target Graphite instance. Must be valid URL.
     * @param username username for authentication on graphite host.
     * @param password password for authentication on graphite host.
     * @param connectTimeout connection timeout in milliseconds.
     * @param readTimeout read timeout in milliseconds.
     */
    public HttpDashboardProvider(String graphiteHost, String username, String password,
                                 int connectTimeout, int readTimeout) {
        notNull(graphiteHost, "'graphiteHost must be initialized.");
        notEmpty(username, "'username' must be not empty string");
        notEmpty(password, "'password' must be not empty string");
        isTrue(connectTimeout >= 0, "'connectTimeout must not be negative: ", connectTimeout);
        isTrue(readTimeout >= 0, "'readTimeout' must not be negative: ", readTimeout);

        this.client = createHttpClient(username, password, connectTimeout, readTimeout);
        this.host = HttpHost.create(graphiteHost);
    }


    @Override
    public void save(String name, String dashboard) throws GraphiteOperationException {
        notEmpty(name, NAME_MUST_BE_INITIALIZED);
        notEmpty(dashboard, "'dashboard' must be initialized");

        String response;
        try {
            HttpPost request = new HttpPost("/dashboard/save/" + name);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(Collections.singletonList(
                    new BasicNameValuePair("state", dashboard)));
            request.setEntity(entity);

            response = new BasicResponseHandler().handleResponse(client.execute(host, request));
        } catch (IOException e) {
            throw new GraphiteOperationException(e.getMessage(), e);
        }

        checkSuccessResponse(response);
    }

    @Override
    public String load(String name) throws GraphiteOperationException {
        notEmpty(name, NAME_MUST_BE_INITIALIZED);

        String response;
        try {
            HttpGet request = new HttpGet("/dashboard/load/" + name);

            response = new BasicResponseHandler().handleResponse(client.execute(host, request));
        } catch (IOException e) {
            throw new GraphiteOperationException(e.getMessage(), e);
        }

        checkErrorResponse(response);
        return parseDashboard(response);
    }

    @Override
    public Dashboard load(String name, JsonDeserializer<Dashboard> deserializer) throws GraphiteOperationException {
        Gson gson = new GsonBuilder().registerTypeAdapter(Dashboard.class, deserializer).create();
        try {
            return gson.fromJson(load(name), Dashboard.class);
        } catch (JsonSyntaxException e) {
            throw new GraphiteOperationException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String name) throws GraphiteOperationException {
        notEmpty(name, NAME_MUST_BE_INITIALIZED);

        String response;
        try {
            HttpGet request = new HttpGet("/dashboard/delete/" + name);

            response = new BasicResponseHandler().handleResponse(client.execute(host, request));
        } catch (IOException e) {
            throw new GraphiteOperationException(e.getMessage(), e);
        }

        checkErrorResponse(response);
        checkSuccessResponse(response);
    }

    @Override
    public List<String> find(String query) throws GraphiteOperationException {
        notNull(query, "'query' must be initialized");

        String response;
        try {
            HttpPost request = new HttpPost("/dashboard/find/");
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(Collections.singletonList(
                    new BasicNameValuePair("query", query)));
            request.setEntity(entity);

            response = new BasicResponseHandler().handleResponse(client.execute(host, request));
        } catch (IOException e) {
            throw new GraphiteOperationException(e.getMessage(), e);
        }

        return parseDashboardsList(response);
    }

    /**
     * Create new instance of HttpClient with specified parameters.
     * @param connectTimeout connection timeout in milliseconds.
     * @param readTimeout read timeout in milliseconds.
     * @return new {@code HttpClient} instance.
     */
    private static HttpClient createHttpClient(String username, String password, int connectTimeout, int readTimeout) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        }

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(readTimeout)
                .build();

        return HttpClientBuilder.create().setDefaultRequestConfig(config)
                .setDefaultCredentialsProvider(provider)
                .build();
    }

    /**
     * Check success operation result by Graphite response. If response contains success element with
     * false value or success element not exists method throws {@code GraphiteOperationException} with
     * error message from response. If response contains success element with true value method do nothing.
     * @param response response for check.
     * @throws GraphiteOperationException when response signals that operation failed or response has invalid format.
     */
    private void checkSuccessResponse(String response) throws GraphiteOperationException {
        notNull(response, "'response' must be initialized");

        JsonElement responseJson = new Gson().fromJson(response, JsonElement.class);
        if (!responseJson.isJsonObject()) {
            throw new GraphiteOperationException(RESPONSE_IS_NOT_A_JSON);
        }

        JsonElement internalElement = responseJson.getAsJsonObject().get("success");
        if (internalElement == null) {
            throw new GraphiteOperationException("Unexpected response format: " + response);
        }

        if (!internalElement.getAsBoolean()) {
            throw new GraphiteOperationException("Response returns 'success' false");
        }
    }

    /**
     * Check operation result by Graphite response. If response contains error element method throws
     * {@code GraphiteOperationException} with error message from response. If response not contains error element
     * method do nothing.
     * @param response response for check.
     * @throws GraphiteOperationException when response contains error element.
     */
    private void checkErrorResponse(String response) throws GraphiteOperationException {
        notNull(response, "'response' must be initialized");

        JsonElement responseJson = new Gson().fromJson(response, JsonElement.class);
        if (!responseJson.isJsonObject()) {
            throw new GraphiteOperationException(RESPONSE_IS_NOT_A_JSON);
        }

        JsonElement internalElement = responseJson.getAsJsonObject().get("error");
        if (internalElement != null) {
            throw new GraphiteOperationException("Error response: " + internalElement.getAsString());
        }
    }

    /**
     * Parse json string and extract dashboard description from this.
     * @param json json string for parsing.
     * @return string with dashboard description.
     * @throws GraphiteOperationException when string is not a json object or not contains state element with
     * dashboard description.
     */
    private String parseDashboard(String json) throws GraphiteOperationException {
        notNull(json, "'json' must be initialized");

        JsonElement parsedJson = new Gson().fromJson(json, JsonElement.class);
        if (!parsedJson.isJsonObject()) {
            throw new GraphiteOperationException(RESPONSE_IS_NOT_A_JSON);
        }

        JsonElement stateElement = parsedJson.getAsJsonObject().get("state");
        if (stateElement == null || !stateElement.isJsonObject()) {
            throw new GraphiteOperationException("Response does not contains 'state' element");
        }

        return stateElement.toString();
    }

    /**
     * Parse json string and get from this list of dashboard names.
     * @param json json string for parsing.
     * @return {@code List} with dashboards names. If no dashboards in json string
     * returns empty list.
     * @throws GraphiteOperationException if string is not a json object or not contains dashboard array.
     */
    private List<String> parseDashboardsList(String json) throws GraphiteOperationException {
        notNull(json, "'json' must be initialized");

        JsonElement parsedJson = new Gson().fromJson(json, JsonElement.class);
        if (!parsedJson.isJsonObject()) {
            throw new GraphiteOperationException(RESPONSE_IS_NOT_A_JSON);
        }

        JsonElement dashboardsElement = parsedJson.getAsJsonObject().get("dashboards");
        if (dashboardsElement == null || !dashboardsElement.isJsonArray()) {
            throw new GraphiteOperationException("Response does not contains 'dashboards' array");
        }

        List<String> dashboardList = new ArrayList<>();
        for (JsonElement dashboard : dashboardsElement.getAsJsonArray()) {
            dashboardList.add(dashboard.getAsJsonObject().get("name").getAsString());
        }

        return dashboardList;
    }
}
