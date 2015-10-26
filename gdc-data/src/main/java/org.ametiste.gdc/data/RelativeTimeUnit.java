package org.ametiste.gdc.data;

/**
 * Enum of available time units for relative time points.
 */
public enum RelativeTimeUnit {
    NOW("now"),
    SECONDS("s"),
    MINUTES("min"),
    HOURS("h"),
    DAYS("d"),
    WEEKS("w"),
    MONTHS("mon"),
    YEARS("y");

    private final String abbreviation;

    RelativeTimeUnit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return abbreviation;
    }

    /**
     * Parse string to one of {@code RelativeTimeUnit} value. Operation is not case sensitive.
     * @param units string for parsing.
     * @return {@code RelativeTimeUnit} value.
     * @throws IllegalArgumentException when string for parsing is null or string not match to any
     *         {@code RelativeTimeUnit} abbreviation.
     */
    public static RelativeTimeUnit parse(String units) throws IllegalArgumentException {
        if (units == null) {
            throw new IllegalArgumentException("'units' must be initialized");
        }

        switch (units.toLowerCase()) {
            case "now":
                return NOW;
            case "s":
                return SECONDS;
            case "min":
                return MINUTES;
            case "h":
                return HOURS;
            case "d":
                return DAYS;
            case "w":
                return WEEKS;
            case "mon":
                return MONTHS;
            case "y":
                return YEARS;
            default:
                throw new IllegalArgumentException("No enum constant with abbreviation " + units);
        }
    }
}