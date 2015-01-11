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
 * DexMoveImageView created by Diego on 13/12/2014.
 */
public class Parameters {
    public static final float MAX_ZOOM = 4.0f;
    public static final float MIN_ZOOM = 0.0f;
    public static final float MAX_ANGLE = 360f;
    public static final float MIN_ANGLE = 0f;
    public static final float MAX_SPEED = 100f;
    public static final float MIN_SPEED = 10f;

    private int deviceWidth;
    private int deviceHeight;
    private int height;
    private int width;

    private float zoom = 2.0f;
    private float minZoom = MIN_ZOOM;
    private float maxZoom = MAX_ZOOM;

    private float angle = 0;
    private float minAngle = MIN_ANGLE;
    private float maxAngle = MAX_ANGLE;

    private float speed = 30;
    private float minSpeed = MIN_SPEED;
    private float maxSpeed = MAX_SPEED;

    private float x;
    private float y;

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        if (zoom >= getMinZoom() && zoom <= getMaxZoom())
            this.zoom = zoom;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        if ((angle >= getMinAngle() && angle <= getMaxAngle()) || (angle <= -getMinAngle() && angle >= -getMaxAngle()))
            this.angle = angle;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getDeviceWidth() {
        return deviceWidth;
    }

    public void setDeviceWidth(int deviceWidth) {
        this.deviceWidth = deviceWidth;
    }

    public int getDeviceHeight() {
        return deviceHeight;
    }

    public void setDeviceHeight(int deviceHeight) {
        this.deviceHeight = deviceHeight;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        if (speed >= getMinSpeed() && speed <= getMaxSpeed())
            this.speed = speed;
    }

    public float getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(float minZoom) {
        if (minZoom <= MAX_ZOOM && minZoom >= MIN_ZOOM)
            this.minZoom = minZoom;
        else
            throw new IllegalArgumentException("MinZoom value must be <= " + MAX_ZOOM + " and >= " + MIN_ZOOM + ". " + minZoom + " is not correct.");
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(float maxZoom) {
        if (maxZoom <= MAX_ZOOM && maxZoom >= MIN_ZOOM)
            this.maxZoom = maxZoom;
        else
            throw new IllegalArgumentException("MaxZoom value must be <= " + MAX_ZOOM + " and >= " + MIN_ZOOM + ". " + maxZoom + " is not correct.");
    }

    public float getMinAngle() {
        return minAngle;
    }

    public void setMinAngle(float minAngle) {
        if (minAngle <= MAX_ANGLE && minAngle >= MIN_ANGLE)
            this.minAngle = minAngle;
        else
            throw new IllegalArgumentException("MinAngle value must be <= " + MAX_ANGLE + " and >= " + MIN_ANGLE + ". " + minAngle + " is not correct.");
    }

    public float getMaxAngle() {
        return maxAngle;
    }

    public void setMaxAngle(float maxAngle) {
        if (maxAngle <= MAX_ANGLE && maxAngle >= MIN_ANGLE)
            this.maxAngle = maxAngle;
        else
            throw new IllegalArgumentException("MaxAngle value must be <= " + MAX_ANGLE + " and >= " + MIN_ANGLE + ". " + maxAngle + " is not correct.");
    }

    public float getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(float minSpeed) {
        if (minSpeed <= MAX_SPEED && minSpeed >= MIN_SPEED)
            this.minSpeed = minSpeed;
        else
            throw new IllegalArgumentException("MinSpeed value must be <= " + MAX_SPEED + " and >= " + MIN_SPEED + ". " + minSpeed + " is not correct.");
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        if (maxSpeed <= MAX_SPEED && maxSpeed >= MIN_SPEED)
            this.maxSpeed = maxSpeed;
        else
            throw new IllegalArgumentException("MaxSpeed value must be <= " + MAX_SPEED + " and >= " + MIN_SPEED + ". " + maxSpeed + " is not correct.");
    }
}
