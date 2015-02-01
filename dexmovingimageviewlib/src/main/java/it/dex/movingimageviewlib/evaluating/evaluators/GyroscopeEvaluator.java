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

package it.dex.movingimageviewlib.evaluating.evaluators;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import it.dex.movingimageviewlib.evaluating.Evaluator;

/**
 * Evaluator class that use an OnSensorChanged implementation to generate values.
 * <p/>
 * GyroscopeEvaluator created by Diego Grancini on 03/01/2015.
 */
public class GyroscopeEvaluator extends Evaluator implements SensorEventListener {
    private static final float MIN_TIME_STEP = (1f / 40f);
    private SensorManager mSensorManager;
    private Sensor mGyroSensor;
    private long mLastTime = System.currentTimeMillis();
    private float x, y, z;

    public GyroscopeEvaluator(View view) {
        super(view);
    }

    public GyroscopeEvaluator(View view, OnEventOccurred onEventOccurred) {
        super(view, onEventOccurred);
    }

    @Override
    protected void onCreate(View view) {
        mSensorManager = (SensorManager) view.getContext().getSystemService(Context.SENSOR_SERVICE);
        mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorManager.registerListener(this, mGyroSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public float evaluateX(View view) {
        return (int) (x * view.getWidth());
    }

    @Override
    public float evaluateY(View view) {
        return (int) (y * view.getHeight());
    }

    @Override
    public float evaluateAngle(View view, float defaultAngle) {
        return (float) (z * 180d / Math.PI);
    }

    @Override
    protected void onDestroy(View view) {
        mSensorManager.unregisterListener(this, mGyroSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        float x = values[1];
        float y = values[0];
        float z = values[2];

        float angularVelocity = z * 0.96f;
        long now = System.currentTimeMillis();
        float timeDiff = (now - mLastTime) / 1000f;
        mLastTime = now;
        if (timeDiff > 1) {
            timeDiff = MIN_TIME_STEP;
        }

        this.x += x * timeDiff;
        if (this.x > 1f)
            this.x = 1f;
        else if (this.x < -1f)
            this.x = -1f;

        this.y += y * timeDiff;
        if (this.y > 1f)
            this.y = 1f;
        else if (this.y < -1f)
            this.y = -1f;

        this.z += angularVelocity * timeDiff;
        if (this.x == 0 && this.y == 0 && this.z == 0) {
            if (getOnEventOccurred() != null && isNotifyEvent())
                getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.MIDDLE, ++middleLoopCount);
        }
        getView().invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
