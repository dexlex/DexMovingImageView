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
import android.view.ViewTreeObserver;

import it.dex.movingimageviewlib.evaluating.Evaluator;

/**
 * DexMoveImageView created by Diego on 13/12/2014.
 */
public class ScrollEvaluator extends Evaluator implements ViewTreeObserver.OnScrollChangedListener {
    private int[] viewLocation = new int[2];

    public ScrollEvaluator(View view) {
        super(view);
    }

    @Override
    public void onCreate(View view) {
        view.getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    public int evaluateX(View view) {
        return viewLocation[0];
    }

    @Override
    public int evaluateY(View view) {
        return viewLocation[1];
    }

    @Override
    public void onDestroy(View view) {
        view.getViewTreeObserver().removeOnScrollChangedListener(this);
    }

    @Override
    public void onScrollChanged() {
        getView().getLocationInWindow(viewLocation);
        getView().invalidate();
    }
}
