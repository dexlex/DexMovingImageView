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

import it.dex.movingimageviewlib.evaluating.Evaluator;

/**
 * DexMoveImageView created by Diego on 10/01/2015.
 */
public class SimpleEvaluator extends Evaluator {

    public SimpleEvaluator(View view) {
        super(view);
    }

    public SimpleEvaluator(View view, OnEventOccurred onEventOccurred) {
        super(view, onEventOccurred);
    }

    @Override
    protected void onCreate(View view) {

    }

    @Override
    public float evaluateX(View view) {
        return 0;
    }

    @Override
    public float evaluateY(View view) {
        return 0;
    }

    @Override
    protected void onDestroy(View view) {

    }
}
