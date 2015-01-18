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

import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import it.dex.movingimageviewlib.evaluating.Evaluator;
import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * DexMoveImageView created by Diego on 13/12/2014.
 */
public class TimeEvaluator extends Evaluator implements Runnable {
    protected int evaluatedValue;
    private float speed = 30;
    private float minZoom = 1.5f;
    private float maxZoom = 1.7f;

    private Timer timer;

    public TimeEvaluator(View view) {
        super(view);
    }

    public TimeEvaluator(View view, OnEventOccurred onEventOccurred) {
        super(view, onEventOccurred);
    }

    @Override
    public void onCreate(final View view) {
        startTimer(speed);
    }

    private void startTimer(float speed) {
        if (getSpeed() != 0) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    getView().post(TimeEvaluator.this);
                }
            }, 0, (long) (getSpeed()));
        }
    }

    @Override
    public float evaluateX(View view) {
        return evaluatedValue;
    }

    @Override
    public float evaluateY(View view) {
        return evaluatedValue;
    }

    @Override
    public float evaluateZoom(View view, float defaultZoom) {
        return evaluatedValue;
    }

    @Override
    public float evaluateAngle(View view, float defaultAngle) {
        return evaluatedValue;
    }

    @Override
    public void onDestroy(View view) {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = null;
    }

    @Override
    public void run() {
        if (++evaluatedValue == Parameters.MAX_ANGLE) {
            loopCount++;
            if (getOnEventOccurred() != null && isNotifyEvent())
                getOnEventOccurred().onEventOccurred(getView(), this, loopCount);
            evaluatedValue = (int) Parameters.MIN_ANGLE;
        }
        getView().invalidate();
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        restart();
    }

    public float getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(float minZoom) {
        this.minZoom = minZoom;
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }
}
