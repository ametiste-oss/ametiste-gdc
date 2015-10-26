package org.ametiste.gdc.provider;

import org.ametiste.gdc.data.dashboard.Dashboard;
import com.google.gson.JsonDeserializer;

import java.util.List;

/**
 * Interface provides protocol for operate with Graphite dashboards.
 * <p>
 * All implementations will be return raw string data received from Graphite instance.
 * Dashboard parsing operation falls on user.
 * <p>
 * Interface methods throws {@code GraphiteOperationException} if operation was failed in some reason.
 * For example, bad request format, incorrect connection settings, Graphite instance not response, etc.
 */
public interface DashboardProvider {

    /**
     * Save dashboard in Graphite instance or create new if dashboard with target name not exists.
     * @param name dashboard name.
     * @param dashboard dashboard description. Must be valid json structure that describe dashboard structure.
     * @throws GraphiteOperationException when error occurred during communication with Graphite instance or
     * Graphite return error response.
     */
    void save(String name, String dashboard) throws GraphiteOperationException;

    /**
     * Load dashboard with specified name from Graphite instance.
     * @param name name of target dashboard.
     * @return string with json object that describes dashboard structure.
     * @throws GraphiteOperationException when error occurred during communication with Graphite instance
     * or Graphite returns response with error massage (in most cases that dashboard not found).
     * In last case exception contains message from response.
     */
    String load(String name) throws GraphiteOperationException;

    /**
     * Load dashboard with specified name from Graphite instance.
     * @param name name of target dashboard.
     * @param deserializer {@code JsonDeserializer} object that helps deserialize string description to
     *                     {@code Dashboard} object.
     * @return {@code Dashboard} object that represents json description.
     * @throws GraphiteOperationException when error occurred during communication with Graphite instance
     * or Graphite returns response with error massage (in most cases that dashboard not found).
     * In last case exception contains message from response. Also exception may be throws when returned json string
     * has wrong syntax.
     */
    Dashboard load(String name, JsonDeserializer<Dashboard> deserializer) throws GraphiteOperationException;

    /**
     * Delete dashboard from Graphite instance.
     * @param name name of dashboard to delete.
     * @throws GraphiteOperationException when error occurred during communication with Graphite instance or
     * if dashboard with specified name not exists.
     */
    void delete(String name) throws GraphiteOperationException;

    /**
     * Find dashboards in Graphite instance that match query.
     * @param query query string for filtering search result. Query contains phrases separated by whitespace.
     *              Result list will contain only those dashboards that contains all phases from query.
     * @return {@code List} of dashboards names that match query. If no dashboards match method returns
     *              empty list.
     * @throws GraphiteOperationException when error occurred during communication with Graphite instance.
     */
    List<String> find(String query) throws GraphiteOperationException;
}
