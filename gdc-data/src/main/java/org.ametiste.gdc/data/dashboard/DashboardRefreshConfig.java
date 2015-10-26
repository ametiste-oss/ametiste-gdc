package org.ametiste.gdc.data.dashboard;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * Class represents dashboard refresh parameters.
 * <p>
 * Class implements method {@code toJsonString} that construct json description of refresh parameters that used in
 * dashboard description.
 * <p>
 * {@code DashboardRefreshConfig} is package local class and used by {@code NoDefaultsDashboard}
 * for group refresh parameters.
 */
class DashboardRefreshConfig {
    private int interval;
    private boolean enabled;

    public DashboardRefreshConfig(boolean enabled, int interval) {
        isTrue(interval > 0, "'interval' must be positive number: ", interval);

        this.enabled = enabled;
        this.interval = interval;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns JSON string that contains refreshConfig element description.
     * @return JSON string.
     */
    public String toJsonString() {
        return String.format("{\"interval\":%d,\"enabled\":%s}", interval, enabled);
    }
}
