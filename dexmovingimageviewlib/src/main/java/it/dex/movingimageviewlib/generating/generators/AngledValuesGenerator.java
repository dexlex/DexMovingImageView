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

package it.dex.movingimageviewlib.generating.generators;

import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * DexMoveImageView created by Diego on 13/12/2014.
 */
public class AngledValuesGenerator extends BaseValuesGenerator {

    public AngledValuesGenerator(Parameters parameters) {
        super(parameters);
    }

    @Override
    public float getX(float x) {
        float t = super.getX(x);
        float angle = getParameters().getAngle();
        return (float) (t * Math.sin(Math.toRadians(angle)));
    }

    @Override
    public float getY(float y) {
        float t = super.getY(y);
        float angle = getParameters().getAngle();
        return (float) (t * Math.cos(Math.toRadians(angle)));
    }
}
