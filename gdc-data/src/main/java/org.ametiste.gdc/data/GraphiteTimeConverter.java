package org.ametiste.gdc.data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.apache.commons.lang3.Validate.*;

/**
 * Class provides functionality for convert time units to string and back for using in dashboard and graph description.
 * {@code GraphiteTimeConverter} contains set of static methods that execute different types of conversion.
 * <p>
 * Note: All dates formatted for time zone GMT.
 */
public final class GraphiteTimeConverter {

    private static final String TIME_ZONE = "GMT";
    private static final String DATE_MUST_BE_INITIALIZED = "'date' must be initialized";

    private static final DateTimeFormatter GRAPH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter GRAPH_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm_yyyyMMdd");
    private static final DateTimeFormatter CONFIG_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm a");
    private static final DateTimeFormatter CONFIG_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private GraphiteTimeConverter() {
    }

    /**
     * Convert relative quantity and units to string for using in graphite time parameters.
     * @param quantity value of relative time. Must be non negative. In other case {@code IllegalArgumentException}
     *                 will be throw.
     * @param units units of relative time. Value may be {@code RelativeTimeUnit.NOW}. In this case quantity parameter
     *              ignores and method returns string that representing now value.
     * @param withSign flag that specify include '-' sign in result or not.
     * @return string that represents relative time for method arguments.
     */
    public static String convert(int quantity, RelativeTimeUnit units, boolean withSign) {
        isTrue(quantity >= 0, "'quantity' must not be negative");
        notNull(units, "'units' must be initialized");

        if (units == RelativeTimeUnit.NOW) {
            return "now";
        } else if (withSign) {
            return "-" + quantity + units;
        } else {
            return String.format("%d%s", quantity, units);
        }
    }

    /**
     * Convert relative quantity and units to string for using in graphite time parameters and
     * return quantity value only.
     * @param quantity value of relative time. Must be non negative. In other case {@code IllegalArgumentException}
     *                 will be throw.
     * @param units units of relative time. Value may be {@code RelativeTimeUnit.NOW}. In this case quantity parameter
     *              ignores and method returns string that representing now value.
     * @param withSign flag that specify include '-' sign in result or not.
     * @return string that represents time quantity for method arguments.
     */
    public static String convertQuantity(int quantity, RelativeTimeUnit units, boolean withSign) {
        isTrue(quantity >= 0, "'quantity' must not be negative");
        notNull(units, "'units' must be initialized");

        if (units == RelativeTimeUnit.NOW) {
            return "";
        } else if (quantity == 0) {
            return "0";
        } else if (withSign) {
            return "-" + quantity;
        } else {
            return String.valueOf(quantity);
        }
    }

    /**
     * Convert {@code Date} object to full date/time formatted string for using in graphs description.
     * @param date date to convert.
     * @return formatter string that contains time and date from {@code Date} argument.
     */
    public static String convertGraph(Date date) {
        notNull(date, DATE_MUST_BE_INITIALIZED);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.of(TIME_ZONE))
                .format(GRAPH_DATE_TIME_FORMATTER);
    }

    /**
     * Convert {@code Date} object to date formatted string for using in graphs description.
     * @param date date to convert.
     * @return formatter string that contains time and date from {@code Date} argument.
     */
    public static String convertGraphDate(Date date) {
        notNull(date, DATE_MUST_BE_INITIALIZED);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.of(TIME_ZONE))
                .format(GRAPH_DATE_FORMATTER);
    }

    /**
     * Convert {@code Date} object to date formatted string for using in time configs of dashboard.
     * @param date date to convert.
     * @return formatter string that contains time and date from {@code Date} argument.
     */
    public static String convertConfigDate(Date date) {
        notNull(date, DATE_MUST_BE_INITIALIZED);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.of(TIME_ZONE))
                .format(CONFIG_DATE_FORMATTER);
    }

    /**
     * Parse string to Date object with specified format.
     * @param date string for parsing.
     * @return {@code Date} object that represent date time in string.
     */
    public static Date parseConfigDate(String date) {
        notEmpty(date, "'date' must be not empty string");
        return Date.from(LocalDateTime.parse(date, CONFIG_DATE_FORMATTER).atZone(ZoneId.of(TIME_ZONE)).toInstant());
    }

    /**
     * Convert {@code Date} object to time formatted string for using in time configs of dashboard.
     * @param date date to convert.
     * @return formatter string that contains time and date from {@code Date} argument.
     */
    public static String convertConfigTime(Date date) {
        notNull(date, DATE_MUST_BE_INITIALIZED);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.of(TIME_ZONE))
                .format(CONFIG_TIME_FORMATTER);
    }
}
