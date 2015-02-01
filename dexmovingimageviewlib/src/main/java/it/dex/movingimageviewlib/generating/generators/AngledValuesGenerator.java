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

import it.dex.movingimageviewlib.generating.ValuesGenerator;
import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * The Angled ValuesGenerator implementation: it generates:
 * <p/>
 * - X value: it's calculated sinusoidally considering the image can be positioned from -viewWidth to deviceWidth + viewWidth. Then the value is normalized by angle value
 * <p/>
 * - Y value: it's calculated sinusoidally considering the image can be positioned from -viewHeight to deviceHeight + viewHeight. Then the value is normalized by angle value
 * <p/>
 * - Zoom value: as the default zoom
 * <p/>
 * - Angle value: as the default angle
 * <p/>
 * AngledValuesGenerator created by Diego Grancini on 13/12/2014.
 */
public class AngledValuesGenerator extends ValuesGenerator {

    public AngledValuesGenerator(Parameters parameters) {
        super(parameters);
    }

    @Override
    public float getX(float x) {
        float angle = getParameters().getAngle();
        float width = getParameters().getWidth();
        float deviceWidth = getParameters().getDeviceWidth();
        float zoom = getParameters().getZoom();
        float range = (deviceWidth - width / 2) * (zoom - 1) / zoom;
        float t = (float) (range * Math.sin(Math.toRadians(x)));
        return (float) (t * Math.cos(Math.toRadians(angle)));
    }

    @Override
    public float getY(float y) {
        float angle = getParameters().getAngle();
        float height = getParameters().getHeight();
        float deviceHeight = getParameters().getDeviceHeight();
        float zoom = getParameters().getZoom();
        float range = (deviceHeight - height / 2) * (zoom - 1) / zoom;
        float t = (float) (range * Math.sin(Math.toRadians(y)));
        return (float) (t * Math.sin(Math.toRadians(angle)));
    }
}
