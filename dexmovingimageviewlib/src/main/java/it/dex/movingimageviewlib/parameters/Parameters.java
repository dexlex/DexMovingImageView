/*
 * Copyright 2014-2015 Diego Grancini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.dex.movingimageviewlib.parameters;

/**
 * Useful bean used to centralize data and values.
 * <p/>
 * Parameters created by Diego Grancini on 13/12/2014.
 */
public class Parameters {
    /**
     * The absolute maximum value the zoom can assume
     */
    public static final float MAX_ZOOM = 4.0f;
    /**
     * The absolute minimum value the zoom can assume
     */
    public static final float MIN_ZOOM = 0.0f;
    /**
     * The absolute maximum value the angle can assume
     */
    public static final float MAX_ANGLE = 360f;
    /**
     * The absolute minimum value the angle can assume
     */
    public static final float MIN_ANGLE = 0f;
    /**
     * The absolute maximum value the frequency can assume
     */
    public static final float MAX_FREQUENCY = 100f;
    /**
     * The absolute minimum value the frequency can assume
     */
    public static final float MIN_FREQUENCY = 10f;

    private float x;
    private float y;

    private float zoom = 2.0f;
    private float minZoom = MIN_ZOOM;
    private float maxZoom = MAX_ZOOM;

    private float angle = 0;
    private float minAngle = MIN_ANGLE;
    private float maxAngle = MAX_ANGLE;

    private float frequency = 30;
    private float minFrequency = MIN_FREQUENCY;
    private float maxFrequency = MAX_FREQUENCY;

    private int deviceWidth;
    private int deviceHeight;
    private int height;
    private int width;

    /**
     * Get the current x value
     *
     * @return the current x
     */
    public float getX() {
        return x;
    }

    /**
     * Set a new x value
     *
     * @param x the new value
     */
    public void setX(float x) {
        this.x = x;
    }


    /**
     * Get the current y value
     *
     * @return the current y
     */
    public float getY() {
        return y;
    }

    /**
     * Set a new y value
     *
     * @param y the new value
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Get the current zoom value
     *
     * @return the current zoom
     */
    public float getZoom() {
        return zoom;
    }

    /**
     * Set a new zoom value
     *
     * @param zoom the new value
     */
    public void setZoom(float zoom) {
        if (zoom >= getMinZoom() && zoom <= getMaxZoom())
            this.zoom = zoom;
    }

    /**
     * Get the current angle value
     *
     * @return the current angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Set a new angle value
     *
     * @param angle the new value
     */
    public void setAngle(float angle) {
        if ((angle >= getMinAngle() && angle <= getMaxAngle()) || (angle <= -getMinAngle() && angle >= -getMaxAngle()))
            this.angle = angle;
    }


    /**
     * Get the current frequency value
     *
     * @return the current frequency
     */
    public float getFrequency() {
        return frequency;
    }

    /**
     * Set a new frequency value
     *
     * @param frequency the new value
     */
    public void setFrequency(float frequency) {
        if (frequency >= getMinFrequency() && frequency <= getMaxFrequency())
            this.frequency = frequency;
    }

    /**
     * Get the current minimum zoom value
     *
     * @return the current minimum zoom
     */
    public float getMinZoom() {
        return minZoom;
    }

    /**
     * Set a new minimum zoom value
     *
     * @param minZoom the new value
     */
    public void setMinZoom(float minZoom) {
        if (minZoom <= MAX_ZOOM && minZoom >= MIN_ZOOM)
            this.minZoom = minZoom;
        else
            throw new IllegalArgumentException("MinZoom value must be <= " + MAX_ZOOM + " and >= " + MIN_ZOOM + ". " + minZoom + " is not correct.");
    }

    /**
     * Get the current maximum zoom value
     *
     * @return the current maximum zoom
     */
    public float getMaxZoom() {
        return maxZoom;
    }

    /**
     * Set a new maximum zoom value
     *
     * @param maxZoom the new value
     */
    public void setMaxZoom(float maxZoom) {
        if (maxZoom <= MAX_ZOOM && maxZoom >= MIN_ZOOM)
            this.maxZoom = maxZoom;
        else
            throw new IllegalArgumentException("MaxZoom value must be <= " + MAX_ZOOM + " and >= " + MIN_ZOOM + ". " + maxZoom + " is not correct.");
    }

    /**
     * Get the current minimum angle value
     *
     * @return the current minimum angle
     */
    public float getMinAngle() {
        return minAngle;
    }

    /**
     * Set a new minimum angle value
     *
     * @param minAngle the new value
     */
    public void setMinAngle(float minAngle) {
        if (minAngle <= MAX_ANGLE && minAngle >= MIN_ANGLE)
            this.minAngle = minAngle;
        else
            throw new IllegalArgumentException("MinAngle value must be <= " + MAX_ANGLE + " and >= " + MIN_ANGLE + ". " + minAngle + " is not correct.");
    }

    /**
     * Get the current maximum angle value
     *
     * @return the current maximum angle
     */
    public float getMaxAngle() {
        return maxAngle;
    }

    /**
     * Set a new maximum angle value
     *
     * @param maxAngle the new value
     */
    public void setMaxAngle(float maxAngle) {
        if (maxAngle <= MAX_ANGLE && maxAngle >= MIN_ANGLE)
            this.maxAngle = maxAngle;
        else
            throw new IllegalArgumentException("MaxAngle value must be <= " + MAX_ANGLE + " and >= " + MIN_ANGLE + ". " + maxAngle + " is not correct.");
    }

    /**
     * Get the current minimum frequency value
     *
     * @return the current minimum frequency
     */
    public float getMinFrequency() {
        return minFrequency;
    }

    /**
     * Set a new minimum frequency value
     *
     * @param minFrequency the new value
     */
    public void setMinFrequency(float minFrequency) {
        if (minFrequency <= MAX_FREQUENCY && minFrequency >= MIN_FREQUENCY)
            this.minFrequency = minFrequency;
        else
            throw new IllegalArgumentException("MinFrequency value must be <= " + MAX_FREQUENCY + " and >= " + MIN_FREQUENCY + ". " + minFrequency + " is not correct.");
    }

    /**
     * Get the current maximum frequency value
     *
     * @return the current maximum frequency
     */
    public float getMaxFrequency() {
        return maxFrequency;
    }

    /**
     * Set a new maximum frequency value
     *
     * @param maxFrequency the new value
     */
    public void setMaxFrequency(float maxFrequency) {
        if (maxFrequency <= MAX_FREQUENCY && maxFrequency >= MIN_FREQUENCY)
            this.maxFrequency = maxFrequency;
        else
            throw new IllegalArgumentException("MaxFrequency value must be <= " + MAX_FREQUENCY + " and >= " + MIN_FREQUENCY + ". " + maxFrequency + " is not correct.");
    }

    /**
     * Get the view height
     *
     * @return the view height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the view height
     *
     * @param height the view height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Get the view width
     *
     * @return the view width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the view width
     *
     * @param width the view width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get the device width
     *
     * @return the device width
     */
    public int getDeviceWidth() {
        return deviceWidth;
    }

    /**
     * Set the device width
     *
     * @param deviceWidth the device width
     */
    public void setDeviceWidth(int deviceWidth) {
        this.deviceWidth = deviceWidth;
    }

    /**
     * Get the device height
     *
     * @return the device height
     */
    public int getDeviceHeight() {
        return deviceHeight;
    }

    /**
     * Set the device height
     *
     * @param deviceHeight the device height
     */
    public void setDeviceHeight(int deviceHeight) {
        this.deviceHeight = deviceHeight;
    }
}
