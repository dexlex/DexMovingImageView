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
 * Created by Diego on 06/12/2014.
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
                if (attr == R.styleable.DexMovingImageView_zoom) {
                    setZoom(a.getFloat(attr, getZoom()));
                } else if (attr == R.styleable.DexMovingImageView_minZoom) {
                    setMinZoom(a.getFloat(attr, getMinZoom()));
                } else if (attr == R.styleable.DexMovingImageView_maxZoom) {
                    setMaxZoom(a.getFloat(attr, getMaxZoom()));
                } else if (attr == R.styleable.DexMovingImageView_angle) {
                    setAngle(a.getFloat(attr, getAngle()));
                } else if (attr == R.styleable.DexMovingImageView_minAngle) {
                    setMinAngle(a.getFloat(attr, getMinAngle()));
                } else if (attr == R.styleable.DexMovingImageView_maxAngle) {
                    setMaxAngle(a.getFloat(attr, getMaxAngle()));
                } else if (attr == R.styleable.DexMovingImageView_speed) {
                    setFrequency(1 / a.getFloat(attr, getFrequency()) * 1000);
                } else if (attr == R.styleable.DexMovingImageView_drawer) {
                    if ((a.getInt(attr, 1) & 0) == 0) {
                        addDrawerType(DRAWERS.SCALE.getDefaultName(), DRAWERS.SCALE);
                    }
                    if ((a.getInt(attr, 0) & 1) == 1) {
                        addDrawerType(DRAWERS.TRANSLATE.getDefaultName(), DRAWERS.TRANSLATE);
                    }
                    if ((a.getInt(attr, 0) & 2) == 2) {
                        addDrawerType(DRAWERS.ROTATE.getDefaultName(), DRAWERS.ROTATE);
                    }
                } else if (attr == R.styleable.DexMovingImageView_generator) {
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
                } else if (attr == R.styleable.DexMovingImageView_evaluator) {
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

    public void addDrawerType(String drawerName, DRAWERS moverType) throws IllegalArgumentException {
        addDrawer(drawerName, DrawerChooser.get(moverType));
    }

    public void removeDrawerType(String drawerName) throws IllegalArgumentException {
        removeDrawer(drawerName);
    }

    public Parameters getParameters() {
        return parameters;
    }

    public ValuesGenerator getValuesGenerator() {
        return valuesGenerator;
    }

    public void setValuesGenerator(ValuesGenerator valuesGenerator) {
        this.valuesGenerator = valuesGenerator;
    }

    public void setValuesGeneratorType(VALUESGENERATORS valuesGeneratorType) {
        setValuesGenerator(ValuesGeneratorChooser.get(valuesGeneratorType, parameters));
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Evaluator evaluator) {
        if (this.evaluator != null)
            this.evaluator.stop();
        if (evaluator instanceof TimeEvaluator)
            ((TimeEvaluator) evaluator).setFrequency(getParameters().getFrequency());
        this.evaluator = evaluator;
        evaluator.start();
    }

    public void setEvaluatorType(EVALUATORS coordinatorType) {
        setEvaluator(EvaluatorChooser.get(coordinatorType, this, this));
    }

    public void addDrawer(String drawerName, Drawer drawer) {
        drawers.put(drawerName, drawer);
    }

    public boolean isDrawerAdded(DRAWERS drawerType) {
        Set<String> set = drawers.keySet();
        return set.contains(drawerType.getDefaultName());
    }

    public void removeDrawer(String drawerName) {
        drawers.remove(drawerName);
    }

    public void setOnValueChanged(OnValueChanged onValueChanged) {
        this.onValueChanged = onValueChanged;
    }

    @Override
    public void onEventOccurred(View view, Evaluator evaluator, Evaluator.EVENT_STATUS eventStatus, int occurrenceCount) {
        if (getOnEventOccurred() != null)
            getOnEventOccurred().onEventOccurred(view, evaluator, eventStatus, occurrenceCount);
    }

    public Evaluator.OnEventOccurred getOnEventOccurred() {
        return onEventOccurred;
    }

    public void setOnEventOccurred(Evaluator.OnEventOccurred onEventOccurred) {
        this.onEventOccurred = onEventOccurred;
    }

    public interface OnValueChanged {
        public void onValueChanged(View view, float x, float y, float z, float angle);
    }

    public float getZoom() {
        return getParameters().getZoom();
    }

    public void setZoom(float zoom) {
        getParameters().setZoom(zoom);
        invalidate();
    }

    public float getAngle() {
        return getParameters().getAngle();
    }

    public void setAngle(float angle) {
        getParameters().setAngle(angle);
        invalidate();
    }

    public float getFrequency() {
        return getParameters().getFrequency();
    }

    public void setFrequency(float frequency) {
        if (getEvaluator() instanceof TimeEvaluator) {
            TimeEvaluator timeEvaluator = (TimeEvaluator) getEvaluator();
            timeEvaluator.setFrequency(frequency);
        }
    }

    public void setSpeed(float speed) {
        setFrequency(1 / speed * 1000);
    }

    public float getMinZoom() {
        return getParameters().getMinZoom();
    }

    public void setMinZoom(float minZoom) {
        getParameters().setMinZoom(minZoom);
    }

    public float getMaxZoom() {
        return getParameters().getMaxZoom();
    }

    public void setMaxZoom(float maxZoom) {
        getParameters().setMaxZoom(maxZoom);
    }

    public float getMinAngle() {
        return getParameters().getMinAngle();
    }

    public void setMinAngle(float minAngle) {
        getParameters().setMinAngle(minAngle);
    }

    public float getMaxAngle() {
        return getParameters().getMaxAngle();
    }

    public void setMaxAngle(float maxAngle) {
        getParameters().setMaxAngle(maxAngle);
    }
}
