package org.ametiste.gdc.data.dashboard;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * Class represents graph size parameters of dashboard description.
 * <p>
 * Class implements method {@code toJsonString} that construct json description of size parameters that used in
 * dashboard description.
 * <p>
 * {@code DashboardGraphSize} is package local class and used by {@code NoDefaultsDashboard}
 * for group graph size parameters.
 */
class DashboardGraphSize {
    private int width;
    private int height;

    public DashboardGraphSize(int width, int height) {
        isTrue(width > 0, "'width' must be positive number: ", width);
        isTrue(height > 0, "'height' must be positive number: ", height);

        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        isTrue(width > 0, "'width' must be positive number: ", width);
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        isTrue(height > 0, "'height' must be positive number: ", height);
        this.height = height;
    }

    /**
     * Returns JSON string that contains graphSize element description.
     * @return JSON string.
     */
    public String toJsonString() {
        return String.format("{\"width\":%d, \"height\":%d}", width, height);
    }
}
