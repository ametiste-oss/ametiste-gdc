package org.ametiste.gdc.data.dashboard;

import org.ametiste.gdc.data.RelativeTimeUnit;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

import static org.ametiste.gdc.data.GraphiteTimeConverter.*;

/**
 * Class represents time period parameters of dashboard description.
 * <p>
 * Class implements method {@code toJsonString} that construct json description of time period parameters that used in
 * dashboard description.
 * <p>
 * {@code DashboardTimeConfig} is package local class and used by {@code NoDefaultsDashboard}
 * for group graph size parameters.
 */
class DashboardTimeConfig {
    private final static Gson MAPPER = new GsonBuilder().registerTypeAdapter(DashboardTimeConfig.class,
            new JsonSerializer<DashboardTimeConfig>() {
                @Override
                public JsonElement serialize(DashboardTimeConfig src, Type typeOfSrc, JsonSerializationContext context) {
                    JsonObject object = new JsonObject();
                    object.addProperty("type", src.type.toString());
                    object.addProperty("relativeStartQuantity", convertQuantity(src.relativeStartQuantity, src.relativeStartUnits, false));
                    object.addProperty("relativeStartUnits", src.relativeStartUnits.toString());
                    object.addProperty("relativeUntilQuantity", convertQuantity(src.relativeUntilQuantity, src.relativeUntilUnits, false));
                    object.addProperty("relativeUntilUnits", src.relativeUntilUnits.toString());
                    object.addProperty("startDate", convertConfigDate(src.startDate));
                    object.addProperty("startTime", convertConfigTime(src.startDate));
                    object.addProperty("endDate", convertConfigDate(src.endDate));
                    object.addProperty("endTime", convertConfigTime(src.endDate));
                    return object;
                }
            }).create();

    private TimeConfigType type;
    private int relativeStartQuantity;
    private RelativeTimeUnit relativeStartUnits;
    private int relativeUntilQuantity;
    private RelativeTimeUnit relativeUntilUnits;
    private Date startDate;
    private Date endDate;

    public enum TimeConfigType {
        RELATIVE("relative"), ABSOLUTE("absolute");

        private final String description;

        TimeConfigType(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public DashboardTimeConfig(boolean relativeTime,
                               int relativeStartQuantity,
                               RelativeTimeUnit relativeStartUnits,
                               int relativeUntilQuantity,
                               RelativeTimeUnit relativeUntilUnits,
                               Date startDate,
                               Date endDate) {
        if (relativeTime) {
            this.type = TimeConfigType.RELATIVE;
        } else {
            this.type = TimeConfigType.ABSOLUTE;
        }
        this.relativeStartQuantity = relativeStartQuantity;
        this.relativeStartUnits = relativeStartUnits;
        this.relativeUntilQuantity = relativeUntilQuantity;
        this.relativeUntilUnits = relativeUntilUnits;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TimeConfigType getType() {
        return type;
    }

    public void setType(boolean relative) {
        if (relative) {
            this.type = TimeConfigType.RELATIVE;
        } else {
            this.type = TimeConfigType.ABSOLUTE;
        }
    }

    public int getRelativeStartQuantity() {
        return relativeStartQuantity;
    }

    public void setRelativeStartQuantity(int relativeStartQuantity) {
        this.relativeStartQuantity = relativeStartQuantity;
    }

    public RelativeTimeUnit getRelativeStartUnits() {
        return relativeStartUnits;
    }

    public void setRelativeStartUnits(RelativeTimeUnit relativeStartUnits) {
        this.relativeStartUnits = relativeStartUnits;
    }

    public int getRelativeUntilQuantity() {
        return relativeUntilQuantity;
    }

    public void setRelativeUntilQuantity(int relativeUntilQuantity) {
        this.relativeUntilQuantity = relativeUntilQuantity;
    }

    public RelativeTimeUnit getRelativeUntilUnits() {
        return relativeUntilUnits;
    }

    public void setRelativeUntilUnits(RelativeTimeUnit relativeUntilUnits) {
        this.relativeUntilUnits = relativeUntilUnits;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate.setTime(startDate.getTime());
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate.setTime(endDate.getTime());
    }

    /**
     * Returns JSON string that contains timeConfig element description.
     * @return JSON string.
     */
    public String toJsonString() {
        return MAPPER.toJson(this, getClass());
    }
}
