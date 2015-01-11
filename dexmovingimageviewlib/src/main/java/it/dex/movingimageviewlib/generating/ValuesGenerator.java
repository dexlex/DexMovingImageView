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

package it.dex.movingimageviewlib.generating;

import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * DexMoveImageView created by Diego on 13/12/2014.
 */
public abstract class ValuesGenerator {
    private Parameters parameters;

    public ValuesGenerator(Parameters parameters) {
        setParameters(parameters);
    }

    public abstract float getX(float x);

    public abstract float getY(float y);

    public float getZoom(float zoom, float defaultZoom) {
        return defaultZoom;
    }

    public float getAngle(float angle, float defaultAngle) {
        return defaultAngle;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}
