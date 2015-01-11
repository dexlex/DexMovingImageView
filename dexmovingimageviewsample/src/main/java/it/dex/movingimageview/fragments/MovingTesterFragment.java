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

package it.dex.movingimageview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import it.dex.movingimageview.R;
import it.dex.movingimageviewlib.DexMovingImageView;
import it.dex.movingimageviewlib.drawing.DRAWERS;
import it.dex.movingimageviewlib.evaluating.EVALUATORS;
import it.dex.movingimageviewlib.evaluating.Evaluator;
import it.dex.movingimageviewlib.generating.VALUESGENERATORS;

public class MovingTesterFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener,
        DexMovingImageView.OnValueChanged, SeekBar.OnSeekBarChangeListener, Evaluator.OnEventOccurred {
    private DexMovingImageView dexMovingImageView;
    private TextView xValue, yValue, zValue, angleValue, eventOccurrences;
    private View commands;
    private SeekBar speedSeekBar;

    public static MovingTesterFragment newInstance() {
        MovingTesterFragment fragment = new MovingTesterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_moving_tester, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dexMovingImageView = (DexMovingImageView) view.findViewById(R.id.image);
        commands = view.findViewById(R.id.commands_card_view);
        SeekBar zoomSeekBar = (SeekBar) view.findViewById(R.id.zoom_seek_bar);
        SeekBar angleSeekBar = (SeekBar) view.findViewById(R.id.angle_seek_bar);
        speedSeekBar = (SeekBar) view.findViewById(R.id.speed_seek_bar);
        Spinner evaluators = (Spinner) view.findViewById(R.id.evaluators);
        Spinner generators = (Spinner) view.findViewById(R.id.generators);
        CheckBox scale = (CheckBox) view.findViewById(R.id.drawer_scale);
        CheckBox rotate = (CheckBox) view.findViewById(R.id.drawer_rotate);
        CheckBox translate = (CheckBox) view.findViewById(R.id.drawer_translate);
        xValue = (TextView) view.findViewById(R.id.x_value);
        yValue = (TextView) view.findViewById(R.id.y_value);
        zValue = (TextView) view.findViewById(R.id.z_value);
        angleValue = (TextView) view.findViewById(R.id.angle_value);
        eventOccurrences = (TextView) view.findViewById(R.id.event_occurrences);
        evaluators.setOnItemSelectedListener(this);
        generators.setOnItemSelectedListener(this);
        scale.setOnCheckedChangeListener(this);
        rotate.setOnCheckedChangeListener(this);
        translate.setOnCheckedChangeListener(this);
        zoomSeekBar.setOnSeekBarChangeListener(this);
        angleSeekBar.setOnSeekBarChangeListener(this);
        speedSeekBar.setOnSeekBarChangeListener(this);
        scale.setChecked(dexMovingImageView.isDrawerAdded(DRAWERS.SCALE));
        rotate.setChecked(dexMovingImageView.isDrawerAdded(DRAWERS.ROTATE));
        translate.setChecked(dexMovingImageView.isDrawerAdded(DRAWERS.TRANSLATE));
        zoomSeekBar.setProgress((int) (dexMovingImageView.getZoom() * 100));
        angleSeekBar.setProgress((int) (dexMovingImageView.getAngle() * 100 / angleSeekBar.getMax()));
        if (dexMovingImageView.getSpeed() == 0)
            speedSeekBar.setProgress(0);
        else
            speedSeekBar.setProgress((int) (1 / dexMovingImageView.getSpeed()));
        dexMovingImageView.setOnValueChanged(this);
        dexMovingImageView.setOnEventOccurred(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.drawer_rotate:
                if (isChecked)
                    dexMovingImageView.addDrawerType(DRAWERS.ROTATE.getDefaultName(), DRAWERS.ROTATE);
                else
                    dexMovingImageView.removeDrawerType(DRAWERS.ROTATE.getDefaultName());
                break;
            case R.id.drawer_scale:
                if (isChecked)
                    dexMovingImageView.addDrawerType(DRAWERS.SCALE.getDefaultName(), DRAWERS.SCALE);
                else
                    dexMovingImageView.removeDrawerType(DRAWERS.SCALE.getDefaultName());
                break;
            case R.id.drawer_translate:
                if (isChecked)
                    dexMovingImageView.addDrawerType(DRAWERS.TRANSLATE.getDefaultName(), DRAWERS.TRANSLATE);
                else
                    dexMovingImageView.removeDrawerType(DRAWERS.TRANSLATE.getDefaultName());
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = (String) parent.getItemAtPosition(position);
        EVALUATORS evaluatorValue = null;
        VALUESGENERATORS generatorValue = null;
        switch (selected) {
            case "Simple":
                evaluatorValue = EVALUATORS.SIMPLE;
                break;
            case "Scroll":
                evaluatorValue = EVALUATORS.SCROLL;
                break;
            case "Time":
                evaluatorValue = EVALUATORS.TIME;
                break;
            case "Gyroscope":
                evaluatorValue = EVALUATORS.GYROSCOPE;
                break;
            case "Base":
                generatorValue = VALUESGENERATORS.BASE;
                break;
            case "Angled":
                generatorValue = VALUESGENERATORS.ANGLED;
                break;
            case "Ranged":
                generatorValue = VALUESGENERATORS.RANGED;
                break;
            case "Smooth":
                generatorValue = VALUESGENERATORS.SMOOTH;
                break;
        }
        if (evaluatorValue != null) {
            dexMovingImageView.setEvaluatorType(evaluatorValue);
            speedSeekBar.setEnabled(evaluatorValue == EVALUATORS.TIME);
        }
        if (generatorValue != null)
            dexMovingImageView.setValuesGeneratorType(generatorValue);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onValueChanged(View view, float x, float y, float z, float angle) {
        xValue.setText((int) x + "");
        yValue.setText((int) y + "");
        zValue.setText(String.format("%.2f", z) + "");
        angleValue.setText((int) angle + "");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_moving_tester, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_commands:
                if (commands.getVisibility() == View.VISIBLE)
                    commands.setVisibility(View.GONE);
                else
                    commands.setVisibility(View.VISIBLE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.zoom_seek_bar:
                dexMovingImageView.setZoom((float) progress / 100);
                break;
            case R.id.angle_seek_bar:
                dexMovingImageView.setAngle(progress);
                break;
            case R.id.speed_seek_bar:
                if (progress != 0)
                    dexMovingImageView.setSpeed(progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onEventOccurred(View view, Evaluator evaluator, int occurrenceCount) {
        eventOccurrences.setText(occurrenceCount + "");
    }
}
