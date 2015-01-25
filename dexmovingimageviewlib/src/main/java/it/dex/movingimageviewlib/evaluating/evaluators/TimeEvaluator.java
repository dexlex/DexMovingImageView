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
    protected float evaluatedValue;
    private float frequency = 30;
    private final float step = 0.1f;

    private Timer timer;

    public TimeEvaluator(View view) {
        super(view);
    }

    public TimeEvaluator(View view, OnEventOccurred onEventOccurred) {
        super(view, onEventOccurred);
    }

    @Override
    public void onCreate(final View view) {
        startTimer(frequency);
    }

    private void startTimer(float frequency) {
        if (getFrequency() != 0) {
            stop();
            setNotifyEvent(true);
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    getView().post(TimeEvaluator.this);
                }
            }, 0, (long) (frequency));
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
        evaluatedValue = evaluatedValue + step;
        if (getOnEventOccurred() != null && isNotifyEvent()) {
            if (evaluatedValue >= Parameters.MAX_ANGLE - step) {
                getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.END, ++endLoopCount);
            } else if (evaluatedValue <= Parameters.MIN_ANGLE + step) {
                getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.START, ++startLoopCount);
            } else if (evaluatedValue + (step / 2) >= (Parameters.MAX_ANGLE + Parameters.MIN_ANGLE) / 2 && evaluatedValue - (step / 2) <= (Parameters.MAX_ANGLE + Parameters.MIN_ANGLE) / 2) {
                getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.MIDDLE, ++middleLoopCount);
            } else if (evaluatedValue + (step / 2) >= (Parameters.MAX_ANGLE + Parameters.MIN_ANGLE) / 4 && evaluatedValue - (step / 2) <= (Parameters.MAX_ANGLE + Parameters.MIN_ANGLE) / 4) {
                getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.FIRST_QUARTER, ++firstQuarterLoopCount);
            } else if (evaluatedValue + (step / 2) >= (Parameters.MAX_ANGLE + Parameters.MIN_ANGLE) * 3 / 4 && evaluatedValue - (step / 2) <= (Parameters.MAX_ANGLE + Parameters.MIN_ANGLE) * 3 / 4) {
                getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.THIRD_QUARTER, ++secondQuarterLoopCount);
            }
        }
        if (evaluatedValue >= Parameters.MAX_ANGLE - step) {
            evaluatedValue = (int) Parameters.MIN_ANGLE;
        }
        getView().invalidate();
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
        restart();
    }
}
