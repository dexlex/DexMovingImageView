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

import it.dex.movingimageviewlib.evaluating.evaluators.GyroscopeEvaluator;
import it.dex.movingimageviewlib.evaluating.evaluators.ScrollEvaluator;
import it.dex.movingimageviewlib.evaluating.evaluators.SimpleEvaluator;
import it.dex.movingimageviewlib.evaluating.evaluators.TimeEvaluator;

/**
 * Created by Diego on 08/12/2014.
 */
public class EvaluatorChooser {

    public static Evaluator get(EVALUATORS evaluator, View view, Evaluator.OnEventOccurred onEventOccurred) throws IllegalArgumentException {
        switch (evaluator) {
            case SIMPLE:
                return new SimpleEvaluator(view);
            case SCROLL:
                return new ScrollEvaluator(view, onEventOccurred);
            case TIME:
                return new TimeEvaluator(view, onEventOccurred);
            case GYROSCOPE:
                return new GyroscopeEvaluator(view, onEventOccurred);
            default:
                throw new IllegalArgumentException();
        }
    }
}
