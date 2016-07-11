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

package it.dex.movingimageviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import it.dex.movingimageviewlib.drawing.DRAWERS;
import it.dex.movingimageviewlib.drawing.Drawer;
import it.dex.movingimageviewlib.drawing.DrawerChooser;
import it.dex.movingimageviewlib.evaluating.EVALUATORS;
import it.dex.movingimageviewlib.evaluating.Evaluator;
import it.dex.movingimageviewlib.evaluating.EvaluatorChooser;
import it.dex.movingimageviewlib.evaluating.evaluators.TimeEvaluator;
import it.dex.movingimageviewlib.generating.VALUESGENERATORS;
import it.dex.movingimageviewlib.generating.ValuesGenerator;
import it.dex.movingimageviewlib.generating.ValuesGeneratorChooser;
import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * This view provides a structured but simple way to create and add modifications to the image.
 * <p/>
 * DexMovingImageView created by Diego Grancini on 06/12/2014.
 */
public class DexMovingImageView extends DexCrossFadeImageView implements Evaluator.OnEventOccurred {
    private Parameters parameters = new Parameters();
    private Map<String, Drawer> drawers = new HashMap<>();
    private ValuesGenerator valuesGenerator;
    private Evaluator evaluator;
    private OnValueChanged onValueChanged;
    private Evaluator.OnEventOccurred onEventOccurred;

    public DexMovingImageView(Context context) {
        this(context, null);
    }

