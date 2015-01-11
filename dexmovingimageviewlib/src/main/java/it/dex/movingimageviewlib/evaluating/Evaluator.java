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

package it.dex.movingimageviewlib.evaluating;

import android.view.View;

/**
 * DexMoveImageView created by Diego on 13/12/2014.
 */
public abstract class Evaluator {
    private View view;
    private OnEventOccurred onEventOccurred;
    private boolean notifyEvent = false;
    protected int loopCount;

    public Evaluator(View view) {
        this.view = view;
    }

    public Evaluator(View view, OnEventOccurred onEventOccurred) {
        this(view);
        setOnEventOccurred(onEventOccurred);
    }

    public void start() {
        setNotifyEvent(true);
        onCreate(view);
    }

    protected abstract void onCreate(View view);

    public abstract float evaluateX(View view);

    public abstract float evaluateY(View view);

    public float evaluateZoom(View view, float defaultZoom) {
        return defaultZoom;
    }

    public float evaluateAngle(View view, float defaultAngle) {
        return defaultAngle;
    }

    public void pause() {
        onPause(view);
    }

    protected void onPause(View view) {
    }

    public void update() {
        onUpdate(view);
    }

    protected void onUpdate(View view) {
    }

    public void stop() {
        setNotifyEvent(false);
        onDestroy(view);
    }

    protected abstract void onDestroy(View view);

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void restart() {
        stop();
        start();
    }

    public OnEventOccurred getOnEventOccurred() {
        return onEventOccurred;
    }

    public void setOnEventOccurred(OnEventOccurred onEventOccurred) {
        this.onEventOccurred = onEventOccurred;
    }

    public boolean isNotifyEvent() {
        return notifyEvent;
    }

    public void setNotifyEvent(boolean notifyEvent) {
        this.notifyEvent = notifyEvent;
    }

    public interface OnEventOccurred {
        public void onEventOccurred(View view, Evaluator evaluator, int occurrenceCount);
    }
}
