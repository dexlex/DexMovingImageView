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
 * A ValuesGenerator convert the values produced by Evaluator to the right values to be passed to the drawers.
 * <p/>
 * ValuesGenerator created by Diego Grancini on 13/12/2014.
 */
public abstract class ValuesGenerator {
    private Parameters parameters;

    /**
     * Default constructor to instantiate a ValuesGenerator
     *
     * @param parameters the values container
     */
    public ValuesGenerator(Parameters parameters) {
        setParameters(parameters);
    }

    /**
     * Generate the x values to be passed to the drawer starting with the values passed by the evaluator
     *
     * @param x the evaluator x value
     * @return the new x value
     */
    public abstract float getX(float x);

    /**
     * Generate the y values to be passed to the drawer starting with the values passed by the evaluator
     *
     * @param y the evaluator y value
     * @return the new y value
     */
    public abstract float getY(float y);

    /**
     * Generate the zoom values to be passed to the drawer starting with the values passed by the evaluator.
     * Default implementation returns the default value.
     *
     * @param zoom        the evaluator zoom value
     * @param defaultZoom the zoom value set by default
     * @return the new x value
     */
    public float getZoom(float zoom, float defaultZoom) {
        return defaultZoom;
    }

    /**
     * Generate the angle values to be passed to the drawer starting with the values passed by the evaluator.
     * Default implementation returns the default value.
     *
     * @param angle        the evaluator angle value
     * @param defaultAngle the angle value set by default
     * @return the new x value
     */
    public float getAngle(float angle, float defaultAngle) {
        return defaultAngle;
    }

    /**
     * Get the current parameters
     *
     * @return the Parameters set instance
     */
    protected Parameters getParameters() {
        return parameters;
    }

    private void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}
