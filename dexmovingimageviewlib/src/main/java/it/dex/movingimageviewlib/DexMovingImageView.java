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

package it.dex.movingimageviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import it.dex.movingimageviewlib.drawing.DRAWERS;
import it.dex.movingimageviewlib.drawing.Drawer;
import it.dex.movingimageviewlib.drawing.DrawerChooser;
import it.dex.movingimageviewlib.evaluating.EVALUATORS;
import it.dex.movingimageviewlib.evaluating.Evaluator;
import it.dex.movingimageviewlib.evaluating.EvaluatorChooser;
import it.dex.movingimageviewlib.generating.VALUESGENERATORS;
import it.dex.movingimageviewlib.generating.ValuesGenerator;
import it.dex.movingimageviewlib.generating.ValuesGeneratorChooser;
import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * Created by Diego on 06/12/2014.
 */
public class DexMovingImageView extends DexCrossFadeImageView {
    private Parameters parameters = new Parameters();
    private List<Drawer> drawers = new ArrayList<>();
    private ValuesGenerator valuesGenerator;
    private Evaluator evaluator;

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
                    getParameters().setZoom(a.getFloat(attr, getParameters().getZoom()));
                } else if (attr == R.styleable.DexMovingImageView_angle) {
                    getParameters().setAngle(a.getFloat(attr, getParameters().getAngle()));
                } else if (attr == R.styleable.DexMovingImageView_drawer) {
                    if ((a.getInt(attr, 0) & 0) == 0)
                        addDrawerType(DRAWERS.SCALE);
                    if ((a.getInt(attr, 0) & 1) == 1)
                        addDrawerType(DRAWERS.TRANSLATE);
                    if ((a.getInt(attr, 0) & 2) == 2)
                        addDrawerType(DRAWERS.ROTATE);
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
                        setEvaluatorType(EVALUATORS.map(a.getInteger(attr, EVALUATORS.SCROLL.getType())));
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
            parameters.setZoom(getValuesGenerator().getZoom(getEvaluator().evaluateZoom(this, getParameters().getZoom())));
            parameters.setAngle(getValuesGenerator().getAngle(getEvaluator().evaluateAngle(this, getParameters().getAngle())));
        }
        super.invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getParameters().setWidth(getWidth());
        getParameters().setHeight(getHeight());
        getParameters().setPaddingBottom(getPaddingBottom());
        getParameters().setPaddingLeft(getPaddingLeft());
        getParameters().setPaddingRight(getPaddingRight());
        getParameters().setPaddingTop(getPaddingTop());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Drawer drawer : drawers)
            drawer.onDraw(canvas, parameters);
        super.onDraw(canvas);
    }

    public void addDrawerType(DRAWERS moverType) throws IllegalArgumentException {
        addDrawer(DrawerChooser.get(moverType));
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
        this.evaluator = evaluator;
    }

    public void setEvaluatorType(EVALUATORS coordinatorType) {
        setEvaluator(EvaluatorChooser.get(coordinatorType, this));
    }

    public void addDrawer(Drawer drawer) {
        drawers.add(drawer);
    }

    public void removeDrawer(Drawer drawer) {
        drawers.remove(drawer);
    }
}