    public DexMovingImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DexMovingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public DexMovingImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        getParameters().setDeviceHeight(getContext().getResources().getDisplayMetrics().heightPixels);
        getParameters().setDeviceWidth(getContext().getResources().getDisplayMetrics().widthPixels);
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.DexMovingImageView, 0, 0);
            int N = a.getIndexCount();
            for (int i = 0; i < N; ++i) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.DexMovingImageView_dex_zoom) {
                    setZoom(a.getFloat(attr, getZoom()));
                } else if (attr == R.styleable.DexMovingImageView_dex_minZoom) {
                    setMinZoom(a.getFloat(attr, getMinZoom()));
                } else if (attr == R.styleable.DexMovingImageView_dex_maxZoom) {
                    setMaxZoom(a.getFloat(attr, getMaxZoom()));
                } else if (attr == R.styleable.DexMovingImageView_dex_angle) {
                    setAngle(a.getFloat(attr, getAngle()));
                } else if (attr == R.styleable.DexMovingImageView_dex_minAngle) {
                    setMinAngle(a.getFloat(attr, getMinAngle()));
                } else if (attr == R.styleable.DexMovingImageView_dex_maxAngle) {
                    setMaxAngle(a.getFloat(attr, getMaxAngle()));
                } else if (attr == R.styleable.DexMovingImageView_dex_speed) {
                    setFrequency(1 / a.getFloat(attr, getFrequency()) * 1000);
                } else if (attr == R.styleable.DexMovingImageView_dex_drawer) {
                    if ((a.getInt(attr, 1) & 0) == 0) {
                        addDrawerType(DRAWERS.SCALE.getDefaultName(), DRAWERS.SCALE);
                    }
                    if ((a.getInt(attr, 0) & 1) == 1) {
                        addDrawerType(DRAWERS.TRANSLATE.getDefaultName(), DRAWERS.TRANSLATE);
                    }
                    if ((a.getInt(attr, 0) & 2) == 2) {
                        addDrawerType(DRAWERS.ROTATE.getDefaultName(), DRAWERS.ROTATE);
                    }
                } else if (attr == R.styleable.DexMovingImageView_dex_generator) {
                    try {
                        setValuesGeneratorType(VALUESGENERATORS.map(a.getInteger(attr, VALUESGENERATORS.BASE.getType())));
                    } catch (IllegalArgumentException | UnsupportedOperationException e) {
                        try {
                            Class cls = Class.forName(a.getString(attr));
                            Object obj = cls.newInstance();
                            setValuesGenerator((ValuesGenerator) obj);
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | ClassCastException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else if (attr == R.styleable.DexMovingImageView_dex_evaluator) {
                    try {
                        setEvaluatorType(EVALUATORS.map(a.getInteger(attr, EVALUATORS.SIMPLE.getType())));
                    } catch (IllegalArgumentException | UnsupportedOperationException e) {
                        try {
                            Class cls = Class.forName(a.getString(attr));
                            Object obj = cls.newInstance();
                            setEvaluator((Evaluator) obj);
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | ClassCastException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            a.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getEvaluator() != null)
            getEvaluator().start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (getEvaluator() != null)
            getEvaluator().stop();
    }

    @Override
    public void invalidate() {
        if (getEvaluator() != null) {
            parameters.setX(getValuesGenerator().getX(getEvaluator().evaluateX(this)));
            parameters.setY(getValuesGenerator().getY(getEvaluator().evaluateY(this)));
            parameters.setZoom(getValuesGenerator().getZoom(getEvaluator().evaluateZoom(this, getParameters().getZoom()), getParameters().getZoom()));
            parameters.setAngle(getValuesGenerator().getAngle(getEvaluator().evaluateAngle(this, getParameters().getAngle()), getParameters().getAngle()));
        }
        super.invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getParameters().setWidth(getWidth());
        getParameters().setHeight(getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Set<String> set = drawers.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String drawer = iterator.next();
            drawers.get(drawer).onDraw(canvas, parameters);
        }
        if (onValueChanged != null)
            onValueChanged.onValueChanged(this, parameters.getX(), parameters.getY(), parameters.getZoom(), parameters.getAngle());
        super.onDraw(canvas);
    }

    /**
     * Get all the values set
     *
     * @return the bean containing the generated values
     */
    public Parameters getParameters() {
        return parameters;
    }

    /**
     * Add a drawer with a name
     *
     * @param drawerName the name used to add the drawer
     * @param moverType  the drawer enum value that corresponds to the drawer
     * @throws IllegalArgumentException
     */
    public void addDrawerType(String drawerName, DRAWERS moverType) throws IllegalArgumentException {
        addDrawer(drawerName, DrawerChooser.get(moverType));
    }

    /**
     * Remove the drawer previously added with the specified name
     *
     * @param drawerName the name of the drawer to remove
     * @throws IllegalArgumentException
     */
    public void removeDrawerType(String drawerName) throws IllegalArgumentException {
        removeDrawer(drawerName);
    }

    /**
     * Get the used values generator
     *
     * @return the used ValuesGenerator
     */
    public ValuesGenerator getValuesGenerator() {
        return valuesGenerator;
    }

    /**
     * Set a ValuesGenerator
     *
     * @param valuesGenerator the ValuesGenerator to use
     */
    public void setValuesGenerator(ValuesGenerator valuesGenerator) {
        this.valuesGenerator = valuesGenerator;
    }

    /**
     * Set a ValuesGenerator using the enumeration value
     *
     * @param valuesGeneratorType the enumeration value to use
     */
    public void setValuesGeneratorType(VALUESGENERATORS valuesGeneratorType) {
        setValuesGenerator(ValuesGeneratorChooser.get(valuesGeneratorType, parameters));
    }

    /**
     * Get the evaluator used
     *
     * @return the used evaluator
     */
    public Evaluator getEvaluator() {
        return evaluator;
    }

    /**
     * Set an evaluator
     *
     * @param evaluator the evaluator to be set
     */
    public void setEvaluator(Evaluator evaluator) {
        if (this.evaluator != null)
            this.evaluator.stop();
        if (evaluator instanceof TimeEvaluator)
            ((TimeEvaluator) evaluator).setFrequency(getParameters().getFrequency());
        this.evaluator = evaluator;
        evaluator.start();
    }

    /**
     * Set an evaluator usign the enumeration value
     *
     * @param coordinatorType the enumeration value to use
     */
    public void setEvaluatorType(EVALUATORS coordinatorType) {
        setEvaluator(EvaluatorChooser.get(coordinatorType, this, this));
    }

    /**
     * Add a drawer with the specified name
     *
     * @param drawerName the name of the drawer to be added
     * @param drawer     the drawer to be added
     */
    public void addDrawer(String drawerName, Drawer drawer) {
        drawers.put(drawerName, drawer);
    }

    /**
     * Check if a drawer is added
     *
     * @param drawerType the enumeration value to be checked
     * @return true if the drawer has been added, false otherwise
     */
    public boolean isDrawerAdded(DRAWERS drawerType) {
        Set<String> set = drawers.keySet();
        return set.contains(drawerType.getDefaultName());
    }

    /**
     * Remove the drawer with the specified name
     *
     * @param drawerName the name of the drawer to remove
     */
    public void removeDrawer(String drawerName) {
        drawers.remove(drawerName);
    }

    /**
     * Set an instance of OnValueChanged
     *
     * @param onValueChanged the OnValueChange instance to be set
     */
    public void setOnValueChanged(OnValueChanged onValueChanged) {
        this.onValueChanged = onValueChanged;
    }

    @Override
    public void onEventOccurred(View view, Evaluator evaluator, Evaluator.EVENT_STATUS eventStatus, int occurrenceCount) {
        if (getOnEventOccurred() != null)
            getOnEventOccurred().onEventOccurred(view, evaluator, eventStatus, occurrenceCount);
    }

    /**
     * Get the used instance of OnEventOccurred interface
     *
     * @return ht eused instance of OnEventOccurred interface
     */
    public Evaluator.OnEventOccurred getOnEventOccurred() {
        return onEventOccurred;
    }

    /**
     * Set an instance of OnEventOccurred interface
     *
     * @param onEventOccurred the instance of OnEventOccurred to be set
     */
    public void setOnEventOccurred(Evaluator.OnEventOccurred onEventOccurred) {
        this.onEventOccurred = onEventOccurred;
    }

    /**
     * This interface is used as a listener to be called when a value is changed
     */
    public interface OnValueChanged {
        /**
         * Method called when a value is changed
         *
         * @param view  the DexMovingImageView the value is changed for
         * @param x     the x value
         * @param y     the y value
         * @param z     the zoom value
         * @param angle the angle value
         */
        public void onValueChanged(View view, float x, float y, float z, float angle);
    }

    /**
     * Get the current value for Zoom
     *
     * @return the zoom value
     */
    public float getZoom() {
        return getParameters().getZoom();
    }

    /**
     * Set a new value for Zoom
     *
     * @param zoom the new zoom value
     */
    public void setZoom(float zoom) {
        getParameters().setZoom(zoom);
        invalidate();
    }

    /**
     * Get the current value for Angle
     *
     * @return the angle value
     */
    public float getAngle() {
        return getParameters().getAngle();
    }

    /**
     * Set a new value for Angle
     *
     * @param angle the new angle value
     */
    public void setAngle(float angle) {
        getParameters().setAngle(angle);
        invalidate();
    }

    /**
     * Get the current value for Frequency
     *
     * @return the frequency value
     */
    public float getFrequency() {
        return getParameters().getFrequency();
    }

    /**
     * Set a new value for Frequency
     *
     * @param frequency the new frequency value
     */
    public void setFrequency(float frequency) {
        if (getEvaluator() instanceof TimeEvaluator) {
            TimeEvaluator timeEvaluator = (TimeEvaluator) getEvaluator();
            timeEvaluator.setFrequency(frequency);
        }
    }

    /**
     * Set the current value for Speed
     *
     * @param speed the new speed
     */
    public void setSpeed(float speed) {
        setFrequency(1 / speed * 1000);
    }

    /**
     * Get the minimum possible zoom value
     *
     * @return the minimum zoom
     */
    public float getMinZoom() {
        return getParameters().getMinZoom();
    }

    /**
     * Set the minimum possible zoom value
     *
     * @param minZoom the minimum zoom
     */
    public void setMinZoom(float minZoom) {
        getParameters().setMinZoom(minZoom);
    }

    /**
     * Get the maximum possible zoom value
     *
     * @return the maximum zoom
     */
    public float getMaxZoom() {
        return getParameters().getMaxZoom();
    }

    /**
     * Set the maximum possible zoom value
     *
     * @param maxZoom the maximum zoom
     */
    public void setMaxZoom(float maxZoom) {
        getParameters().setMaxZoom(maxZoom);
    }

    /**
     * Get the minimum possible angle value
     *
     * @return the minimum angle
     */
    public float getMinAngle() {
        return getParameters().getMinAngle();
    }

    /**
     * Set the minimum possible angle value
     *
     * @param minAngle the minimum angle
     */
    public void setMinAngle(float minAngle) {
        getParameters().setMinAngle(minAngle);
    }

    /**
     * Get the maximum possible angle value
     *
     * @return the maximum angle
     */
    public float getMaxAngle() {
        return getParameters().getMaxAngle();
    }

    /**
     * Set the maximum possible angle value
     *
     * @param maxAngle the maximum angle
     */
    public void setMaxAngle(float maxAngle) {
        getParameters().setMaxAngle(maxAngle);
    }
}
