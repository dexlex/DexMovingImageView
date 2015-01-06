/*
 * Copyright 2014 Diego Grancini
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

/**
 * DexMoveImageView created by Diego on 13/12/2014.
 */
public class AutoEvaluator extends Evaluator implements Runnable {
    private int evaluatedValue;
    private Timer timer;

    public AutoEvaluator(View view) {
        super(view);
    }

    @Override
    public void onCreate(final View view) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getView().post(AutoEvaluator.this);
            }
        }, 0, 30);
    }

    @Override
    public int evaluateX(View view) {
        return (int) (view.getWidth() * Math.cos(Math.toRadians(evaluatedValue)));
    }

    @Override
    public int evaluateY(View view) {
        return (int) (view.getHeight() * Math.sin(Math.toRadians(evaluatedValue)));
    }

    @Override
    public float evaluateAngle(View view, float defaultAngle) {
        return (float) (360 * Math.sin(Math.toRadians(evaluatedValue)));
    }

    @Override
    public float evaluateZoom(View view, float defaultZoom) {
        return (float) (2 * Math.cos(Math.toRadians(evaluatedValue)));
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
        if (++evaluatedValue == 360)
            evaluatedValue = 0;
        getView().invalidate();
    }
}
