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

    public ScrollEvaluator(View view, OnEventOccurred onEventOccurred) {
        super(view, onEventOccurred);
    }

    @Override
    public void onCreate(View view) {
        view.getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    public float evaluateX(View view) {
        return viewLocation[0];
    }

    @Override
    public float evaluateY(View view) {
        return viewLocation[1];
    }

    @Override
    public void onDestroy(View view) {
        view.getViewTreeObserver().removeOnScrollChangedListener(this);
    }

    @Override
    public void onScrollChanged() {
        getView().getLocationInWindow(viewLocation);
        if (viewLocation[0] + getView().getWidth() / 2 == getView().getContext().getResources().getDisplayMetrics().widthPixels / 2 &&
                viewLocation[1] + getView().getHeight() / 2 == getView().getContext().getResources().getDisplayMetrics().heightPixels / 2) {
            if (getOnEventOccurred() != null && isNotifyEvent())
                getOnEventOccurred().onEventOccurred(getView(), this, EVENT_STATUS.MIDDLE, ++middleLoopCount);
        }
        getView().invalidate();
    }
}
