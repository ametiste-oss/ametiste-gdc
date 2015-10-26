package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import org.ametiste.gdc.data.graph.Graph;
import org.ametiste.gdc.data.graph.GraphBuilder;
import org.ametiste.gdc.data.graph.GraphiteGraphBuilder;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.ametiste.gdc.data.GraphiteTimeConverter.parseConfigDate;

/**
 * {@code JsonDeserializer<T>} interface implementation that deserialize json string to {@code NoDefaultsDashboard}
 * object. Deserializer return result as{@code Dashboard} object.
 * <p>
 * Class can be register in {@code GsonBuilder} to add type adapter for {@code Dashboard.class}.
 */
public class NoDefaultsDashboardDeserializer implements JsonDeserializer<Dashboard> {

    /**
     * Function cut graph description object from description array.
     */
    private static final Function<JsonElement, JsonObject> CutGraphObject = jsonElement ->  {
        if (jsonElement.isJsonArray()) {
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                if (element.isJsonObject()) {
                    return element.getAsJsonObject();
                }
            }
        }
        return null;
    };

    /**
     * Function convert json object to Graph object.
     */
    private static final Function<JsonObject, Graph> createGraph = json -> {
        if (json != null) {
            GraphBuilder builder = GraphiteGraphBuilder.create();

            if (json.get("title") != null) {
                builder.setTitle(json.get("title").getAsString());
            }

            if (json.get("target") != null) {
                for (JsonElement element : json.get("target").getAsJsonArray()) {
                    builder.addTarget(element.getAsString());
                }
            }
            return builder.build();
        }
        return null;
    };

    @Override
    public Dashboard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String name;
        DashboardGraphSize graphSize;
        DashboardRefreshConfig refreshConfig;
        DashboardTimeConfig timeConfig;
        List<Graph> graphs;

        if (!json.isJsonObject()) {
            throw new JsonParseException("dashboard must be a json object");
        }
        JsonObject dashboard = json.getAsJsonObject();

        name = parseName(dashboard);
        graphSize = parseGraphSize(dashboard);
        refreshConfig = parseRefreshConfig(dashboard);
        timeConfig = parseTimeConfig(dashboard);
        graphs = parseGraphs(dashboard);

        return new NoDefaultsDashboard(name, refreshConfig, graphs, timeConfig, graphSize);
    }

    private void isNull(Object object, String message) throws JsonParseException {
        if (object == null) {
            throw new JsonParseException(message);
        }
    }

    private String parseName(JsonObject dashboard) throws JsonParseException {
        isNull(dashboard.get("name"), "'name' element is missing");

        return dashboard.get("name").getAsString();
    }

    private DashboardGraphSize parseGraphSize(JsonObject dashboard) throws JsonParseException {
        isNull(dashboard.get("graphSize"), "'graphSize' element is missing");

        JsonObject graphSize = dashboard.getAsJsonObject("graphSize");

        isNull(graphSize.get("width"), "'width' element is missing");
        isNull(graphSize.get("height"), "'height' element is missing");

        return new DashboardGraphSize(graphSize.get("width").getAsInt(), graphSize.get("height").getAsInt());
    }

    private DashboardRefreshConfig parseRefreshConfig(JsonObject dashboard) throws JsonParseException {
        isNull(dashboard.get("refreshConfig"), "'refreshConfig' is missing");

        JsonObject config = dashboard.getAsJsonObject("refreshConfig");

        isNull(config.get("interval"), "'interval' element is missing");
        isNull(config.get("enabled"), "'enabled' element is missing");

        return new DashboardRefreshConfig(config.get("enabled").getAsBoolean(), config.get("interval").getAsInt());
    }

    private DashboardTimeConfig parseTimeConfig(JsonObject dashboard) throws JsonParseException {
        isNull(dashboard.get("timeConfig"), "'timeConfig' is missing");

        JsonObject config = dashboard.getAsJsonObject("timeConfig");

        isNull(config.get("type"), "'interval' element is missing");
        isNull(config.get("relativeStartQuantity"), "'relativeStartQuantity' element is missing");
        isNull(config.get("relativeStartUnits"), "'relativeStartUnits' element is missing");
        isNull(config.get("relativeUntilQuantity"), "'relativeUntilQuantity' element is missing");
        isNull(config.get("relativeUntilUnits"), "'relativeUntilUnits' element is missing");
        isNull(config.get("startDate"), "'startDate' element is missing");
        isNull(config.get("endDate"), "'endDate' element is missing");


        boolean relative = config.get("type").getAsString().equals("relative");

        int relativeStartQuantity;
        if (config.get("relativeStartQuantity").getAsString().isEmpty()) {
            relativeStartQuantity = 0;
        } else {
            relativeStartQuantity = config.get("relativeStartQuantity").getAsInt();
        }

        int relativeUntilQuantity;
        if (config.get("relativeUntilQuantity").getAsString().isEmpty()) {
            relativeUntilQuantity = 0;
        } else {
            relativeUntilQuantity = config.get("relativeUntilQuantity").getAsInt();
        }

        RelativeTimeUnit relativeStartUnits = RelativeTimeUnit.parse(config.get("relativeStartUnits").getAsString());
        RelativeTimeUnit relativeUntilUnits = RelativeTimeUnit.parse(config.get("relativeUntilUnits").getAsString());

        Date startDate = parseConfigDate(config.get("startDate").getAsString());
        Date endDate = parseConfigDate(config.get("endDate").getAsString());

        return new DashboardTimeConfig(relative, relativeStartQuantity, relativeStartUnits, relativeUntilQuantity,
                relativeUntilUnits, startDate, endDate);
    }

    private List<Graph> parseGraphs(JsonObject dashboard) {
        isNull(dashboard.get("graphs"), "'graphs' is missing");

        return StreamSupport.stream(dashboard.getAsJsonArray("graphs").spliterator(), false)
                .map(CutGraphObject)
                .map(createGraph)
                .filter(graph -> graph != null)
                .collect(Collectors.toList());
    }
}
