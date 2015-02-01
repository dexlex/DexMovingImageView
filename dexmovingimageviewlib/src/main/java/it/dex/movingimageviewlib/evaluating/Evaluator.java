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
 * An evaluator handles generating values basing on some sort of logic (e.g. Time, Scroll, Gyroscope...).
 * <p/>
 * It has got its own lifecycle:
 * <p/>
 * - onCreate()
 * <p/>
 * - onUpdate()
 * <p/>
 * - onPause()
 * <p/>
 * - onDestroy()
 * <p/>
 * The foreground lifetime starts in the onCreate() method and ends in the onDestroy() one
 * During its foreground lifetime an Evaluator generates values. Call the view.invalidate() method to update the view when any changing value event occurs.
 * To generate values it uses the evaluate methods. These methods are called when the view is invalidated, so don't call them.
 * <p/>
 * Evaluator created by Diego Grancini on 13/12/2014.
 */
public abstract class Evaluator {
    private View view;
    private OnEventOccurred onEventOccurred;
    private boolean notifyEvent = false;
    /**
     * Counter used to track the start events
     */
    protected int startLoopCount;
    /**
     * Counter used to track the middle events
     */
    protected int middleLoopCount;
    /**
     * Counter used to track the end events
     */
    protected int endLoopCount;
    /**
     * Counter used to track the first quarter events
     */
    protected int firstQuarterLoopCount;
    /**
     * Counter used to track the second quarter events
     */
    protected int secondQuarterLoopCount;

    /**
     * Default constructor with View to inavlidate when a change occurs
     *
     * @param view the View to be invalidated when a change occurs
     */
    public Evaluator(View view) {
        this.view = view;
    }

    /**
     * Costructor used to pass an OnEventOccurred interface instance
     *
     * @param view            the View to be invalidated when a change occurs
     * @param onEventOccurred The OnEventOccurred interface instance to be called
     */
    public Evaluator(View view, OnEventOccurred onEventOccurred) {
        this(view);
        setOnEventOccurred(onEventOccurred);
    }

    /**
     * Start the evaluator forcing the call to the onCreate() method
     */
    public void start() {
        setNotifyEvent(true);
        onCreate(view);
    }

    /**
     * Called when the Evaluator is starting. Set every needed callback here to generate the values
     *
     * @param view the view the evaluator is created for
     */
    protected abstract void onCreate(View view);

    /**
     * Evaluate the X value
     *
     * @param view the view to change the value for
     * @return the evaluated X value
     */
    public abstract float evaluateX(View view);

    /**
     * Evaluate the Y value
     *
     * @param view the view to change the value for
     * @return the evaluated Y value
     */
    public abstract float evaluateY(View view);

    /**
     * Evaluate the Zoom value.
     * <p/>
     * Default implementation return the default value.
     *
     * @param view        the view to change the value for
     * @param defaultZoom the default Zoom value set for the view
     * @return the evaluated Zoom value
     */
    public float evaluateZoom(View view, float defaultZoom) {
        return defaultZoom;
    }

    /**
     * Evaluate the Angle value.
     * <p/>
     * Default implementation return the default value.
     *
     * @param view         the view to change the value for
     * @param defaultAngle the default Angle value set for the view
     * @return the evaluated Angle value
     */
    public float evaluateAngle(View view, float defaultAngle) {
        return defaultAngle;
    }

    /**
     * Pause the evaluator forcing the call to the onPause() method
     */
    public void pause() {
        onPause(view);
    }

    /**
     * Called when the Evaluator is pausing.
     *
     * @param view the view the evaluator is created for
     */
    protected void onPause(View view) {
    }

    /**
     * Update the evaluator forcing the call to the onUpdate() method
     */
    public void update() {
        onUpdate(view);
    }

    /**
     * Called when the Evaluator is updating.
     *
     * @param view the view the evaluator is created for
     */
    protected void onUpdate(View view) {
    }

    /**
     * Stop the evaluator forcing the call to the onDestroy() method
     */
    public void stop() {
        setNotifyEvent(false);
        onDestroy(view);
    }

    /**
     * Called when the Evaluator is stopping. Free every needed callback here
     *
     * @param view the view the evaluator is created for
     */
    protected abstract void onDestroy(View view);

    /**
     * Get the view the evaluator is created for
     *
     * @return the view the evaluator is created for
     */
    protected View getView() {
        return view;
    }

    /**
     * Set the view the evaluator is created for
     *
     * @param view the view the evaluator is created for
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Utility method used to force stopping and starting again the lifecycle of the evaluator.
     */
    public void restart() {
        stop();
        start();
    }

    /**
     * Get the OnEventOccurred instance set to be called when an event occurs
     *
     * @return the OnEventOccurred instance set to be called when an event occurs
     */
    protected OnEventOccurred getOnEventOccurred() {
        return onEventOccurred;
    }

    /**
     * Set the OnEventOccurred instance set to be called when an event occurs
     *
     * @param onEventOccurred the OnEventOccurred instance set to be called when an event occurs
     */
    public void setOnEventOccurred(OnEventOccurred onEventOccurred) {
        this.onEventOccurred = onEventOccurred;
    }

    /**
     * Check if the call to the OnEventOccurred instance is enabled
     *
     * @return true if the call to the OnEventOccurred instance is enabled, false otherwise
     */
    public boolean isNotifyEvent() {
        return notifyEvent;
    }

    /**
     * Set if the call to the OnEventOccurred instance is enabled or not
     *
     * @param notifyEvent true if the call to the OnEventOccurred instance is enabled, false otherwise
     */
    public void setNotifyEvent(boolean notifyEvent) {
        this.notifyEvent = notifyEvent;
    }

    /**
     * This interface is used as a listener to be called when an event occurs
     */
    public interface OnEventOccurred {
        public void onEventOccurred(View view, Evaluator evaluator, EVENT_STATUS eventStatus, int occurrenceCount);
    }

    /**
     * Enumeration used to listen to the possible generated events
     */
    public enum EVENT_STATUS {
        START, FIRST_QUARTER, MIDDLE, THIRD_QUARTER, END
    }
}
